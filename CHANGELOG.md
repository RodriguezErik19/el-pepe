# Changelog

## Unreleased

- Migración: reemplazada implementación de lista simple (`src/datos/Lista`, `Nodo`, `PosicionIlegalException`) por `negocios.utilidades.listaDoble.ListaDoble` (lista doblemente ligada).
- Se actualizó el código, pruebas y UIs para usar `ListaDoble<T>` y la excepción unificada `negocios.utilidades.listaDoble.PosicionIlegalException`.
- Se eliminó el código legado `src/datos/Lista.java`, `src/datos/Nodo.java` y `src/datos/PosicionIlegalException.java`.
- Se actualizaron comentarios y documentación para referenciar `ListaDoble` en lugar de `Lista`.

> Ejecutado automáticamente por el migrador de listas — confirme y ajuste el changelog si desea más detalles.
