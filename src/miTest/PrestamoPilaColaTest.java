package miTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import datos.Fecha;
import datos.FamiliaTipo;
import datos.Prestamo;
import negocios.Universidad;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

public class PrestamoPilaColaTest {

    @Test
    public void testPilaColaPrestamos() throws PosicionIlegalException {
        Universidad u = new Universidad();

        // crear estudiante y productos de prueba
        int codigo = 9999;
        u.agregarEstudiante(codigo, "Alumno Test", "test@example.com", new Fecha("1/1/1990"), "M", "CS");
        u.agregarProducto(FamiliaTipo.MUSICALES, 300, "GuitarraTest");
        u.agregarProducto(FamiliaTipo.DEPORTIVOS, 301, "PelotaTest");

        Fecha f1 = new Fecha("01/10/2025");
        Fecha f2 = new Fecha("02/10/2025");

        boolean ok1 = u.prestarProducto(codigo, 300, f1);
        assertTrue(ok1, "Primer préstamo debe registrarse correctamente");

        boolean ok2 = u.prestarProducto(codigo, 301, f2);
        assertTrue(ok2, "Segundo préstamo debe registrarse correctamente");

        // El último préstamo debe ser el segundo
        Prestamo ultimo = u.obtenerUltimoPrestamoEstudiante(codigo);
        assertNotNull(ultimo, "Debe existir un último préstamo");
        assertEquals(301, ultimo.getProductoId(), "El último préstamo debe corresponder al producto 301");
        Fecha fp = ultimo.getFechaPrestamo();
        assertEquals(2, fp.getDia());
        assertEquals(10, fp.getMes());
        assertEquals(2025, fp.getAnio());

        // Historial completo (cola) debe contener ambos préstamos en orden cronológico
        ListaDoble<Prestamo> historial = u.obtenerPrestamosEstudiante(codigo);
        assertNotNull(historial);
        assertEquals(2, historial.getTamanio(), "Historial debe contener 2 préstamos");
        assertEquals(300, historial.getValor(0).getProductoId());
        assertEquals(301, historial.getValor(1).getProductoId());
    }
}
