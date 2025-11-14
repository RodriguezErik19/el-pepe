package miTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import datos.Estudiante;
import datos.FamiliaTipo;
import datos.Fecha;
import datos.Prestamo;
import datos.Producto;
import negocios.Universidad;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

public class PrestamoSinEstudianteTest {

    @Test
    public void testConsultarProductosCuandoEstudianteNoEstaEnLista() throws PosicionIlegalException {
        Universidad u = new Universidad();

        int codigo = 20249999; // código que no existe en los fixtures por defecto

        // crear un estudiante parcial (solo código) y un préstamo asociado al producto 40
        Estudiante parcial = new Estudiante();
        parcial.setCodigo(codigo);
        Fecha fecha = new Fecha("04/11/2025");
        Prestamo p = new Prestamo(40, FamiliaTipo.DEPORTIVOS, parcial, fecha);

        // importar el préstamo a la universidad (reconstruirá la lista de prestamos)
        ListaDoble<Prestamo> lista = new ListaDoble<>();
        lista.agregar(p);
        u.importarPrestamos(lista);

        ListaDoble<Producto> productos = u.consultarProductosDeUnEstudiante(codigo);
        assertNotNull(productos, "La lista de productos no debe ser null");
        boolean found = false;
        for (int i = 0; i < productos.getTamanio(); i++) {
            Producto prod = productos.getValor(i);
            if (prod != null && prod.getId() == 40) { found = true; break; }
        }
        assertTrue(found, "Se debe encontrar el producto 40 en los préstamos del código " + codigo);
    }
}
