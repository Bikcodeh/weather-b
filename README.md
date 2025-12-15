# WeatherApp 🌦️

WeatherApp es una aplicación para Android nativa desarrollada en Kotlin, que permite a los usuarios consultar el pronóstico del tiempo para diferentes ubicaciones. La aplicación muestra las condiciones actuales y un pronóstico para los próximos días.

## ✨ Características Principales

- Búsqueda de Ubicaciones: Busca ciudades y ubicaciones para obtener datos meteorológicos.
- Clima Actual: Visualiza la temperatura, condición climática (soleado, nublado, etc.), y otros detalles relevantes del día actual.
- Pronóstico Extendido: Muestra el pronóstico del tiempo para los próximos días.
- Interfaz Moderna: Interfaz de usuario limpia e intuitiva construida con Jetpack Compose.

## 🛠️ Tecnologías y Librerías Utilizadas
Este proyecto utiliza una variedad de herramientas y librerías modernas del ecosistema de Android para asegurar un desarrollo robusto, escalable y mantenible.

### Librerías Más Importantes
- Jetpack Compose: Utilizado para construir la interfaz de usuario de forma declarativa, moderna y nativa.
- Kotlin Coroutines & Flow: Para gestionar operaciones asíncronas de manera eficiente, especialmente para las llamadas de red y las operaciones de base de datos.
- Retrofit 3: Cliente HTTP para realizar las peticiones a la API del clima de forma segura y estructurada.
- Hilt: Para la inyección de dependencias, facilitando la gestión de las mismas y mejorando la modularidad y testeabilidad del código.
- ViewModel: Para gestionar y almacenar los datos relacionados con la UI, sobreviviendo a cambios de configuración.
- Coil: Para la carga y caché de imágenes de forma eficiente, como los íconos del clima.
- Moshi: Para la serialización y deserialización de objetos JSON a objetos Kotlin, integrado con Retrofit.
- Timber: Una librería para gestionar los logs de la aplicación de una manera más inteligente y flexible.

## 🏗️ Arquitectura
La aplicación sigue los principios de Clean Architecture para separar las responsabilidades y desacoplar las capas del software. Esto hace que el proyecto sea más fácil de mantener, escalar y testear. La arquitectura está dividida en tres capas principales:

1. Capa de Presentación (UI): Construida con Jetpack Compose. Es responsable de mostrar los datos en la pantalla y de capturar la interacción del usuario. Incluye Composables, ViewModels y estados de la UI.
2. Capa de Dominio (Domain): Contiene la lógica de negocio central de la aplicación. Define los casos de uso (Use Cases) que orquestan el flujo de datos desde la capa de datos hacia la de presentación. Esta capa es independiente de cualquier framework.
3. Capa de Datos (Data): Se encarga de proveer los datos a la aplicación, abstrayendo su origen (API remota, base de datos local, etc.). Implementa los Repositories definidos en la capa de dominio y utiliza Data Sources (como la API de Retrofit) para obtener la información.

## 📦 Mapa de Paquetes (Estructura del Proyecto)

```text
com.bikcodeh.weatherapp
├── core/                 
│   ├── di/               # Modulos de HILT para proveer repositories, retrofit y dispatchers
│   ├── dispatcher/       # Implementación concretata para proveer dispatchers para coroutines
│   └── mvi/              # Configuración base para manejar MVI en la aplicación
├── data/
│   ├── remote/
│   │   ├── api/          # Interfaces de Retrofit (ej: WeatherApi.kt)
│   │   ├── dto/          # Data Transfer Objects (respuestas de la API)
│   │   ├── interceptor/  # Interceptor para agregar dinamicamente el apiKey a los requests
│   │   └── network/      # Implementación para hacer peticiones seguras
│   └── repository/       # Implementaciones de repositorios
│
├── domain/
│   ├── commons/       # Interfaz para proveer dispatchers para coroutines
│   ├── error/         # Clases y mapper para manejar errores que puedan ocurrir
│   └── repository/    # Abstracción de los repositories
│
├── ui/
│   ├── components/    # Componentes reutilizables
│   ├── search/        # Todo lo relacionado al screen search, componentes, viewmodel, navigation
│   ├── detail/        # Todo lo relacionado al screen detail, componentes, viewmodel, navigation
│   ├── theme/         # Colores, tipografía
│   ├── error/         # Mapper para parsear los errores provenientes de la capa data para mostrarle al usuario un mensaje mas contextual
│   ├── mapper/        # Mappers para convertir los modelos dto provenientes de data a modelos de UI para separar responsabilidades de capas
│   ├── model/         # Modelos de UI
│   ├── utils/         # Utilidades para usar en la UI
│   └── navigation/    # Configuración de rutas para la navegación de la aplicación 
└──
```


## ✅ Pruebas Unitarias
Se han implementado pruebas unitarias para garantizar la correcta funcionalidad de las partes críticas de la aplicación, enfocándose principalmente en:

- Capa de Datos: Se testean los Repositories para verificar que manejan adecuadamente los datos provenientes de las fuentes de datos (Data Sources), incluyendo el manejo de errores y casos de éxito.
- ViewModels: Se prueban para asegurar que los estados de la UI se actualizan correctamente en respuesta a las interacciones del usuario y los resultados de los casos de uso.
- Mappers: 

## 🚀 Requisitos para Compilar
Para poder compilar y ejecutar el proyecto, necesitas lo siguiente:

1. Android Studio: Se recomienda la última versión estable (por ejemplo, Iguana o superior).
2. JDK: Versión 17 o superior.
3. Api mínima es 21 (Android 5.0)
4. API Key: La aplicación utiliza una API externa para obtener los datos del clima. Necesitarás obtener una API Key de WeatherAPI.com. Una vez obtenida, debes agregarla en el archivo local.properties de la siguiente manera:

```properties
WEATHER_API_KEY="TU_API_KEY_AQUI"
```

El proyecto está configurado para leer esta clave desde el BuildConfig para mantenerla segura y fuera del control de versiones.
    
