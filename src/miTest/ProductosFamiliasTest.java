package miTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import datos.Fecha;
import datos.FamiliaTipo;
import negocios.utilidades.listaDoble.PosicionIlegalException;
import datos.Producto;
// Importaciones mínimas usadas por las pruebas
import negocios.Universidad;

public class ProductosFamiliasTest {

    @Test
    public void testAgregarYBuscarPorFamilia() throws PosicionIlegalException {
        Universidad u = new Universidad();
        boolean added = u.agregarProducto(FamiliaTipo.DEPORTIVOS, 200, "Pelota Test");
        assertTrue(added, "El producto debe agregarse");
        Producto p = u.buscarProductoPorId(200);
        assertNotNull(p);
        assertEquals(200, p.getId());
        assertEquals("Pelota Test", p.getNombre());
    }

    @Test
    public void testPrestarDevolverPorFamilia() throws PosicionIlegalException {
        Universidad u = new Universidad();
        u.agregarProducto(FamiliaTipo.MUSICALES, 201, "Ukelele Test");
        Producto p = u.buscarProductoPorId(201);
        assertTrue(p.isDisponible(), "Debe estar disponible al inicio");
    Fecha f = new Fecha("01/01/2025");
    assertTrue(u.prestarProducto(20240001, 201, f));
        p = u.buscarProductoPorId(201);
        assertFalse(p.isDisponible(), "No debe estar disponible luego de prestarse");
    assertTrue(u.devolverProducto(201, f));
        p = u.buscarProductoPorId(201);
        assertTrue(p.isDisponible(), "Debe volver a estar disponible");
    }

    @Test
    public void testBuscarPorFamiliaExplicita() throws PosicionIlegalException {
        Universidad u = new Universidad();
        u.agregarProducto(FamiliaTipo.ESCENICOS, 300, "Traje Test");
        Producto p = u.buscarProductoPorIdYFamilia(FamiliaTipo.ESCENICOS, 300);
        assertNotNull(p);
        assertEquals(300, p.getId());
    }

}
