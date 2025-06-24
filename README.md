# EmoCare – Asistente Móvil de Bienestar Emocional con IA

**EmoCare** es una aplicación móvil diseñada para acompañar a los usuarios en el reconocimiento y gestión de sus emociones cotidianas. Utilizando tecnologías modernas de desarrollo móvil e inteligencia artificial, la app permite detectar emociones a partir de texto o voz, brindando recomendaciones personalizadas orientadas al bienestar mental.

Este proyecto se desarrolla como parte de la unidad de aprendizaje "Programación de Dispositivos Móviles" del Instituto Politécnico Nacional (UPIIT Tlaxcala), cumpliendo con los lineamientos de la práctica 3.1 y fungiendo como la primera entrega parcial del proyecto final.

---

## Objetivo General

El objetivo principal de EmoCare es crear una herramienta accesible y empática que ayude a los usuarios a comprender sus estados emocionales y recibir orientación positiva. Para lograrlo, se incorpora un modelo de inteligencia artificial que analiza las expresiones textuales o vocales del usuario y determina la emoción predominante, con el fin de ofrecer respuestas adecuadas y apoyo inmediato.

---

## Descripción Técnica del Proyecto

EmoCare está desarrollada utilizando Android Jetpack Compose, un framework moderno para el diseño de interfaces declarativas en Kotlin. La arquitectura de la aplicación está basada en el patrón MVVM (Model-View-ViewModel), facilitando la separación entre la lógica de negocio, la gestión de estado y la interfaz de usuario.

La aplicación permite al usuario registrar cómo se siente a través de texto escrito en una interfaz limpia y accesible. Este texto es enviado a un módulo de procesamiento que, en esta primera etapa, simula el comportamiento de un modelo de inteligencia artificial para devolver una emoción probable, como “felicidad”, “tristeza”, “enojo” o “calma”.

Aunque el modelo aún no se encuentra completamente integrado, la aplicación ya contempla la arquitectura necesaria para su conexión mediante una API RESTful externa o, en versiones futuras, mediante TensorFlow Lite para ejecución local. Esta fase inicial permite validar el flujo general y preparar el terreno para la incorporación real de la IA.

---

## Funcionalidad Implementada

En esta primera entrega, se ha desarrollado una pantalla funcional que permite al usuario ingresar texto libremente. Tras pulsar un botón, el texto es enviado a una función simulada de clasificación emocional. El resultado es mostrado en la misma pantalla mediante una etiqueta textual. Esto permite verificar el comportamiento de la interfaz y la lógica de flujo entre entrada, procesamiento y salida.

La simulación del modelo ha sido diseñada para facilitar la transición hacia una integración real, permitiendo posteriormente sustituir la lógica por una llamada HTTP a un servicio externo de IA o cargar un modelo preentrenado en formato `.tflite`.

Se ha utilizado ViewModel para mantener el estado de la emoción detectada, y corrutinas de Kotlin para manejar procesos de forma asíncrona, preparando la base para futuras conexiones a red o procesamiento de voz.

---

## Estructura del Código Fuente

El proyecto está organizado en una estructura modular que contempla carpetas específicas para las pantallas (`ui`), lógica del ViewModel, modelos de datos (`model`), consumo de red (`network`) y almacenamiento (`data`). Esta organización busca facilitar la escalabilidad y mantenimiento del código a medida que se integran nuevas funcionalidades como reconocimiento de voz, recomendaciones dinámicas o almacenamiento local del historial emocional.

---

## Requisitos para Compilar

La aplicación ha sido desarrollada con Android Studio (versión Giraffe o superior) y requiere el SDK de Android 33+. Es necesario tener configurado Kotlin en su versión 1.9 o superior, y habilitar el soporte para Jetpack Compose con Material 3. No se requieren permisos especiales aún, salvo el acceso a internet si se integra una API en línea.

---

## Consideraciones y Próximos Pasos

EmoCare está diseñada para crecer en futuras etapas. Algunas de las funcionalidades planeadas incluyen la entrada de voz utilizando la API de reconocimiento de voz de Android, el consumo de APIs de frases motivacionales para generar recomendaciones, y el almacenamiento de un historial local con Room o DataStore.

También se planea la implementación de animaciones mediante Lottie o componentes nativos de Compose, y la configuración de WorkManager para enviar notificaciones diarias que fomenten hábitos saludables de introspección emocional.

---

## Autor y Créditos

Este proyecto fue desarrollado por **Aishlinn Ivette Samperio Ortiz** como parte del curso de Programación de Dispositivos Móviles en la Unidad Profesional Interdisciplinaria de Ingeniería, Campus Tlaxcala (IPN). Todos los derechos están reservados para fines educativos y de evaluación académica.


