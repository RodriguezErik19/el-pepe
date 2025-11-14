package miTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import datos.UniversidadDAO;
import negocios.Universidad;
import negocios.UniversidadService;
import datos.FamiliaTipo;
import datos.Estudiante;
import datos.Fecha;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

public class PersistenciaIndexTest {

    @Test
    public void testPersistirEstudiantesRewritesFile() throws Exception {
        Universidad u = new Universidad();
        int codigo = 999900;
        u.agregarEstudiante(codigo, "Prueba Test", "test@example.com", new Fecha("1/1/1990"), "M", "CS");
        UniversidadDAO dao = new UniversidadDAO();
        dao.persistirEstudiantes(u.mostrarEstudiantes());
        File f = new File("src/miTest/archivos/estudiantes.txt");
        assertTrue(f.exists(), "El archivo de estudiantes debe existir en ruta de pruebas");
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith(String.valueOf(codigo))) { found = true; break; }
            }
        }
        assertTrue(found, "El estudiante agregado debe encontrarse en el archivo de pruebas");
    }

    @Test
    public void testPersistirProductosRewritesFile() throws Exception {
        Universidad u = new Universidad();
        int id = 999901;
        u.agregarProducto(FamiliaTipo.MUSICALES, id, "Instrumento Test");
        UniversidadDAO dao = new UniversidadDAO();
        dao.persistirProductos(u.mostrarProductosDeportivos(), u.mostrarProductosEscenicos(), u.mostrarProductosMusicales());
        File f = new File("src/miTest/archivos/ProductosMusicales.txt");
        assertTrue(f.exists(), "El archivo de musicales debe existir en ruta de pruebas");
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith(String.valueOf(id))) { found = true; break; }
            }
        }
        assertTrue(found, "El producto agregado debe encontrarse en el archivo de musicales");
    }

    @Test
    public void testIndiceInvertidoEncuentraProducto() throws PosicionIlegalException {
        Universidad u = new Universidad();
        int id = 888888;
        u.agregarProducto(FamiliaTipo.MUSICALES, id, "Guitarra Special");
        ListaDoble<datos.Producto> res = u.buscarProductosPorPalabraClave("guitarra");
        boolean found = false;
        for (int i = 0; i < res.getTamanio(); i++) {
            if (res.getValor(i).getId() == id) { found = true; break; }
        }
        assertTrue(found, "La búsqueda por token debe devolver el producto recién agregado");
    }
}
