# Arquitectura

Este documento explica *por qué* el proyecto está construido así. El *qué* de cada clase está en su
KDoc.

## Principios

- **Regla de dependencias.** Las dependencias apuntan hacia el dominio. `:core:domain` es un módulo
  Kotlin/JVM puro: el compilador impide que el dominio use Android, Retrofit o cualquier framework.
- **Features independientes.** `:feature:*` solo dependen de contratos (`:core:domain`) y de módulos
  de UI compartidos; nunca de `:core:data` ni de otra feature. `:app` es el único módulo que conoce
  todo: arma el grafo de Hilt y conecta la navegación.
- **Encapsulamiento.** Dentro de cada módulo todo es `internal` salvo su API pública: rutas de
  navegación en las features, contratos en el dominio y `WeatherApiConfig` en datos.

## Dominio — `:core:domain`

| Elemento | Decisión |
|---|---|
| Modelos | `data class` inmutables. Las fechas son `LocalDate`, no `String`. |
| `WeatherRepository` | Contrato *main-safe*: se puede llamar desde el hilo principal. |
| Casos de uso | Contienen reglas de negocio reales: `SearchLocationsUseCase` normaliza la consulta y exige una longitud mínima; `GetWeatherForecastUseCase` define y valida los días del pronóstico. |
| `DomainResult<T>` | `Success(data)` o `Failure(DomainError)`, con `map` y `fold`. |
| `DomainError` | Jerarquía cerrada: `Network`, `Timeout`, `Unauthorized`, `NotFound`, `Server`, `Parsing`, `Unknown`. |

**¿Por qué no `kotlin.Result`?** Obliga a modelar errores como `Throwable`, deja escapar excepciones
de frameworks hacia la UI y `runCatching` atrapa `CancellationException`. Con un tipo propio el
`when` es exhaustivo: si se agrega un error, el compilador señala cada lugar que debe manejarlo.

## Datos — `:core:data`

- **API:** `WeatherApi` (Retrofit con funciones `suspend`) y DTOs `@Serializable` con solo los
  campos usados (`ignoreUnknownKeys`). Los campos esenciales son no nulos a propósito: si falta la
  temperatura se reporta un error de parseo en lugar de mostrar un `0°` falso.
- **Mappers DTO → dominio:** concentran las particularidades del API: URLs de iconos sin esquema
  (`//cdn...`), fechas ISO y la consulta `id:<id>`, que pide la ubicación exacta elegida en la
  búsqueda (buscar por nombre mezcla "London, UK" con "London, Canadá").
- **Errores:** `safeApiCall` convierte cualquier excepción en `DomainError` y relanza
  `CancellationException` para no romper la concurrencia estructurada.
- **Interceptores:** `ApiKeyInterceptor` (la key se redacta en los logs HTTP) y
  `LanguageInterceptor`, que pide los textos del API en el idioma del usuario.
- **Hilos:** el repositorio garantiza *main-safety* con `withContext(ioDispatcher)`. El dispatcher se
  inyecta con el qualifier `@IoDispatcher`, así los tests usan dispatchers de prueba. El
  `OkHttpClient` se crea de forma perezosa (`dagger.Lazy`) en la primera petición, fuera del hilo
  principal.
- **Configuración:** `WeatherApiConfig` (API key, logging, URL base) la provee `:app`; la librería
  no tiene secretos ni `BuildConfig` propio.

## Presentación — `:feature:*`

MVVM con flujo de datos unidireccional (UDF):

```text
Composable ──onQueryChange()/onRetry()──▶ ViewModel ──uiState: StateFlow──▶ Composable
```

- **Un único `UiState` por pantalla**, modelado como `sealed interface`
  (`Idle | Loading | Empty | Success | Error`): los estados imposibles (cargando *y* con error) no
  se pueden representar.
- **Errores como estado**, con botón de reintento, en lugar de eventos *one-shot*: no se pierden en
  rotaciones ni dependen del ciclo de vida del colector.
- **`stateIn(WhileSubscribed(5_000))`**: la carga empieza cuando la UI se suscribe (nada en `init`),
  sobrevive a rotaciones sin recargar y se detiene cuando la app pasa a segundo plano.
- **Búsqueda reactiva**: `trim` → `debounce` (300 ms) → `distinctUntilChanged` → `flatMapLatest`,
  que cancela la búsqueda anterior para que un resultado viejo nunca pise uno nuevo.
- **Estado del campo de texto en la UI** (`TextFieldState`): se actualiza de forma síncrona mientras
  se escribe y el ViewModel recibe la consulta con `snapshotFlow`. El ViewModel no depende de tipos
  de Compose.
- **Argumentos por *assisted injection***: `DetailViewModel` recibe el `locationId` en el
  constructor (`@HiltViewModel(assistedFactory = ...)`), no conoce la navegación y se prueba
  instanciándolo directamente.
- **Composables**: uno con estado (`XScreen`, obtiene el ViewModel) y uno sin estado (`XContent`,
  usado por previews y tests). `Modifier` como primer parámetro opcional, textos y formatos desde
  recursos (`@StringRes`, fechas según el `Locale`), descripciones de accesibilidad y semántica
  agrupada.

## Navegación

- Navigation Compose *type-safe*: `@Serializable data object SearchRoute` y
  `data class DetailRoute(val locationId: Long)`.
- Cada feature expone `NavGraphBuilder.xScreen(...)` y `NavController.navigateToX(...)`;
  `WeatherNavHost` en `:app` es el único lugar que sabe que un resultado abre el detalle.
- Los clics repetidos mientras una pantalla sale se ignoran (`dropUnlessResumed` / estado del
  lifecycle) para no apilar ni sacar destinos de más.

## Design system — `:core:designsystem`

- Tema propio con roles de color documentados y sin *dynamic color*: la identidad visual (degradado
  azul) es fija.
- Iconos vectoriales Material Symbols y componentes reutilizables: `WeatherTopAppBar`,
  `GradientBackground`, `ErrorState`, `LoadingIndicator`, `NetworkImage`, `LoopingLottieAnimation`.
- Recursos con prefijo por módulo (`resourcePrefix`) para evitar colisiones entre módulos.

## Build

- *Convention plugins* en `build-logic`: `weather.android.application`, `weather.android.library`,
  `weather.android.compose`, `weather.android.feature`, `weather.hilt` y `weather.jvm.library`.
- AGP 9 con Kotlin integrado, KSP en lugar de kapt, un único *version catalog*, configuration cache
  y build cache.
- R8 en release sin reglas `-keep` globales: cada librería aporta las suyas.
- `minSdk 26`, `targetSdk 37`.

## Testing

- **Fakes en lugar de mocks:** `FakeWeatherRepository` (en `:core:testing`) permite tests sociales
  con los casos de uso reales.
- **Tiempo virtual:** `MainDispatcherRule` + `runTest` + Turbine validan *debounce*, cancelación y
  reintentos en milisegundos. Los ViewModels se crean en `@Before`, cuando la regla ya instaló el
  dispatcher de prueba.
- **Integración de red:** `MockWebServer` sirve JSON reales del API para probar Retrofit,
  serialización, interceptores y mapeo de errores juntos.
- **UI:** Compose UI Test sobre Robolectric, en la JVM y sin emulador, también en CI.

## Siguientes pasos

- Caché offline con Room (repositorio *offline-first*).
- Baseline Profiles y Macrobenchmark.
- Screenshot tests de los componentes.
- Layouts adaptativos para tablets y plegables.
