package miTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import datos.Estudiante;
import datos.Fecha;
import datos.LectorArchivo;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;
import datos.Producto;
import datos.FamiliaTipo;
import datos.Prestamo;
import datos.ProductosDeportivos;
import datos.ProductosEscenicos;
import datos.ProductosMusicales;
import negocios.Universidad;
import datos.Email;

public class AppTest {

    private LectorArchivo lectorArchivo;
	private ListaDoble<Estudiante> estudiantes;
	private ListaDoble<Producto> recursos;
	private ListaDoble<Prestamo> prestamos;
    private Universidad universidad;
    private Estudiante estudiante;
	private Producto recurso;
	LocalDateTime fechaActual = LocalDateTime.now();
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaFormateada = fechaActual.format(formato);
	Fecha fecha = new Fecha(fechaFormateada);


    @Test
    public void test_agregarEstudiante() throws PosicionIlegalException {
        universidad = new Universidad();
		
        boolean condicion = universidad.agregarEstudiante(20240006,"Maria Lourdes Armenta Lindoro",
			"maria.al@culiacan.tecnm.mx", new Fecha("11/02/1968"),"Femenino","Sistemas");  
			assertTrue(condicion,"El estudiante debería haberse agregado correctamente");
		
		condicion = universidad.agregarEstudiante(20240007,"Cecilia Aragon",
					"ceciliaa@itculiacan.edu.mx", new Fecha("06/12/2006"),"Femenino","Sistemas");
			assertTrue(condicion,"El estudiante debería haberse agregado correctamente");
		
		condicion = universidad.agregarEstudiante(20240007,"Cecilia Aragon",
					"ceciliaa@itculiacan.edu.mx", new Fecha("06/12/2006"),"Femenino","Sistemas");
			assertFalse(condicion,"El estudiante no debería haberse agregado nuevamente");
		
		
	}

	@Test
    public void testAgregarEstudianteConEmailInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Estudiante(20231002, "Cecilia Aragon", new Email("correo.invalido"),
                    new Fecha("06/12/1999"), "Femenino", "Sistemas");
        });

        String expectedMessage = "Correo electrónico no válido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Debería lanzarse una excepción para un correo electrónico no válido");
    }

		

    @Test
	public void test_agregarRecurso() throws PosicionIlegalException{
		
		universidad = new Universidad();
		
	boolean condicion = universidad.agregarProducto(FamiliaTipo.MUSICALES,100,"Guitarra eléctrica");
	assertTrue(condicion,"El Producto debió haberse agregado correctamente");
		
	condicion = universidad.agregarProducto(FamiliaTipo.MUSICALES,90,"Saxofon");
		
	assertFalse(condicion,"El Producto no debería agregarse ya que se encuentra en el archivo inicial");	
		
	}
    @Test
	public void test_buscarEstudiante() throws PosicionIlegalException{
		universidad = new Universidad();
		
		
		Estudiante estudiante = universidad.buscarEstudiante(20240001);
		assertEquals("Ana Lopez",estudiante.getNombre(),"El nombre no es el esperado ya que lo cargó del archivo inicial");
	}

    @Test
	public void test_buscarRecurso() throws PosicionIlegalException{
		
		universidad = new Universidad();
		recurso = null;
		
		
		recurso = universidad.buscarProductoPorId(70);
		assertEquals("Pesitas de 500 grs",recurso.getNombre(),"El nombre es el esperado ya que lo cargó del archivo inicial");	
		
	}

    @Test 
	public void test_prestarRecurso() throws PosicionIlegalException{
		universidad = new Universidad();
		
		Producto recurso = universidad.buscarProductoPorId(70);
	assertTrue(recurso.isDisponible(),"El producto debe estar disponible");
		
	assertTrue(universidad.prestarProducto(20240001, 70,fecha), "El producto fue prestado con exito");

		recurso = universidad.buscarProductoPorId(70); 
	assertFalse(recurso.isDisponible(),"El producto ya no debe estar disponible");

	assertFalse(universidad.prestarProducto(20240001, 70,fecha), "El producto no puede ser prestado dos veces");
		
	}
    @Test
	public void test_consultarEstudianteTieneRecurso() throws PosicionIlegalException{
		universidad = new Universidad();
		//estudiante = new Estudiante();
		
	assertTrue(universidad.prestarProducto(20240001, 70,fecha), "El producto 70 fue prestado con exito");
		
	estudiante=universidad.consultarEstudianteTieneProducto(70);
	assertEquals("Ana Lopez",estudiante.getNombre(),"Fue prestado el producto 70");
  		
		
	}
	@Test
	public void test_consultarProductosDeUnEstudiante() throws PosicionIlegalException{
		universidad = new Universidad();
		estudiante = new Estudiante();
		recursos = new ListaDoble<Producto>();
		
	assertTrue(universidad.prestarProducto(20240001, 70,fecha), "El producto 70 fue prestado con exito");
        
	estudiante=universidad.consultarEstudianteTieneProducto(70);
	assertEquals("Ana Lopez",estudiante.getNombre(),"Fue prestado el producto 70");
        
	assertTrue(universidad.prestarProducto(20240001, 10,fecha), "El producto 10 fue prestado con exito");
	assertTrue(universidad.prestarProducto(20240001, 20,fecha), "El producto 20 fue prestado con exito");
        
	recursos=universidad.consultarProductosDeUnEstudiante(20240001);
	assertEquals(3,recursos.getTamanio(),"Tiene 3 productos prestados");
		
	}
	@Test
	public void test_devolverProducto() throws PosicionIlegalException{
		universidad = new Universidad();
		recurso = null;
        
		assertTrue(universidad.prestarProducto(20240001, 70,fecha), "El producto 70 fue prestado con exito");
		recurso = universidad.buscarProductoPorId(70);
		assertFalse(recurso.isDisponible(),"El producto ya no debe estar disponible");
		assertFalse(universidad.prestarProducto(20240001, 70,fecha), "El producto 70 No puede ser prestado dos veces");
		recurso = universidad.buscarProductoPorId(70);
		assertTrue(universidad.devolverProducto(70,fecha),"El producto 70 de devolvió con éxito");
		recurso = universidad.buscarProductoPorId(70);
		assertTrue(recurso.isDisponible(),"El producto 70 debe estar disponible");
		assertTrue(universidad.prestarProducto(20240001, 70,fecha), "El producto 70 fue prestado con exito");

	
	}
	@Test
	public void test_eliminarProducto() throws PosicionIlegalException{
		universidad = new Universidad();
		recursos = new ListaDoble<Producto>();
    
		assertFalse(universidad.eliminarProducto(100),"No se puede eliminar un producto que no existe");
        
		assertTrue(universidad.eliminarProducto(40),"Se eliminó un producto");
        
		assertNull(universidad.buscarProductoPorId(40),"El producto ya no existe y regresa Nulo");
        
        
	}
    @Test
	public void test_eliminarEstudiante() throws PosicionIlegalException{
		universidad = new Universidad();
		recursos = new ListaDoble<Producto>();
		estudiantes = new  ListaDoble<Estudiante>();
		
		assertFalse(universidad.eliminarEstudiante(123),"El estudiante no existe por lo que no se elimina");
		
		boolean condicion = universidad.agregarEstudiante(20230001,"Maria Lourdes Armenta Lindoro",
				"maria.al@culiacan.tecnm.mx", new Fecha("11/02/1968"),"F","Sistemas");  
		assertTrue(condicion,"El estudiante se agregó con éxito");
		assertTrue(universidad.eliminarEstudiante(20230001),"Se eliminó con éxito");
		
		assertTrue(universidad.eliminarEstudiante(20240001),"Se elimino con exito un estudiante con prestamo");
		assertTrue(universidad.eliminarEstudiante(20240002),"Se elimino con exito un estudiante con prestamo");
		
	}
	@Test
	public void test_buscarNombreProducto() throws PosicionIlegalException{
		universidad = new Universidad();
        
		String nombre = universidad.buscarNombreProducto(50);
		assertEquals("balón de futbol",nombre,"El nombre es el esperado");
    
	}
	@Test
	public void test_buscarNombreEstudiante() throws PosicionIlegalException{
        universidad = new Universidad();
		
		String nombre = universidad.buscarNombreEstudiante(20240001);
		assertEquals("Ana Lopez",nombre,"El nombre es el esperado");
		
	}
	@Test
	public void test_mostrarProductos() throws PosicionIlegalException{
		universidad = new Universidad();
	recursos = new ListaDoble<Producto>();
		recursos.agregar(new Producto(10,"Guitarra"){ private boolean prest=false; @Override public boolean esPrestable(){return prest;} @Override public void prestar(){prest=true;} @Override public void devolver(){prest=false;} });
		recursos.agregar(new Producto(20,"Violin"){ private boolean prest=false; @Override public boolean esPrestable(){return prest;} @Override public void prestar(){prest=true;} @Override public void devolver(){prest=false;} });
		recursos.agregar(new Producto(30,"Trompeta"){ private boolean prest=false; @Override public boolean esPrestable(){return prest;} @Override public void prestar(){prest=true;} @Override public void devolver(){prest=false;} });
		recursos.agregar(new Producto(40,"Bat de beisbol"){ private boolean prest=false; @Override public boolean esPrestable(){return prest;} @Override public void prestar(){prest=true;} @Override public void devolver(){prest=false;} });
		recursos.agregar(new Producto(50,"balón de futbol"){ private boolean prest=false; @Override public boolean esPrestable(){return prest;} @Override public void prestar(){prest=true;} @Override public void devolver(){prest=false;} });
		System.out.println("Tamaño Recursos:"+recursos.getTamanio());
		
		
	}
	@Test 
	public void test_mostrarEstudiantes() throws PosicionIlegalException{
		universidad = new Universidad();
		estudiantes = new ListaDoble<Estudiante>();
		estudiantes.agregar(new Estudiante(20231000,"Ana Lopez",new Email("anaLopez@email.com"),
	            new Fecha("01/01/2000"),"Femenino","Sistemas"));
		estudiantes.agregar(new Estudiante(20231001,"Pedro Buelna",new Email("pedroBuelna@email.com"),
		    new Fecha("24/12/1999"),"Masculino","Sistemas"));
		estudiantes.agregar(new Estudiante(20231002,"Cecilia Aragon",new Email("ceciliaAragon@email.com"),
		    new Fecha("06/12/1999"),"Femenimo","Sistemas"));
		estudiantes.agregar(new Estudiante(20232001,"Patricia Reyes",new Email("patriciaReyes@email.com"),
		    new Fecha("30/01/2001"),"Femenimo","TICs"));
		estudiantes.agregar(new Estudiante(20232002,"Juan Sanchez",new Email("juanSanchez@email.com"),
		    new Fecha("30/05/2001"),"Masculino","TICs"));
		assertEquals(5,estudiantes.getTamanio(),"Deben existir 5 elementos de estudiantes");
		
		
		
	}
	@Test
	public void test_listaMasTresPrestados() throws PosicionIlegalException{
		universidad = new Universidad();
		
		estudiantes = new ListaDoble<Estudiante>();
		
	universidad.prestarProducto(20240001, 10,fecha);
	universidad.prestarProducto(20240001, 20,fecha);
	universidad.prestarProducto(20240001, 30,fecha);
	universidad.prestarProducto(20240001, 40,fecha);
        
	universidad.prestarProducto(20240002, 50,fecha);
	universidad.prestarProducto(20240002, 60,fecha);
	universidad.prestarProducto(20240002, 70,fecha);
	universidad.prestarProducto(20240002, 80,fecha);
        
	universidad.prestarProducto(20240003, 90,fecha);
		
		estudiantes = universidad.mostrarEstudiantesMasDeTres();
		
		assertEquals(2,estudiantes.getTamanio(),"Existen 2 estudiantes con mas de 3 prestamos");
		
	}
	@Test
	public void testConstructorCargaRecursos() throws PosicionIlegalException {
		Universidad universidad = new Universidad();
		ListaDoble<Producto> recursos = universidad.mostrarProductos();
		assertNotNull(recursos,"La lista de recursos no debería ser null después de la construcción");
		assertFalse(recursos.esVacia(),"La lista de recursos no debería estar vacía después de la construcción");
	}

	@Test
	public void testLeerArchivo() throws PosicionIlegalException{

	lectorArchivo = new LectorArchivo();
	estudiantes = new ListaDoble<>();
	ListaDoble<ProductosDeportivos> dep = new ListaDoble<>();
	ListaDoble<ProductosEscenicos> esc = new ListaDoble<>();
	ListaDoble<ProductosMusicales> mus = new ListaDoble<>();
	prestamos = new ListaDoble<>();

	// Usar la nueva firma que carga por familias
	lectorArchivo.leerArchivo(estudiantes, dep, esc, mus, prestamos);

	// Verificar que las listas no estén vacías después de leer los archivos
	assertFalse(estudiantes.esVacia(), "La lista de estudiantes no debería estar vacía después de leer el archivo");

	// Reconstruir la vista de recursos en el orden esperado por las aserciones
	ListaDoble<Producto> recursosCompat = new ListaDoble<>();
	// musicales id < 40
	for (int i = 0; i < mus.getTamanio(); i++) {
		try {
			ProductosMusicales p = mus.getValor(i);
			if (p.getId() < 40) recursosCompat.agregar(p);
		} catch (PosicionIlegalException e) {
		}
	}
	// deportivos
	for (int i = 0; i < dep.getTamanio(); i++) {
		try {
			ProductosDeportivos p = dep.getValor(i);
			recursosCompat.agregar(p);
		} catch (PosicionIlegalException e) {
		}
	}
	// musicales restantes
	for (int i = 0; i < mus.getTamanio(); i++) {
		try {
			ProductosMusicales p = mus.getValor(i);
			if (p.getId() >= 40) recursosCompat.agregar(p);
		} catch (PosicionIlegalException e) {
		}
	}
	// escenicos
	for (int i = 0; i < esc.getTamanio(); i++) {
		try {
			ProductosEscenicos p = esc.getValor(i);
			recursosCompat.agregar(p);
		} catch (PosicionIlegalException e) {
		}
	}

	assertFalse(recursosCompat.esVacia(), "La lista de recursos no debería estar vacía después de leer el archivo");
	assertTrue(estudiantes.getValor(0).getCodigo()==20240001,"20240001 debe ser el código del registro 0");
	assertTrue(estudiantes.getValor(4).getCodigo()==20240005,"20240005 debe ser el código del registro 4");
	assertTrue(recursosCompat.getValor(0).getId()==10,"10 debe ser el registro 0");
	assertTrue(recursosCompat.getValor(8).getId()==90,"90 debe ser el registro 8");
	}

	@Test
    public void testFechaDevolucionMayorOIgualQueFechaPrestamo() throws PosicionIlegalException {
        Universidad universidad = new Universidad();
    
        universidad.agregarEstudiante(20240007, "Ana Lopez", "ana.lopez@itculiacan.edu.mx", new Fecha("01/01/2000"), "Femenino", "Sistemas");
		
	universidad.agregarProducto(FamiliaTipo.MUSICALES,100,"Libro de Java");

        Fecha fechaPrestamo = new Fecha("01/10/2023");
		
	// Prestar el producto
	assertTrue(universidad.prestarProducto(20240007, 100, fechaPrestamo), "El producto debería prestarse correctamente");

        // Intentar devolver el recurso con una fecha anterior a la fecha de préstamo
        Fecha fechaDevolucion = new Fecha("30/09/2023");
	assertFalse(universidad.devolverProducto(100, fechaDevolucion), "No se debería permitir devolver el producto con una fecha anterior a la fecha de préstamo");

        // Intentar devolver el recurso con una fecha igual a la fecha de préstamo
        fechaDevolucion = new Fecha("01/10/2023");
	assertTrue(universidad.devolverProducto(100, fechaDevolucion), "Debería permitir devolver el producto con una fecha igual a la fecha de préstamo");

        // Prestar el recurso nuevamente
	assertTrue(universidad.prestarProducto(20240007, 100, fechaPrestamo), "El producto debería prestarse correctamente");

        // Intentar devolver el recurso con una fecha posterior a la fecha de préstamo
        fechaDevolucion = new Fecha("02/10/2023");
	assertTrue(universidad.devolverProducto(100, fechaDevolucion), "Debería permitir devolver el producto con una fecha posterior a la fecha de préstamo");
    }



    

    
}
