package datos;

import java.io.IOException;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

/**
 * Interfaz que expone las operaciones de persistencia necesarias para la aplicación.
 * Permite desacoplar `UniversidadService` de la implementación concreta basada en archivos.
 */
public interface IUniversidadDAO {

    Producto obtenerProductoPorIdYFamilia(int id, FamiliaTipo familia) throws PosicionIlegalException;

    void guardarPrestamo(Prestamo p) throws IOException;

    ListaDoble<Prestamo> cargarPrestamos() throws IOException;

    boolean eliminarProductoPorId(int id) throws IOException;

    boolean eliminarEstudiantePorCodigo(int codigo) throws IOException;

    boolean limpiarPrestamosPorProductoOEstudiante(int productoId, int estudianteCodigo) throws IOException;

    /**
     * Actualiza la fecha de devolución de un préstamo persistido que corresponde
     * al producto y estudiante indicados. Si encuentra una línea sin fecha de
     * devolución la actualiza in-place (escribir en un temporal y renombrar).
     * Retorna true si se realizó alguna actualización.
     */
    boolean actualizarPrestamoDevolucion(int productoId, int estudianteCodigo, Fecha fechaDevolucion) throws IOException;

    /**
     * Persiste (reescribe) el fichero de estudiantes desde la lista en memoria.
     * Retorna true si la operación modificó el archivo.
     */
    boolean persistirEstudiantes(ListaDoble<Estudiante> estudiantes) throws IOException;

    /**
     * Persiste (reescribe) los ficheros de productos por familia desde las listas en memoria.
     * Retorna true si la operación modificó al menos un archivo.
     */
    boolean persistirProductos(ListaDoble<ProductosDeportivos> deportivos,
                                ListaDoble<ProductosEscenicos> escenicos,
                                ListaDoble<ProductosMusicales> musicales) throws IOException;

}
