# Versión para estudiantes — Práctica

Objetivo
-------
Esta rama contiene una versión reducida del proyecto diseñada para que los alumnos implementen la lógica principal. Los métodos marcados con TODO están intencionalmente incompletos y son la base de la práctica.

Archivos clave a completar
## Versión para estudiantes — Práctica

### Objetivo
Esta rama contiene una versión reducida del proyecto diseñada para que los alumnos implementen la lógica principal. Los métodos marcados con TODO están intencionalmente incompletos y son la base de la práctica.

### Archivos clave a completar
- `src/negocios/Universidad.java`
  - Métodos a implementar:
    - `prestarProducto(int codigo, int id, Fecha fecha)`
    - `devolverProducto(int id, Fecha fechaDev)`
    - `consultarEstudianteTieneProducto(int id)`
    - `consultarProductosDeUnEstudiante(int codigo)`
    - `eliminarProducto(int id)`

- `src/datos/UniversidadDAO.java`
  - Métodos a implementar (I/O):
    - `guardarPrestamo(Prestamo p)`
    - `cargarPrestamos()`
    - `persistirProductos(...)` y `persistirEstudiantes(...)`
    - `eliminarProductoPorId(int id)`
    - `eliminarEstudiantePorCodigo(int codigo)`
    - `actualizarPrestamoDevolucion(...)`
    - `limpiarPrestamosPorProductoOEstudiante(...)`

- `src/datos/LectorArchivo.java`
  - Método: `leerArchivo(...)` — parseo CSV para cargar estudiantes, productos y préstamos.

### Expectativas de la práctica
- Mantener las firmas públicas de los métodos. No cambies nombres de métodos ni paquetes.
- Implementar la lógica solicitada siguiendo los contratos indicados abajo.

### Contratos mínimos (por método)
- `prestarProducto(codigo,id,fecha)`:
  - Precondiciones: existe estudiante con `codigo`, existe producto con `id`, producto disponible (no prestado y no hay préstamo activo).
  - Efecto: crear `Prestamo`, agregarlo a `prestamos`, actualizar `prestamosPorEstudiantePila/Cola`, y marcar producto como prestado (`producto.prestar()`).
  - Retorno: `true` si se realizó, `false` en caso contrario.

- `devolverProducto(id, fechaDev)`:
  - Precondiciones: existe préstamo activo para `id`; `fechaDev >= fechaPrestamo`.
  - Efecto: setFechaDevolucion en el préstamo, marcar producto como devuelto (`producto.devolver()`), persistir cambios si aplica.
  - Retorno: `true` si se realizó, `false` en caso contrario.

- `consultarEstudianteTieneProducto(id)`:
  - Buscar en `prestamos` un préstamo con `productoId == id` y `fechaDevolucion == null`, retornar el `Estudiante` asociado o `null`.

- `consultarProductosDeUnEstudiante(codigo)`:
  - Retornar `ListaDoble<Producto>` con los productos que tengan préstamos activos para el código dado.

- `eliminarProducto(id)`:
  - Eliminar préstamos asociados, quitar el producto de la lista correspondiente y actualizar el índice invertido (`desindexarProducto`). Retornar `true` si se eliminó.

### Pistas de implementación
- Para leer/escribir archivos, revisa `src/miTest/archivos` (fixtures) para ver el formato CSV.
- Usa `Configuracion.getBoolean("modo.pruebas")` y `Configuracion.get("ruta.pruebas")` para elegir rutas en `LectorArchivo` / `UniversidadDAO`.
- Mantén los pasos de persistencia atómicos (escribir en archivo temporal y renombrar).

### Tests y verificación
Ejecuta los tests con:

```bash
make compile && make test
```

Nota: en esta rama muchos tests fallarán hasta que completes las tareas. Puedes implementar progresivamente y ejecutar los tests para verificar avances.

### Tarea adicional: Autenticación (requerida)

Se solicita que los alumnos implementen la parte de autentificación de usuarios como parte de la práctica. Puntos clave:

- Implementa un servicio `src/negocios/AuthService.java` (ya existe un esqueleto en la rama `student-version`).
- Flujo mínimo esperado:
  - Método `login(username,password)` que valida credenciales y retorna un objeto `datos.Usuario` en caso de éxito o `null` si falla.
  - Debe lanzarse `negocios.excepciones.ErrorFisicoException` en errores de E/S.
  - Opcional: `register(Usuario u, String password)` para añadir nuevos usuarios.

- Integración con la aplicación:
  - `miPrincipal.Principal` actualmente usa `uI.Aplicacion.autenticarUsuario(...)` como ejemplo rápido. Los alumnos deben:
    1. Implementar `AuthService.login(...)` y actualizar `miPrincipal.Principal` para usarla (o delegar desde `uI.Aplicacion`).
    2. Asegurarse de que tras login correcto se inyecta `nombreUsuario` y `email` en `UniversidadService` y `Menu` (ya hay constructores preparados para ello).

- Requisitos mínimos de seguridad (sugeridos para la práctica):
  - No almacenar contraseñas en texto plano en producción (para la práctica pueden usar `usuario.properties` con contraseña en texto plano; en un ejercicio avanzado, implementar hashing simple).
  - Limitar intentos de login (p. ej. 3 intentos) antes de cerrar la aplicación (ejemplo ya presente en `miPrincipal.Principal`).

- Tests recomendados que los alumnos pueden escribir en `src/miTest`:
  - `AuthServiceLoginTest`: login correcto devuelve Usuario no nulo.
  - `AuthServiceBadCredsTest`: login con credenciales inválidas devuelve null.

Si quieres, puedo añadir tests guía vacíos para que los estudiantes ejecuten y vean rápidamente el efecto de su implementación.

### Buen trabajo
Incluye mensajes claros `// TODO (Alumno): ...` en los métodos que implementes. Si quieres, puedes añadir tus propios tests en `src/miTest` para validar pasos parciales.

Si necesitas que deje algunos tests guía pasando (por ejemplo, que `LectorArchivo` cargue estudiantes mínimos), dímelo y ajusto los esqueletos para que validen pasos intermedios.
