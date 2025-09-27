# 🎮 Centro de Juegos - FordGame

**Proyecto Final - Desarrollo de Aplicaciones Móviles**

**Versión:** 1.0  
**Plataforma:** Android  
**Idioma:** Español  
**Equipo de Desarrollo:** Daniel Garza & Misael Martinez  
**Institución Académica:** Universidad Tecmilenio  
**Materia:** Desarrollo de Aplicaciones Móviles  
**Período Académico:** 2025  

---

## 📋 Descripción

**Centro de Juegos** es una aplicación móvil Android desarrollada como proyecto final de la materia de Desarrollo de Aplicaciones Móviles. La aplicación integra una colección de cuatro juegos clásicos en una plataforma unificada, demostrando competencias en desarrollo Android, diseño de interfaces, gestión de datos y arquitectura de software.

### 🎓 Objetivos Académicos

Este proyecto fue desarrollado con el propósito de demostrar:

- **Desarrollo Android Nativo**: Implementación completa usando Kotlin y Android SDK
- **Arquitectura de Software**: Organización modular y mantenible del código
- **Gestión de Datos**: Persistencia local con SharedPreferences y encriptación
- **Diseño UI/UX**: Aplicación de principios de Material Design 3
- **Trabajo en Equipo**: Desarrollo colaborativo con división de responsabilidades
- **Documentación Técnica**: Creación de documentación profesional y detallada

### 🎯 Características Principales

- ✅ **Sistema de Autenticación**: Login y registro con validación de datos y encriptación SHA-256
- ✅ **Cuatro Juegos Integrados**: Sudoku, Tres en Raya, Juego de Memoria y Trivia Tecnológica
- ✅ **Estadísticas Persistentes**: Seguimiento de puntuaciones y progreso del usuario
- ✅ **Interfaz Moderna**: Diseño basado en Material Design 3 con paleta de colores personalizada
- ✅ **Navegación Intuitiva**: Dashboard centralizado con iconos personalizados para cada juego
- ✅ **Totalmente en Español**: Interfaz completamente localizada

---

## 👥 Organización del Equipo

### División de Responsabilidades

El desarrollo del proyecto se realizó mediante una metodología colaborativa con división específica de tareas:

#### Daniel Garza
- **🏗️ Arquitectura del Proyecto**: Estructura inicial, configuración de Gradle y dependencias
- **🔐 Sistema de Autenticación**: Implementación completa de Login y Registro con encriptación
- **🎮 Juegos - Tres en Raya**: Lógica de juego, animaciones y estadísticas persistentes
- **🧠 Juegos - Juego de Memoria**: Implementación completa con temporizador y puntuaciones
- **📱 Interfaz Principal**: Dashboard, navegación y gestión de sesiones

#### Misael Martinez
- **🎨 Diseño e Iconografía**: Creación de iconos vectoriales personalizados y paleta de colores
- **🔢 Juegos - Sudoku**: Generación de puzzles, validación y sistema de pistas
- **🧩 Juegos - Trivia**: Base de preguntas, sistema de scoring y feedback visual
- **📋 Layouts y UI**: Diseño responsive de todas las actividades
- **🧪 Testing y QA**: Pruebas de funcionalidad y corrección de bugs

#### Responsabilidades Compartidas
- **📚 Documentación**: Elaboración conjunta del README y comentarios en código
- **🎯 Integración**: Merge de funcionalidades y resolución de conflictos
- **🐛 Debugging**: Identificación y corrección colaborativa de errores
- **✨ Refinamiento**: Mejoras de UX y optimización de rendimiento

### Metodología de Trabajo

1. **Planificación Inicial**: Definición de alcance y división de módulos
2. **Desarrollo Paralelo**: Trabajo simultáneo en diferentes componentes
3. **Integración Continua**: Merge frecuente y testing conjunto
4. **Revisión de Código**: Code review mutuo antes de commits importantes
5. **Testing Colaborativo**: Pruebas cruzadas de funcionalidades
6. **Documentación Final**: Elaboración conjunta de documentación técnica

---

## 🚀 Instalación y Configuración

### Requisitos del Sistema

- **Android Studio**: Arctic Fox (2020.3.1) o superior
- **SDK mínimo**: Android 7.0 (API 24)
- **SDK objetivo**: Android 14 (API 36)
- **Kotlin**: 2.0.21
- **Gradle**: 8.12.3

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd ProyectoFinal
   ```

2. **Abrir en Android Studio**
   - Abrir Android Studio
   - Seleccionar "Open an Existing Project"
   - Navegar hasta la carpeta del proyecto

3. **Sincronizar dependencias**
   ```bash
   ./gradlew build
   ```

4. **Ejecutar la aplicación**
   - Conectar un dispositivo Android o iniciar un emulador
   - Hacer clic en "Run" o usar `Shift + F10`

---

## 🏗️ Arquitectura del Proyecto

### Estructura de Directorios

```
app/
├── src/main/
│   ├── java/com/example/proyectofinal/
│   │   ├── MainActivity.kt              # Punto de entrada y redirección
│   │   ├── LoginActivity.kt             # Autenticación de usuarios
│   │   ├── RegisterActivity.kt          # Registro de nuevos usuarios
│   │   ├── DashboardActivity.kt         # Dashboard principal
│   │   ├── TicTacToeActivity.kt         # Juego Tres en Raya
│   │   ├── SudokuActivity.kt            # Juego Sudoku
│   │   ├── MemoryGameActivity.kt        # Juego de Memoria
│   │   └── QuizGameActivity.kt          # Juego de Trivia
│   ├── res/
│   │   ├── drawable/                    # Iconos vectoriales personalizados
│   │   ├── layout/                      # Layouts XML para cada Activity
│   │   ├── values/                      # Recursos de strings, colores y temas
│   │   └── mipmap-*/                    # Iconos de la aplicación
│   └── AndroidManifest.xml
└── build.gradle.kts
```

### Tecnologías Utilizadas

| Componente | Tecnología | Versión |
|------------|------------|---------|
| **Lenguaje** | Kotlin | 2.0.21 |
| **UI Framework** | Material Design 3 | 1.13.0 |
| **Layouts** | ConstraintLayout, GridLayout | 2.2.1 |
| **Persistencia** | SharedPreferences | Android SDK |
| **Seguridad** | SHA-256 Hash | Java Security |
| **Testing** | JUnit, Espresso | 4.13.2 / 3.7.0 |

---

## 🎮 Funcionalidades

### 1. Sistema de Autenticación

#### Login (`LoginActivity.kt`)
- **Validación de email**: Verificación de formato válido
- **Encriptación de contraseñas**: Hash SHA-256 para seguridad
- **Sesión persistente**: Mantiene al usuario logueado
- **Manejo de errores**: Mensajes claros de error

#### Registro (`RegisterActivity.kt`)
- **Validación completa**: Nombre, email y contraseña
- **Prevención de duplicados**: No permite emails repetidos
- **Encriptación automática**: Hash de contraseñas al registrar
- **Redirección automática**: Login automático tras registro exitoso

### 2. Dashboard Principal (`DashboardActivity.kt`)

- **Navegación centralizada**: Acceso a todos los juegos
- **Mensaje de bienvenida**: Saludo personalizado con el nombre del usuario
- **Logout seguro**: Confirmación antes de cerrar sesión
- **Iconos personalizados**: Representación visual única para cada juego

### 3. Juegos Implementados

#### 🎯 Tres en Raya (`TicTacToeActivity.kt`)
- **Lógica de juego completa**: Detección de ganador y empates
- **Animaciones suaves**: Transiciones visuales en cada movimiento
- **Estadísticas persistentes**: Contador de victorias y empates
- **Reinicio de stats**: Opción para resetear estadísticas
- **Interfaz responsive**: Adaptable a diferentes tamaños de pantalla

#### 🔢 Sudoku (`SudokuActivity.kt`)
- **Generación automática**: Puzzles únicos en cada partida
- **Validación en tiempo real**: Verificación inmediata de números
- **Sistema de pistas**: Ayudas limitadas para el usuario
- **Detección de victoria**: Verificación automática de solución completa
- **Interfaz intuitiva**: Grid 9x9 con separadores visuales

#### 🧠 Juego de Memoria (`MemoryGameActivity.kt`)
- **Grid 4x4**: 16 cartas con 8 pares de emojis
- **Contador de movimientos**: Seguimiento de eficiencia del jugador
- **Temporizador**: Cronómetro en tiempo real
- **Mejor puntuación**: Registro del menor número de movimientos
- **Animaciones de flip**: Efectos visuales al voltear cartas

#### 🧩 Trivia Tecnológica (`QuizGameActivity.kt`)
- **10 preguntas técnicas**: Sobre programación y tecnología
- **Opciones múltiples**: 4 alternativas por pregunta
- **Puntuación dinámica**: Sistema de scoring por respuestas correctas
- **Feedback inmediato**: Indicación visual de respuestas correctas/incorrectas
- **Progreso visual**: Indicador de pregunta actual

---

## 🎨 Diseño e Interfaz

### Paleta de Colores

| Color | Hex Code | Uso |
|-------|----------|-----|
| **Rojo Principal** | `#D91B24` | Botones primarios, acentos |
| **Rojo Oscuro** | `#761F21` | Bordes, elementos secundarios |
| **Gris Claro** | `#C4C4C4` | Elementos deshabilitados |
| **Gris Oscuro** | `#504141` | Texto secundario |
| **Superficie** | `#FFFBFE` | Fondos de superficie |

### Iconografía

Todos los iconos son vectoriales personalizados diseñados específicamente para la aplicación:

- **🎮 Icono principal**: Controlador de juegos estilizado
- **🔢 Sudoku**: Grid con números de muestra
- **⭕ Tres en Raya**: Tablero con X y O
- **🃏 Memoria**: Cartas boca arriba y boca abajo
- **🧠 Trivia**: Cerebro con signo de interrogación
- **🚪 Logout**: Puerta con flecha de salida

### Temas y Estilos

- **Base**: Material Design 3 con personalización
- **Modo oscuro**: Compatible con preferencias del sistema
- **Tipografía**: Roboto con jerarquía clara
- **Componentes**: Cards elevadas, botones con esquinas redondeadas

---

## 💾 Gestión de Datos

### SharedPreferences

La aplicación utiliza múltiples archivos de SharedPreferences para organizar los datos:

#### 1. UserPrefs
```kotlin
// Autenticación y sesión
- "IsLoggedIn": Boolean
- "CurrentUserEmail": String
- "Users": Set<String> (emails registrados)
- "[email]_name": String
- "[email]_password": String (hash SHA-256)
```

#### 2. TicTacToeStats
```kotlin
// Estadísticas del Tres en Raya
- "PlayerXWins": Int
- "PlayerOWins": Int  
- "Draws": Int
```

#### 3. MemoryGameStats
```kotlin
// Estadísticas del Juego de Memoria
- "BestScore": Int (menor número de movimientos)
```

### Seguridad

- **Encriptación SHA-256**: Todas las contraseñas se almacenan hasheadas
- **Validación de entrada**: Verificación de formato de email y contraseñas
- **Gestión de sesiones**: Control de estado de login/logout

---

## 🧪 Testing

### Estructura de Pruebas

```
src/
├── test/                    # Unit Tests
│   └── ExampleUnitTest.kt
└── androidTest/             # Integration Tests
    └── ExampleInstrumentedTest.kt
```

### Comandos de Testing

```bash
# Ejecutar unit tests
./gradlew test

# Ejecutar instrumented tests
./gradlew connectedAndroidTest

# Ejecutar todos los tests
./gradlew testDebug
```

---

## 📱 Compatibilidad

### Versiones de Android Soportadas

- **Mínimo**: Android 7.0 (API 24) - 95% de dispositivos
- **Objetivo**: Android 14 (API 36) - Últimas características
- **Compilación**: SDK 36

### Características del Dispositivo

- **RAM mínima**: 2GB recomendado
- **Almacenamiento**: ~50MB para la aplicación
- **Pantalla**: Compatible desde 4" hasta tablets
- **Orientación**: Portrait (vertical)

---

## 🔧 Configuración de Desarrollo

### Variables de Build

```kotlin
// build.gradle.kts (app)
android {
    compileSdk = 36
    
    defaultConfig {
        applicationId = "com.example.proyectofinal"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}
```

### Dependencias Principales

```kotlin
dependencies {
    // Android Jetpack
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.activity:activity:1.11.0")
    
    // Material Design
    implementation("com.google.android.material:material:1.13.0")
    
    // GridLayout para juegos
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
}
```

---

## 📈 Roadmap y Mejoras Futuras

### Versión 1.1 (Planificada)
- [ ] Modo multijugador local para Tres en Raya
- [ ] Niveles de dificultad para Sudoku
- [ ] Más categorías de preguntas en Trivia
- [ ] Sonidos y efectos de audio

### Versión 1.2 (Planificada)
- [ ] Guardado en la nube con Firebase
- [ ] Logros y badges
- [ ] Ranking global de jugadores
- [ ] Modo oscuro manual

### Versión 2.0 (Futuro)
- [ ] Nuevos juegos (Solitario, 2048)
- [ ] Multijugador online
- [ ] Personalización de avatares
- [ ] Análiticas de juego

---

## 🐛 Problemas Conocidos

Actualmente no hay problemas conocidos. Si encuentras algún bug, por favor:

1. Verifica que estés usando la versión más reciente
2. Reproduce el error en un dispositivo limpio
3. Incluye información del dispositivo y versión de Android
4. Describe los pasos para reproducir el problema

---

## 📄 Licencia y Uso Académico

Este proyecto ha sido desarrollado exclusivamente con fines académicos como parte del proyecto final de la materia de Desarrollo de Aplicaciones Móviles. 

### Términos de Uso Académico

- **Propósito**: Proyecto final educativo - demostración de competencias técnicas
- **Institución**: [Universidad/Instituto] - Ingeniería en Desarrollo de Software
- **Evaluación**: Proyecto sujeto a evaluación académica
- **Código Fuente**: Disponible para revisión académica y educativa
- **Uso Comercial**: No autorizado - únicamente fines educativos

### Derechos de Autoría

**© 2024 Daniel Garza & Misael Martinez**  
*Estudiantes de Ingeniería en Desarrollo de Software*

**Todos los derechos reservados** - Proyecto académico bajo supervisión educativa

---

## 👨‍💻 Autores

### 👨‍💻 Daniel Garza
- **Rol**: Desarrollador Android - Backend & Arquitectura
- **Responsabilidades**: Sistema de autenticación, arquitectura del proyecto, juegos (Tres en Raya, Memoria)
- **Competencias**: Kotlin, Android SDK, Gestión de datos, Lógica de negocio
- **Contacto**: [email del estudiante]

### 👨‍💻 Misael Martinez  
- **Rol**: Desarrollador Android - Frontend & Diseño
- **Responsabilidades**: Diseño UI/UX, iconografía, juegos (Sudoku, Trivia), testing
- **Competencias**: Material Design, Layouts responsive, Testing, Optimización UX
- **Contacto**: [email del estudiante]

**Programa Académico**: Ingeniería en Desarrollo de Software  
**Institución**: [Universidad/Instituto]  
**Período**: 2024

---

## 🎓 Agradecimientos Académicos

### Institución Educativa
- **[Universidad/Instituto]**: Por proporcionar los recursos y el ambiente de aprendizaje
- **Profesor [Nombre]**: Por la guía y supervisión durante el desarrollo del proyecto
- **Laboratorio de Cómputo**: Por facilitar el equipo y software necesario

### Recursos Tecnológicos
- **Material Design**: Por las guías de diseño y mejores prácticas de UI/UX
- **Android Developers**: Por la documentación completa y tutoriales
- **Kotlin**: Por un lenguaje moderno y expresivo que facilitó el desarrollo
- **JetBrains**: Por el entorno de desarrollo Android Studio
- **Comunidad Open Source**: Por las librerías y recursos disponibles

### Metodología de Aprendizaje
- **Desarrollo Colaborativo**: Experiencia valiosa en trabajo en equipo
- **Metodologías Ágiles**: Aplicación de principios de desarrollo iterativo
- **Documentación Técnica**: Práctica en creación de documentación profesional
- **Testing y QA**: Implementación de buenas prácticas de calidad de software

---

## 📞 Soporte Académico

### Para Evaluación Académica
- **Profesor**: [Nombre del profesor] - [email académico]
- **Institución**: [Universidad/Instituto]
- **Documentación**: Ver secciones específicas de este README
- **Presentación**: Disponible para demo en vivo del proyecto

### Para Consultas Técnicas
- **Equipo de Desarrollo**: Daniel Garza & Misael Martinez
- **Repositorio**: Disponible para revisión del código fuente
- **Documentación de Código**: Comentarios detallados en archivos fuente

---

## 📋 Nota Final Académica

Este proyecto representa la culminación del aprendizaje en desarrollo de aplicaciones móviles Android. Ha sido desarrollado aplicando metodologías profesionales de desarrollo de software, buenas prácticas de programación, y principios de diseño centrado en el usuario.

**Competencias Demostradas:**
- ✅ Desarrollo nativo Android con Kotlin
- ✅ Implementación de arquitecturas limpias y mantenibles  
- ✅ Gestión de datos y persistencia local
- ✅ Diseño de interfaces siguiendo Material Design
- ✅ Trabajo colaborativo y control de versiones
- ✅ Testing y aseguramiento de calidad
- ✅ Documentación técnica profesional

El proyecto está listo para evaluación académica y demuestra el dominio de las tecnologías y metodologías enseñadas durante el curso.

---

*Proyecto Final - Desarrollo de Aplicaciones Móviles*  
*Daniel Garza & Misael Martinez*  
*Ingeniería en Desarrollo de Software*  
*Período Académico 2025*
