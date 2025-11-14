package miTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import datos.FamiliaTipo;
import datos.Producto;
import negocios.Universidad;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

public class BuscarIndiceInvertidoTest {

    private boolean contieneId(ListaDoble<Producto> lst, int id) throws PosicionIlegalException {
        if (lst == null) 
            return false;
        for (int i = 0; i < lst.getTamanio(); i++) {
            if (lst.getValor(i).getId() == id) return true;
        }
        return false;
    }

    @Test
    public void testIndiceEncuentraProductoAlAgregar() throws PosicionIlegalException {
        Universidad u = new Universidad();
        int id = 901234;
        u.agregarProducto(FamiliaTipo.MUSICALES, id, "Guitarra Special");

        ListaDoble<Producto> res = u.buscarProductosPorPalabraClave("guitarra");
        assertTrue(contieneId(res, id), "El índice debe devolver el producto añadido por token 'guitarra'");
    }

    @Test
    public void testIndiceCaseInsensitive() throws PosicionIlegalException {
        Universidad u = new Universidad();
        int id = 901235;
        u.agregarProducto(FamiliaTipo.MUSICALES, id, "GUItarra Test Case");

        ListaDoble<Producto> res = u.buscarProductosPorPalabraClave("guitarra");
        assertTrue(contieneId(res, id), "Búsqueda debe ser case-insensitive y encontrar 'GUItarra'");
    }

    @Test
    public void testIndiceNoResultados() throws PosicionIlegalException {
        Universidad u = new Universidad();
        ListaDoble<Producto> res = u.buscarProductosPorPalabraClave("token_inexistente_12345");
        assertNotNull(res);
        assertEquals(0, res.getTamanio(), "Buscar un token inexistente debe devolver 0 resultados");
    }

    @Test
    public void testIndiceActualizaAlEliminar() throws PosicionIlegalException {
        Universidad u = new Universidad();
        int id = 901236;
        u.agregarProducto(FamiliaTipo.MUSICALES, id, "Guitarra Temporal");

        ListaDoble<Producto> res1 = u.buscarProductosPorPalabraClave("guitarra");
        assertTrue(contieneId(res1, id), "Producto debe encontrarse antes de eliminar");

        // eliminar y comprobar que ya no aparece
        u.eliminarProducto(id);
        ListaDoble<Producto> res2 = u.buscarProductosPorPalabraClave("guitarra");
        assertFalse(contieneId(res2, id), "Producto no debe aparecer en el índice después de eliminarlo");
    }
}
