# Kata 4: cliente HTTP de Fibonacci
**Tecnologías:** Java · Maven · Javalin 7.2.4 · Google Gson 2.15.0  
**Autora:** Sofía Rivero Perea

## 1. Objetivo de la entrega
Consultar un servicio HTTP local desde Java, comprobar el estado de la respuesta y transformar el JSON recibido en un objeto `Series`. El servidor, basado en el ejemplo del profesor, calcula los términos de Fibonacci que solicita el cliente. La versión final corresponde a R3: muestra la serie completa y sus términos pares.

## 2. Cómo compilar y ejecutar
1. Clona o descarga el repositorio y abre el archivo `pom.xml` en IntelliJ IDEA como proyecto Maven.
2. Configura un JDK 21 o superior y carga las dependencias con Load Maven Changes.
3. En el panel Maven, abre kata4 → Lifecycle y ejecuta compile.
4. Ejecuta el método `main` de `FibonacciServer.java` con el triángulo verde. Déjalo en marcha.
5. Ejecuta el método `main` de `Main.java`. Sin argumentos, el cliente solicita 8 términos y muestra el resultado por consola.

### Servicio y datos de entrada
Abre [http://localhost:8080/fibonacci?n=8](http://localhost:8080/fibonacci?n=8) para ver el JSON en el navegador. La **URL base** está en la constante `BASE_URL` de `Main`. El adaptador añade la ruta `/fibonacci` y el parámetro `n`, que indica cuántos términos se solicitan.

## 3. Dependencias y versión de JDK
| Tecnología | Uso |
| --- | --- |
| **JDK 21 o superior** | Compila y ejecuta el proyecto, configurado para Java 21. Su clase `HttpClient` envía las peticiones HTTP. |
| **Maven** | Gestiona la compilación y las dependencias declaradas en `pom.xml`. |
| **Javalin 7.2.4** | Arranca el servidor local y publica la ruta `/fibonacci`. |
| **Google Gson 2.15.0** | Genera el array JSON en el servidor y lo interpreta en el cliente. |
| **SLF4J Simple 2.0.20** | Muestra los mensajes de registro del servidor. |
| **IntelliJ IDEA** | Entorno utilizado para desarrollar y ejecutar la práctica. |

Se utiliza **Gson** para generar el JSON en el servidor y leer su estructura en el cliente. El parser comprueba que la respuesta sea un array y que sus elementos sean enteros antes de construir la serie.

## 4. Estructura de la entrega y clases principales

El código fuente está en `src/main/java`, bajo el paquete base `software.ulpgc.katas`. En la raíz se encuentran `pom.xml`, `.gitignore` y `README.md`.

| Clase o interfaz | Responsabilidad |
| --- | --- |
| `Main` | Configura la URL base, solicita la serie y muestra el resultado por consola. |
| `architecture.domain.Series` | Representa la serie mediante un array de enteros. |
| `architecture.ports.FibonacciService` | Define el puerto de consulta mediante `findSeries(int n)`. |
| `infrastructure.adapters.HttpFibonacciService` | Implementa el puerto, envía el GET y comprueba el estado HTTP. |
| `infrastructure.adapters.GsonSeriesParser` | Interpreta la respuesta JSON y construye un objeto `Series`. |
| `FibonacciServer` | Arranca el servidor en el puerto 8080 y atiende las peticiones a `/fibonacci`. |
| `architecture.usecases.CalculateFibonacciSeriesUseCase` | Calcula los términos de Fibonacci solicitados. |
| `infrastructure.adapters.ContextInput` | Lee el parámetro `n` de la petición. |
| `infrastructure.adapters.GsonSeriesSerializer` | Convierte la serie calculada en el JSON que devuelve el servidor. |

### Separación de responsabilidades
`Main` configura el adaptador y pide la serie mediante el puerto `FibonacciService`. `HttpFibonacciService` construye la URL y envía la petición **GET**. Si recibe **HTTP 200**, pasa el cuerpo de la respuesta a `GsonSeriesParser`, que lo convierte en un objeto `Series`. El método `show` de `Main` muestra el resultado y, en R3, filtra los términos pares. El cálculo de Fibonacci se realiza en el servidor.

## 5. Vídeo explicativo
[https://youtu.be/QMoI7r1IC9w](https://youtu.be/QMoI7r1IC9w).
El vídeo corresponde a la **primera repetición (R1)**, disponible en la rama `develop`.

## 6. Repeticiones de la kata
El cliente se rehízo **tres veces** manteniendo el mismo servidor y la misma estructura de consulta HTTP, conversión de JSON y presentación. La variación está en lo que se muestra por consola.
| Repetición | Rama de consulta | Variación |
| --- | --- | --- |
| **R1** | [`develop`](https://github.com/SofiaRivero333/kata4/tree/develop) | Muestra la serie completa. |
| **R2** | [`develop_two`](https://github.com/SofiaRivero333/kata4/tree/develop_two) | Muestra la serie y la cantidad de términos recibidos. |
| **R3** | [`develop_three`](https://github.com/SofiaRivero333/kata4/tree/develop_three) | Muestra la serie completa y, por separado, los términos pares. |

La rama **`master`** contiene la versión final (**R3**) y la documentación.

## 7. Evidencias de verificación o pruebas realizadas
Se comprobó que la versión final compila con **JDK 21** y se realizaron las siguientes pruebas.

### 7.1. Salida del programa en R3
Con el servidor en marcha, la petición `GET /fibonacci?n=8` respondió con **HTTP 200** y este JSON:

```json
[0, 1, 1, 2, 3, 5, 8, 13]
```

Al ejecutar `Main` sin argumentos, se obtuvo:

```text
Serie de Fibonacci: [0, 1, 1, 2, 3, 5, 8, 13]
Términos pares: [0, 2, 8]
```

### 7.2. Serie vacía
Se ejecutó `Main` con el argumento `0`. El servidor devolvió un array vacío y el cliente mostró:

```text
Serie de Fibonacci: []
Términos pares: []
```

### 7.3. Petición fuera de rango
Al ejecutar `Main` con el argumento `-1`, el servidor rechazó la petición y el cliente mostró:

```text
Error: El servicio respondió con HTTP 400.
```

### 7.4. Servicio no disponible
Se detuvo `FibonacciServer` y se volvió a ejecutar `Main` sin argumentos. El cliente controló el fallo de conexión:

```text
Error: No se pudo consultar el servicio en http://localhost:8080.
```

### 7.5. Respuesta JSON inválida
En una copia de prueba del servidor se sustituyó temporalmente `result(output.result())` por `result("esto no es JSON")`. Al arrancar esa copia y ejecutar `Main`, el servidor devolvió **HTTP 200**, pero el cliente rechazó el cuerpo de la respuesta:

```text
Error: Respuesta JSON inválida.
```
