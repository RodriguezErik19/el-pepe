package miTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import datos.UniversidadDAO;
import datos.Fecha;
import negocios.Universidad;
import negocios.UniversidadService;
import datos.Prestamo;

public class PrestamoPersistenciaReconstruccionTest {

    @Test
    public void testPersistirYReconstruirPrestamoEnMemoria() throws Exception {
        int productoId = 999999;
        int estudianteCodigo = 20240001; // estudiante existente en datos de prueba
        Fecha fecha = new Fecha("30/10/2025");

        Universidad origen = new Universidad();
        // agregar producto en memoria en la instancia origen (no necesitamos persistir archivo de productos)
        origen.agregarProducto(datos.FamiliaTipo.MUSICALES, productoId, "Instrumento Persistencia Test");

        UniversidadDAO dao = new UniversidadDAO();
        // limpiar cualquier prestamo previo para el producto (asegurar id único)
        try { dao.limpiarPrestamosPorProductoOEstudiante(productoId, -1); } catch (Exception e) { /* ignore */ }

        UniversidadService servicio = new UniversidadService(origen, dao, "src/miTest/archivos", "test", "test@example.com");

        // crear y persistir préstamo (servicio persistirá el préstamo mediante DAO)
        boolean ok = servicio.prestarProducto(estudianteCodigo, productoId, fecha);
        assertTrue(ok, "El préstamo inicial debe registrarse correctamente");

        // Ahora crear una nueva UniversidadService (simula reinicio) y comprobar reconstrucción
        UniversidadService servicioReload = new UniversidadService(new Universidad(), dao, "src/miTest/archivos", "test", "test@example.com");

        // Debe existir un préstamo activo para el producto en la nueva instancia
        datos.Prestamo p = servicioReload.obtenerPrestamoActivoPorProducto(productoId);
        assertNotNull(p, "El préstamo debe reconstruirse desde persistencia en la nueva instancia");
        assertEquals(productoId, p.getProductoId(), "El préstamo reconstruido debe referir el mismo producto");
        assertNotNull(p.getEstudiante(), "El préstamo debe conservar información del estudiante");
        assertEquals(estudianteCodigo, p.getEstudiante().getCodigo(), "El código de estudiante debe coincidir");
        assertEquals(fecha.toString(), p.getFechaPrestamo().toString(), "La fecha de préstamo debe coincidir");
    }
}
