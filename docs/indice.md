# Índice invertido — guía rápida

Este documento muestra cómo usar el índice invertido del proyecto y ejemplos
rápidos de la API `buscarProductosPorPalabraClave`.

Resumen técnico
- Implementación: `negocios.utilidades.hashMapCombinada.HashMapCombinada<T>`.
  Internamente usa un `java.util.HashMap<String, LinkedList<T>>` para mapear
  tokens normalizados a listas de `Producto`.
- Dónde se indexa: `src/negocios/Universidad.java` → método `indexarProducto(Producto)`.
  La indexación inicial ocurre en el constructor de `Universidad` al cargar
  los productos desde los ficheros.

Ejemplo de uso (modo de aplicación)

1) Crear/instanciar la universidad (la carga lee los ficheros según `usuario.properties`):

```java
Universidad uni = new Universidad();
```

2) Buscar por palabra clave (case-insensitive, tokenizada):

```java
ListaDoble<Producto> resultados = uni.buscarProductosPorPalabraClave("guitarra");
for (int i = 0; i < resultados.getTamanio(); i++) {
    Producto p = resultados.getValor(i);
    System.out.println("Id: " + p.getId() + " Descripcion: " + p.getNombre());
}
```

Notas prácticas
- `buscarProductosPorPalabraClave` normaliza el token (`TextUtil.normalizar`) y
  devuelve una `ListaDoble<Producto>` construida a partir de la `LinkedList`
  interna del índice.
- La indexación se realiza sobre tokens extraídos de `producto.getNombre()` y
  `descripcion` (si existe). Si se añade una operación de edición de producto,
  hay que asegurarse de llamar a `desindexarProducto(prod)` antes de la mutación
  y `indexarProducto(prod)` después para mantener la consistencia.

Referencias
- Implementación del mapa: `src/negocios/utilidades/hashMapCombinada/HashMapCombinada.java`
- Indexación y búsqueda: `src/negocios/Universidad.java` (`indexarProducto`,
  `desindexarProducto`, `buscarProductosPorPalabraClave`)
- Documentación general: `README.MD` (sección "Índice invertido — implementación y ubicación")
