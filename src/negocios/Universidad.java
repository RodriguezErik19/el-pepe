package negocios;
import datos.Estudiante;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;
import datos.Prestamo;
import datos.Producto;
import datos.ProductosDeportivos;
import datos.ProductosEscenicos;
import datos.ProductosMusicales;
import datos.FamiliaTipo;
import datos.Fecha;
import datos.Email;
import datos.Familia;

import datos.LectorArchivo;
import negocios.utilidades.hashMapCombinada.HashMapCombinada;
import negocios.utilidades.TextUtil;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import negocios.utilidades.pila.Pila;
import negocios.utilidades.cola.Cola;

/**
 * La clase Universidad representa una universidad que maneja colecciones de estudiantes,
 * productos y préstamos. Internamente estas colecciones están implementadas con
 * {@link negocios.utilidades.listaDoble.ListaDoble} (lista doblemente ligada).
 * Proporciona métodos para gestionar estos datos.
 */
public class Universidad {
	private ListaDoble<Estudiante> estudiantes;
	private ListaDoble<ProductosDeportivos> productosDeportivos;
	private ListaDoble<ProductosEscenicos> productosEscenicos;
	private ListaDoble<ProductosMusicales> productosMusicales;

	private ListaDoble<Prestamo> prestamos;
	private Map<Integer, Pila<Prestamo>> prestamosPorEstudiantePila;
	private Map<Integer, Cola<Prestamo>> prestamosPorEstudianteCola;
	private HashMapCombinada<Producto> indicePalabras;
	/**
	* Constructor por defecto que inicializa las listas de estudiantes, productos y préstamos.
     */
	public Universidad() {
		estudiantes = new ListaDoble<Estudiante>();
		productosDeportivos= new ListaDoble<ProductosDeportivos>();
		productosEscenicos = new ListaDoble<ProductosEscenicos>();
		productosMusicales = new ListaDoble<ProductosMusicales>();

		prestamos = new ListaDoble<Prestamo>();
		// estructuras auxiliares por estudiante (pila = último, cola = historial)
		prestamosPorEstudiantePila = new HashMap<>();
		prestamosPorEstudianteCola = new HashMap<>();
		//invocar al archivo de productos
		LectorArchivo lector = new LectorArchivo();
        lector.leerArchivo(estudiantes, productosDeportivos,productosEscenicos, productosMusicales, prestamos);

		// construir índice invertido en memoria
		indicePalabras = new HashMapCombinada<>();
		try {
			for (int i = 0; i < productosDeportivos.getTamanio(); i++) 
				indexarProducto(productosDeportivos.getValor(i));
			for (int i = 0; i < productosEscenicos.getTamanio(); i++) 
				indexarProducto(productosEscenicos.getValor(i));
			for (int i = 0; i < productosMusicales.getTamanio(); i++) 
				indexarProducto(productosMusicales.getValor(i));
		} catch (PosicionIlegalException e) {
			// ignorar elementos inválidos durante indexación inicial
		}

				// Reconstruir pilas/colas por estudiante a partir de los préstamos cargados
		try {
			for (int i = 0; i < prestamos.getTamanio(); i++) {
				Prestamo p = prestamos.getValor(i);
				if (p != null && p.getEstudiante() != null) {
					int codigo = p.getEstudiante().getCodigo();
					
					Pila<Prestamo> pila = prestamosPorEstudiantePila.get(codigo);
					if (pila == null) { pila = new Pila<>(); prestamosPorEstudiantePila.put(codigo, pila); }
					pila.apilar(p);
					Cola<Prestamo> cola = prestamosPorEstudianteCola.get(codigo);
					if (cola == null) { cola = new Cola<>(); prestamosPorEstudianteCola.put(codigo, cola); }
					cola.encolar(p);
				}
			}
		} catch (PosicionIlegalException e) {
			// ignorar problemas al reconstruir estructuras
		}
	}

	/**
	 * Devuelve el último préstamo (evento más reciente) registrado para un estudiante.
	 */
	public Prestamo obtenerUltimoPrestamoEstudiante(int codigo) {
		Pila<Prestamo> pila = prestamosPorEstudiantePila.get(codigo);
		if (pila == null || pila.esVacia()) return null;
		return pila.cima();
	}

	/**
	 * Importa préstamos desde una lista (por ejemplo, cargada desde persistencia)
	 * y los integra en las estructuras del dominio: lista `prestamos`, pilas y colas
	 * por estudiante, y marca como prestados los productos que tengan préstamos
	 * activos (fechaDevolucion == null).
	 */
	public void importarPrestamos(negocios.utilidades.listaDoble.ListaDoble<datos.Prestamo> lista) {
		if (lista == null) return;
		try {
			for (int i = 0; i < lista.getTamanio(); i++) {
				datos.Prestamo p = lista.getValor(i);
				if (p == null) continue;
				// Si el préstamo trae sólo un estudiante parcial (solo código), intentar
				// resolver el Estudiante completo desde la lista de estudiantes en memoria
				if (p.getEstudiante() != null) {
					int codigo = p.getEstudiante().getCodigo();
					try {
						Estudiante estFull = buscarEstudiante(codigo);
						if (estFull != null) p.setEstudiante(estFull);
					} catch (PosicionIlegalException pie) {
						// ignorar y usar el objeto parcial si no se puede resolver
					}
				}
				// Añadir a la lista local de préstamos
				this.prestamos.agregar(p);
				// Actualizar pilas/colas por estudiante usando el Estudiante resuelto (si existe)
				if (p.getEstudiante() != null) {
					int codigo = p.getEstudiante().getCodigo();
					Pila<Prestamo> pila = prestamosPorEstudiantePila.get(codigo);
					if (pila == null) { pila = new Pila<>(); prestamosPorEstudiantePila.put(codigo, pila); }
					pila.apilar(p);
					Cola<Prestamo> cola = prestamosPorEstudianteCola.get(codigo);
					if (cola == null) { cola = new Cola<>(); prestamosPorEstudianteCola.put(codigo, cola); }
					cola.encolar(p);
				}
				// Si el préstamo está activo, marcar producto como prestado en memoria
				if (p.getFechaDevolucion() == null) {
					try {
						Producto prod = buscarProductoPorId(p.getProductoId());
						if (prod != null) prod.prestar();
					} catch (PosicionIlegalException pie) {
						// ignorar si no se puede localizar producto
					}
				}
			}
		} catch (PosicionIlegalException e) {
			// ignorar problemas al recorrer la lista entrante
		}
	}

	/**
	 * Devuelve el historial de préstamos de un estudiante como ListaDoble (orden cronológico).
	 */
	public ListaDoble<Prestamo> obtenerPrestamosEstudiante(int codigo) throws PosicionIlegalException {
		ListaDoble<Prestamo> res = new ListaDoble<>();
		for (int i = 0; i < prestamos.getTamanio(); i++) {
			Prestamo p = prestamos.getValor(i);
			if (p != null && p.getEstudiante() != null && p.getEstudiante().getCodigo() == codigo) res.agregar(p);
		}
		return res;
	}

	private void indexarProducto(Producto p) {
		/**
		 * Indexa un producto en el índice invertido (`indicePalabras`).
		 *
		 * Esta rutina tokeniza el nombre y la descripción del producto y añade
		 * el producto a la estructura `HashMapCombinada` por cada token.
		 *
		 * Nota para mantenedores: ver la sección "Índice invertido — implementación y ubicación"
		 * en `README.MD` para más contexto sobre la implementación (clase
		 * `negocios.utilidades.hashMapCombinada.HashMapCombinada`) y dónde se
		 * construye/actualiza el índice.
		 *
		 * @param p producto a indexar (puede ser null)
		 */
		if (p == null) 
			return;
		String descr = "";
		if (p instanceof ProductosDeportivos) 
			descr = ((ProductosDeportivos)p).getDescripcion();
		else if (p instanceof ProductosEscenicos) 
			descr = ((ProductosEscenicos)p).getDescripcion();
		else if (p instanceof ProductosMusicales) 
			descr = ((ProductosMusicales)p).getDescripcion();
		List<String> tokens = TextUtil.tokens(p.getNombre() + " " + (descr == null ? "" : descr));
		for (String t : tokens) {
			indicePalabras.poner(t, p);
		}
	}

	private void desindexarProducto(Producto p) {
		if (p == null) 
			return;
		String descr = "";
		if (p instanceof ProductosDeportivos) 
			descr = ((ProductosDeportivos)p).getDescripcion();
		else if (p instanceof ProductosEscenicos) 
			descr = ((ProductosEscenicos)p).getDescripcion();
		else if (p instanceof ProductosMusicales) 
			descr = ((ProductosMusicales)p).getDescripcion();
		List<String> tokens = TextUtil.tokens(p.getNombre() + " " + (descr == null ? "" : descr));
		for (String t : tokens) {
			indicePalabras.quitar(t, p);
		}
	}

	public ListaDoble<Producto> buscarProductosPorPalabraClave(String palabra) throws PosicionIlegalException{
		if (palabra == null) 
			return new ListaDoble<>();
		String tok = TextUtil.normalizar(palabra);
		java.util.LinkedList<Producto> lst = indicePalabras.obtener(tok);
		ListaDoble<Producto> res = new ListaDoble<>();
		if (lst == null) 
			return res;
		for (Producto p : lst) 
			res.agregar(p);
		return res;
	}

	
	/**
	 * 
	 * @param codigo
	 * @param nombre
	 * @param email
	 * @param fechaNac
	 * @param sexo
	 * @param programa
	 * @return
	 * @throws PosicionIlegalException
	 */

	public boolean agregarEstudiante(int codigo, String nombre,
			String email,Fecha fechaNac, String sexo, String programa)
					throws PosicionIlegalException{
		Estudiante est = buscarEstudiante(codigo);
		if (est == null) {
			estudiantes.agregar(new Estudiante(codigo, nombre,
					new Email(email), fechaNac, sexo, programa));
			return true;
		}
		return false;
			
	}

	/**
	 * Edita los datos de un estudiante existente. Los parámetros que sean null o
	 * cadenas vacías no se aplican (se mantienen los valores actuales).
	 *
	 * @return true si se encontró y actualizó el estudiante; false si no existe.
	 */
	public boolean editarEstudiante(int codigo, String nombre,
			String email, Fecha fechaNac, String sexo, String programa) throws PosicionIlegalException{
		Estudiante est = buscarEstudiante(codigo);
		if (est == null) return false;
		// Actualizar sólo los campos proporcionados
		if (nombre != null && !nombre.trim().isEmpty()) est.setNombre(nombre);
		if (email != null && !email.trim().isEmpty()) est.setEmail(new Email(email));
		if (fechaNac != null) est.setFechaNac(fechaNac);
		if (sexo != null && !sexo.trim().isEmpty()) est.setSexo(sexo);
		if (programa != null && !programa.trim().isEmpty()) est.setPrograma(programa);
		System.out.println("Estudiante " + codigo + " actualizado: " + est);
		return true;
	}

	
	/**
	 * 
	 * @param codigo
	 * @return
	 * @throws PosicionIlegalException
	 */

	public Estudiante buscarEstudiante(int codigo) throws PosicionIlegalException{
		for (int i=0;i<estudiantes.getTamanio();i++) {
			Estudiante est = estudiantes.getValor(i);
			if (est.getCodigo() == codigo) 
				return est;	
		}
		return null;
	}

	/**
	 * Busca un producto (deportivos, escenicos o musicales) por id
	 * Retorna un objeto Producto o null si no se encuentra.
	 */
	public Producto buscarProductoPorId(int id) throws PosicionIlegalException{
		for (int i = 0; i < productosDeportivos.getTamanio(); i++) {
			ProductosDeportivos p = productosDeportivos.getValor(i);
			if (p.getId() == id) return p;
		}
		for (int i = 0; i < productosEscenicos.getTamanio(); i++) {
			ProductosEscenicos p = productosEscenicos.getValor(i);
			if (p.getId() == id) return p;
		}
		for (int i = 0; i < productosMusicales.getTamanio(); i++) {
			ProductosMusicales p = productosMusicales.getValor(i);
			if (p.getId() == id) return p;
		}
		return null;
	}

	// (Se eliminó el método de compatibilidad para el antiguo tipo 'Recurso')

	/**
	 * Busca un producto por familia explícita
	 */
	public Producto buscarProductoPorIdYFamilia(FamiliaTipo fam, int id) throws PosicionIlegalException{
		switch(fam) {
			case DEPORTIVOS:
				for (int i = 0; i < productosDeportivos.getTamanio(); i++) {
					ProductosDeportivos p = productosDeportivos.getValor(i);
					if (p.getId() == id) return p;
				}
				break;
			case ESCENICOS:
				for (int i = 0; i < productosEscenicos.getTamanio(); i++) {
					ProductosEscenicos p = productosEscenicos.getValor(i);
					if (p.getId() == id) return p;
				}
				break;
			case MUSICALES:
				for (int i = 0; i < productosMusicales.getTamanio(); i++) {
					ProductosMusicales p = productosMusicales.getValor(i);
					if (p.getId() == id) return p;
				}
				break;
		}
		return null;
	}

	/**
	 * Agrega un producto a la lista correspondiente. IDs son únicos globalmente.
	 * Devuelve true si se agregó, false si el id ya existía.
	 */
	public boolean agregarProducto(FamiliaTipo fam, int id, String nombre) throws PosicionIlegalException{
		// IDs únicos globalmente
		if (buscarProductoPorId(id) != null) return false;
		Familia famObj = new Familia(0, fam.name(), "");
		switch(fam) {
			case DEPORTIVOS:
				ProductosDeportivos pd = new ProductosDeportivos(id, nombre, "", "", "", "", famObj, false);
				productosDeportivos.agregar(pd);
				indexarProducto(pd);
				return true;
			case ESCENICOS:
				ProductosEscenicos pe = new ProductosEscenicos(id, nombre, "", "", "", "", "", "", famObj, false);
				productosEscenicos.agregar(pe);
				indexarProducto(pe);
				return true;
			case MUSICALES:
				ProductosMusicales pm = new ProductosMusicales(id, nombre, "", "", "", "", famObj, false);
				productosMusicales.agregar(pm);
				indexarProducto(pm);
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param id
	 * @param nombre
	 * @return
	 * @throws PosicionIlegalException
	 */
	public boolean agregarProductoDeportivo(int id, String nombre) throws PosicionIlegalException{
		// Delegar a la función genérica que garantiza IDs únicos globalmente
		return agregarProducto(FamiliaTipo.DEPORTIVOS, id, nombre);
	}

	public ListaDoble<ProductosDeportivos> mostrarProductosDeportivos() throws PosicionIlegalException{
		
		return productosDeportivos;
	}

	public ListaDoble<ProductosEscenicos> mostrarProductosEscenicos() throws PosicionIlegalException{
		
		return productosEscenicos;
	}

	public ListaDoble<ProductosMusicales> mostrarProductosMusicales() throws PosicionIlegalException{
		
		return productosMusicales;
	}
 
	/**
	 * 
	 * @param id
	 * @return
	 * @throws PosicionIlegalException
	 */
	// Nota: la búsqueda de producto por id ya está cubierta por buscarProductoPorId
	// El método que antes devolvía el tipo antiguo fue eliminado en favor de `Producto`.

	/**
	 * 
	 * @param codigo
	 * @param id
	 * @param fecha
	 * @return
	 * @throws PosicionIlegalException
	 */
		public boolean prestarProducto(int codigo, int id, Fecha fecha) throws PosicionIlegalException{
    Estudiante est = buscarEstudiante(codigo);
    if (est == null) return false;
    
    Producto prod = buscarProductoPorId(id);
    if (prod == null) return false;
    
    if (!prod.isDisponible()) return false;
    
    Prestamo nuevoPrestamo = new Prestamo(est, id, fecha, null);
    prestamos.agregar(nuevoPrestamo);
    
    Pila<Prestamo> pila = prestamosPorEstudiantePila.get(codigo);
    if (pila == null) { pila = new Pila<>(); prestamosPorEstudiantePila.put(codigo, pila); }
    pila.apilar(nuevoPrestamo);
    
    Cola<Prestamo> cola = prestamosPorEstudianteCola.get(codigo);
    if (cola == null) { cola = new Cola<>(); prestamosPorEstudianteCola.put(codigo, cola); }
    cola.encolar(nuevoPrestamo);
    
    prod.prestar();
    return true;
}
	/**
	 * 
	 * @param id
	 * @return
	 * @throws PosicionIlegalException
	 */
		public Estudiante consultarEstudianteTieneProducto(int id) throws PosicionIlegalException{
    for (int i = 0; i < prestamos.getTamanio(); i++) {
        Prestamo p = prestamos.getValor(i);
        if (p.getProductoId() == id && p.getFechaDevolucion() == null) 
            return p.getEstudiante();
    }
    return null;
}
	/**
	 * 
	 * @param codigo
	 * @return
	 * @throws PosicionIlegalException
	 */
	/**
	 * Devuelve los productos prestados a un estudiante como ListaDoble<Producto>.
	 */
		public ListaDoble<Producto> consultarProductosDeUnEstudiante(int codigo) throws
	   PosicionIlegalException {
		ListaDoble<Producto> res = new ListaDoble<Producto>();
		for (int i = 0; i < prestamos.getTamanio(); i++) {
			Prestamo p = prestamos.getValor(i);
			if (p != null && p.getEstudiante() != null && 
				p.getEstudiante().getCodigo() == codigo && 
				p.getFechaDevolucion() == null) {
				Producto prod = buscarProductoPorId(p.getProductoId());
				if (prod != null) res.agregar(prod);
			}
		}
		return res;
	}

	/**
	 * 
	 * @param id
	 * @param fecha
	 * @return
	 * @throws PosicionIlegalException
	 */
	
		public boolean devolverProducto(int id, Fecha fechaDev) throws PosicionIlegalException{
		Prestamo prestamoActivo = obtenerPrestamoActivoPorProducto(id);
		if (prestamoActivo == null) return false;
		
		if (fechaDev == null) return false;
		
		prestamoActivo.setFechaDevolucion(fechaDev);
		
		Producto prod = buscarProductoPorId(id);
		if (prod != null) prod.devolver();
		
		return true;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws PosicionIlegalException
	 */
		public boolean eliminarProducto(int id) throws PosicionIlegalException{
		Producto prod = buscarProductoPorId(id);
		if (prod == null) return false;
		
		if (!prod.isDisponible()) return false;
		
		for (int i = prestamos.getTamanio() - 1; i >= 0; i--) {
			Prestamo p = prestamos.getValor(i);
			if (p.getProductoId() == id) {
				prestamos.remover(i);
			}
		}
		
		FamiliaTipo fam = obtenerFamiliaDeProducto(prod);
		boolean eliminado = false;
		
		switch(fam) {
			case DEPORTIVOS:
				for (int i = 0; i < productosDeportivos.getTamanio(); i++) {
					if (productosDeportivos.getValor(i).getId() == id) {
						productosDeportivos.remover(i);
						eliminado = true;
						break;
					}
				}
				break;
			case ESCENICOS:
				for (int i = 0; i < productosEscenicos.getTamanio(); i++) {
					if (productosEscenicos.getValor(i).getId() == id) {
						productosEscenicos.remover(i);
						eliminado = true;
						break;
					}
				}
				break;
			case MUSICALES:
				for (int i = 0; i < productosMusicales.getTamanio(); i++) {
					if (productosMusicales.getValor(i).getId() == id) {
						productosMusicales.remover(i);
						eliminado = true;
						break;
					}
				}
				break;
		}
		
		if (eliminado) {
			desindexarProducto(prod);
			return true;
		}
		
		return false;
	}

	/**
	 * 
	 * @param codigo
	 * @return
	 * @throws PosicionIlegalException
	 */
		public boolean eliminarEstudiante(int codigo) throws PosicionIlegalException{
		Estudiante est = buscarEstudiante(codigo);
		if (est==null) {
			System.out.println("Estudiante no existe");
			return false;
		}
		
		
		ListaDoble<Producto> prestamosActivos = consultarProductosDeUnEstudiante(codigo);
		if (prestamosActivos.getTamanio() > 0) { //verifica si hayy un estudiante con presta,mos activo
			System.out.println("No se puede eliminar estudiante con préstamos activos");
			return false;
		}
		
		//Borrar el estudiante de los prestamos
		for (int i = prestamos.getTamanio() - 1; i >= 0; i--) {
			Prestamo pres= prestamos.getValor(i);
			if(pres.getEstudiante().getCodigo() == codigo) {
				prestamos.remover(i);
			}
		}
		
		//Borrar el estudiante de estudiantes
		for(int i=0; i<estudiantes.getTamanio();i++) {
			if(estudiantes.getValor(i).getCodigo() == codigo) {
				estudiantes.remover(i);
				break;
			}
		}
		
		//Limpiar estructuras auxiliares
		prestamosPorEstudiantePila.remove(codigo);
		prestamosPorEstudianteCola.remove(codigo);
		
		System.out.println("El estudiante "+est+ " fue eliminado satisfactoriamente");
		return true;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws PosicionIlegalException
	 */
	public String buscarNombreProducto(int id) throws PosicionIlegalException{
		Producto p = buscarProductoPorId(id);
		if (p != null) return p.getNombre();
		return null;
	}

	/**
	 * 
	 * @param codigo
	 * @return
	 * @throws PosicionIlegalException
	 */
	public String buscarNombreEstudiante(int codigo) throws PosicionIlegalException{
		for(int i=0; i<estudiantes.getTamanio();i++) {
			Estudiante est = estudiantes.getValor(i);
			if(est.getCodigo()==codigo) {
				return est.getNombre();
			}
		}
		return null;
	}

	
	 /**
	  * 
	  * @return
	  * @throws PosicionIlegalException
	  */
	
	/**
	 * Devuelve la lista completa de productos como ListaDoble<Producto>.
	 */
	public ListaDoble<Producto> mostrarProductos() throws PosicionIlegalException{
		ListaDoble<Producto> list = new ListaDoble<Producto>();
		for (int i = 0; i < productosDeportivos.getTamanio(); i++) {
			Producto p = productosDeportivos.getValor(i);
			String estado = p.isDisponible() ? "Disponible" : "Prestado";
			System.out.println("Id: " + p.getId() + "  Descripcion: " + p.getNombre() + "  (" + estado + ")");
			list.agregar(p);
		}
		for (int i = 0; i < productosEscenicos.getTamanio(); i++) {
			Producto p = productosEscenicos.getValor(i);
			String estado = p.isDisponible() ? "Disponible" : "Prestado";
			System.out.println("Id: " + p.getId() + "  Descripcion: " + p.getNombre() + "  (" + estado + ")");
			list.agregar(p);
		}
		for (int i = 0; i < productosMusicales.getTamanio(); i++) {
			Producto p = productosMusicales.getValor(i);
			String estado = p.isDisponible() ? "Disponible" : "Prestado";
			System.out.println("Id: " + p.getId() + "  Descripcion: " + p.getNombre() + "  (" + estado + ")");
			list.agregar(p);
		}
		return list;
	}

	/**
	 * Retorna el préstamo activo (sin fechaDevolucion) para un producto dado, o null si no existe.
	 */
	public Prestamo obtenerPrestamoActivoPorProducto(int id) throws PosicionIlegalException {
		for (int i = 0; i < prestamos.getTamanio(); i++) {
			Prestamo p = prestamos.getValor(i);
			if (p.getProductoId() == id && p.getFechaDevolucion() == null) return p;
		}
		return null;
	}

	/**
	 * Determina la familia de un producto concreto.
	 */
	private FamiliaTipo obtenerFamiliaDeProducto(Producto p) {
		if (p == null) return null;
		if (p instanceof ProductosDeportivos) return FamiliaTipo.DEPORTIVOS;
		if (p instanceof ProductosEscenicos) return FamiliaTipo.ESCENICOS;
		if (p instanceof ProductosMusicales) return FamiliaTipo.MUSICALES;
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws PosicionIlegalException
	 */
	
	public ListaDoble<Estudiante> mostrarEstudiantes() throws PosicionIlegalException {
		
		return estudiantes;
		
	}
	/**
	 * 
	 * @return una lista de los estudiantes que cumplan con la condición de tener mas
	 *       de tres préstamos
	 * @throws PosicionIlegalException
	 */
	
	public ListaDoble<Estudiante> mostrarEstudiantesMasDeTres() throws PosicionIlegalException {
		 ListaDoble<Estudiante> prestamosMasDeTres=new ListaDoble<Estudiante>();
		
		for (int i=0;i<estudiantes.getTamanio();i++) {
			
			 Estudiante estudiante = estudiantes.getValor(i);
			 int contador = 0;
			 for(int j=0;j<prestamos.getTamanio();j++) {
				 
				 if(estudiante.equals(prestamos.getValor(j).getEstudiante())) {
					 //Si el producto del estudiante ya esta devuelto, no lo considero
					 if (prestamos.getValor(j).getFechaDevolucion() == null)
					    contador++;
				 }		 
				 
			 }
			 if (contador>3)
			 {
				 prestamosMasDeTres.agregar(estudiante);
			 }
			
		}	
		
		return prestamosMasDeTres;
	}
	
}
		
	
	





