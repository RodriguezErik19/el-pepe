package miTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import listaDoble.PosicionIlegalException;
import miPrincipal.modelo.Libreria;
import miPrincipal.modelo.Libro;
import miPrincipal.modelo.Pedido;


import utilerias.Fecha;


public class AppTest {
    private Libreria libreria;
    private Libro libro1;
    private Libro libro2;
    private Libro libro3;
    private Libro libro4;
    private Libro libro5;

    @Before
    public void setUp() {
        libreria = new Libreria();
        libro1 = new Libro("Cien años de soledad", "Gabriel García Márquez", "978-3-16-148410-0");
        libro2 = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "978-0-14-243723-0");
        libro3 = new Libro("Orgullo y prejuicio", "Jane Austen", "978-0-19-953556-9");
        libro4 = new Libro("1984", "George Orwell", "978-0-452-28423-4");
        libro5 = new Libro("Matar a un ruiseñor", "Harper Lee", "978-0-06-112008-4");
    }

    @Test
    public void testAgregarLibro() {
        libreria.agregarLibro(libro1);
        assertTrue(libreria.obtenerLibros().contiene(libro1)); //falta hacer ese método contener
    }
    
    @Test
    public void testMostrarLibrosLista() {
        libreria.agregarLibro(libro1);
        libreria.agregarLibro(libro2);
        assertEquals(2, libreria.obtenerLibros().getTamanio());
    }
    
    @Test
    public void testAgregarLibroCola() {
       
        assertTrue(libreria.agregarLibroCola(libro1));
        
     
    }
    
    @Test
    public void testObtenerLibroCola() {
        
        libreria.agregarLibroCola(libro1);
        libreria.agregarLibroCola(libro2);
        assertEquals(libro1, libreria.obtenerLibroCola());
    }
    
    @Test //ESTE TEST ES INCORRECTO
    public void testEliminarUltimoLibro() throws PosicionIlegalException {
        libreria.agregarLibro(libro1);
        libreria.agregarLibro(libro2);
        assertEquals(libro2, libreria.eliminarUltimoLibro());
        assertFalse(libreria.obtenerLibros().contiene(libro2));
        assertEquals(libro2,libreria.obtenerLibroPila());
    }
    
    
    @Test
    public void testDeshacerEliminacion() throws PosicionIlegalException {
        libreria.agregarLibro(libro1);
        libreria.eliminarUltimoLibro();
        assertEquals(libro1, libreria.deshacerEliminarLibro());
        assertTrue(libreria.obtenerLibros().contiene(libro1));
        assertNull(libreria.obtenerLibroPila());  
    }
    
    @Test
    public void testDevolverLibro() throws PosicionIlegalException{
        libreria.agregarLibro(libro1);
        assertTrue(libreria.devolverLibro(libro1));
        assertFalse(libreria.obtenerLibros().contiene(libro1));
    }
    

    @Test
    public void testCrearPedido() {
        libreria.agregarLibro(libro1);
        Pedido pedido = libreria.crearPedido(libro1, new Fecha());
        assertNotNull(pedido);
        assertEquals(libro1, pedido.getLibro());
       
    }
    

   
}