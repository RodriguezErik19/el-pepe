package negocios;


import datos.IUniversidadDAO;

import datos.Estudiante;

import datos.FamiliaTipo;
import datos.Fecha;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

import datos.Producto;
import datos.ProductosDeportivos;
import datos.ProductosEscenicos;
import datos.ProductosMusicales;
public class UniversidadService{
	private IUniversidadDAO universidadDAO;
	private Universidad universidad;
    private String ruta;
    private String nombreUsuario;
    private String emailUsuario;
	public UniversidadService(IUniversidadDAO universidadDAO, String ruta, String nombreUsuario, String emailUsuario) {
		this.universidadDAO = universidadDAO;
        this.ruta = ruta;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
		// Inicializar el dominio en memoria y, si tenemos DAO, cargar préstamos
		this.universidad = new Universidad();
		if (this.universidadDAO != null) {
			try {
				ListaDoble<datos.Prestamo> lista = this.universidadDAO.cargarPrestamos();
				if (lista != null) this.universidad.importarPrestamos(lista);
			} catch (Exception ex) {
				System.out.println("Advertencia: no se pudieron importar préstamos al iniciar: " + ex.getMessage());
			}
		}
    }
	public UniversidadService(Universidad universidad, String ruta, String nombreUsuario, String emailUsuario) {
		// Si se pasó una instancia de Universidad la usamos; si no, creamos una
		this.universidad = universidad != null ? universidad : new Universidad();
        this.ruta = ruta;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
    }
    
	/**
	 * Constructor que permite inyectar ambas dependencias: el dominio en memoria
	 * (`Universidad`) y la capa de persistencia (`UniversidadDAO`).
	 *
	 * Use este constructor para wiring completo al arrancar la aplicación.
	 */
	public UniversidadService(Universidad universidad, IUniversidadDAO universidadDAO, String ruta, String nombreUsuario, String emailUsuario) {
		this.universidad = universidad;
		this.universidadDAO = universidadDAO;
		this.ruta = ruta;
		this.nombreUsuario = nombreUsuario;
		this.emailUsuario = emailUsuario;
		// Si hay DAO disponible y la universidad en memoria está inicializada, importar préstamos
		if (this.universidad != null && this.universidadDAO != null) {
			try {
				ListaDoble<datos.Prestamo> lista = this.universidadDAO.cargarPrestamos();
				if (lista != null) this.universidad.importarPrestamos(lista);
			} catch (Exception ex) {
				System.out.println("Advertencia: no se pudieron importar préstamos al iniciar: " + ex.getMessage());
			}
		}
	}

    /**
     * Método que imprime un mensaje de bienvenida
     */
    public void presentarse() {
        System.out.println("Bienvenido a Gestión de Préstamos Univeritarios");
        System.out.println("Nombre: "+nombreUsuario);
        System.out.println("Email: "+emailUsuario);
        System.out.println("Ruta de trabajo: "+ruta);

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
		universidad.agregarEstudiante(codigo, nombre, email, fechaNac, sexo, programa);
		// Intentar persistir estudiantes en disco (reescritura)
		if (this.universidadDAO != null) {
			try {
				this.universidadDAO.persistirEstudiantes(this.universidad.mostrarEstudiantes());
				System.out.println("Persistencia: estudiante agregado en archivos.");
			} catch (java.io.IOException ioe) {
				System.out.println("Advertencia: no se pudo persistir el estudiante: " + ioe.getMessage());
			}
		}
		return true;
			
	}

    /**
	 * 
	 * @param codigo
	 * @return
	 * @throws PosicionIlegalException
	 */

    public Estudiante buscarEstudiante(int codigo) throws PosicionIlegalException{
		if (this.universidad == null) return null;
		return this.universidad.buscarEstudiante(codigo);
	}

    /**
	 * 
	 * @param id
	 * @param nombre
	 * @return
	 * @throws PosicionIlegalException
	 */
	public boolean agregarProducto(int id, String nombre) throws PosicionIlegalException{
		// Por compatibilidad, agregar como DEPORTIVO por defecto
		return agregarProducto(FamiliaTipo.DEPORTIVOS, id, nombre);
		
	}

	/**
	 * Agrega un producto indicando la familia y persiste los archivos reescribiendo su contenido.
	 */
	public boolean agregarProducto(FamiliaTipo fam, int id, String nombre) throws PosicionIlegalException{
		if (this.universidad == null) 
			return false;
		boolean ok = this.universidad.agregarProducto(fam, id, nombre);
		if (ok && this.universidadDAO != null) {
			try {
				this.universidadDAO.persistirProductos(this.universidad.mostrarProductosDeportivos(),
													   this.universidad.mostrarProductosEscenicos(),
													   this.universidad.mostrarProductosMusicales());
				System.out.println("Persistencia: producto agregado a archivos.");
			} catch (java.io.IOException ioe) {
				System.out.println("Advertencia: no se pudo persistir el producto: " + ioe.getMessage());
			}
		}
		return ok;
	}

	public ListaDoble<Producto> buscarProductosPorPalabraClave(String palabra) throws PosicionIlegalException{
		if (this.universidad == null) 
			return new ListaDoble<>();
		return this.universidad.buscarProductosPorPalabraClave(palabra);
	}

    /**
	 * 
	 * @param id
	 * @return
	 * @throws PosicionIlegalException
	 */
	// Buscar un producto por id (delegar al dominio)
	public Producto buscarProducto(int id) throws PosicionIlegalException{
		if (this.universidad == null) 
			return null;
		return this.universidad.buscarProductoPorId(id);
	}

    /**
	 * 
	 * @param codigo
	 * @param id
	 * @param fecha
	 * @return
	 * @throws PosicionIlegalException
	 */
	public boolean prestarProducto(int codigo, int id, Fecha fecha) throws PosicionIlegalException{
		if (this.universidad == null) 
			return false;
		boolean ok = this.universidad.prestarProducto(codigo, id, fecha);
		if (ok) {
			// Intentar persistir el préstamo recién creado: buscar el préstamo activo y guardarlo
			try {
				datos.Prestamo p = this.obtenerPrestamoActivoPorProducto(id);
				if (p != null && this.universidadDAO != null) {
					try {
						this.guardarPrestamo(p);
					} catch (java.io.IOException ioe) {
						System.out.println("Advertencia: no se pudo guardar el préstamo en persistencia: " + ioe.getMessage());
					}
				}
			} catch (PosicionIlegalException | java.io.IOException ex) {
				// Si no es posible recuperar/guardar, no abortamos el préstamo en memoria; sólo avisamos
				System.out.println("Advertencia: no se pudo persistir el préstamo: " + ex.getMessage());
			}
		}
		return ok;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws PosicionIlegalException
	 */
	public Estudiante consultarEstudianteTieneProducto(int id) throws PosicionIlegalException{
		if (this.universidad == null) 
			return null;
		return this.universidad.consultarEstudianteTieneProducto(id);
	}

	/**
	 * 
	 * @param codigo
	 * @return
	 * @throws PosicionIlegalException
	 */
	 public ListaDoble<Producto> consultarProductosDeUnEstudiante(int codigo) throws
		 PosicionIlegalException {
		if (this.universidad == null) 
			return null;
		return this.universidad.consultarProductosDeUnEstudiante(codigo);
	}

	/**
	 * 
	 * @param id
	 * @param fecha
	 * @return
	 * @throws PosicionIlegalException
	 */
	
	public boolean devolverProducto(int id, Fecha fechaDev) throws PosicionIlegalException{
		if (this.universidad == null) 
			return false;
		// Obtener el préstamo activo antes de modificarlo para poder persistir el cambio
		datos.Prestamo prestamoActivo = null;
		try {
			prestamoActivo = this.universidad.obtenerPrestamoActivoPorProducto(id);
		} catch (Exception e) {
			prestamoActivo = null;
		}
		boolean ok = this.universidad.devolverProducto(id, fechaDev);
		if (ok && this.universidadDAO != null) {
			try {
				// Intentar actualizar in-place la línea del préstamo con la fecha de devolución
				boolean updated = false;
				try {
					if (prestamoActivo != null) {
						int codigoEst = prestamoActivo.getEstudiante() != null ? prestamoActivo.getEstudiante().getCodigo() : -1;
						updated = this.universidadDAO.actualizarPrestamoDevolucion(id, codigoEst, prestamoActivo.getFechaDevolucion());
					}
				} catch (java.io.IOException ioe) {
					// si la actualización falla, caer a la estrategia anterior: limpiar+guardar
					updated = false;
				}
				if (!updated) {
					// Fallback: eliminar líneas antiguas y volver a guardar la versión actualizada
					this.universidadDAO.limpiarPrestamosPorProductoOEstudiante(id, -1);
					if (prestamoActivo != null) {
						this.universidadDAO.guardarPrestamo(prestamoActivo);
					}
				}
				// Además persistir el estado de los productos (para que el flag prestado se refleje en disco)
				try {
					this.universidadDAO.persistirProductos(this.universidad.mostrarProductosDeportivos(),
										this.universidad.mostrarProductosEscenicos(),
										this.universidad.mostrarProductosMusicales());
				} catch (Exception ex) {
					// No queremos que la persistencia de productos bloquee la devolución; informar y continuar
					System.out.println("Advertencia: no se pudo persistir estado de productos: " + ex.getMessage());
				}
			} catch (java.io.IOException ioe) {
				System.out.println("Error persistiendo devolución: " + ioe.getMessage());
			}
		}
		return ok;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws PosicionIlegalException
	 */
	public boolean eliminarProducto(int id) throws PosicionIlegalException{
		if (this.universidad == null) {
			System.out.println("Operación no disponible: Universidad no inicializada en el servicio");
			return false;
		}
		boolean ok = this.universidad.eliminarProducto(id);
		if (ok && this.universidadDAO != null) {
			try {
				// eliminar del almacenamiento por familia si existe
				boolean mod = this.universidadDAO.eliminarProductoPorId(id);
				// limpiar préstamos persistidos que apunten al producto eliminado
				this.universidadDAO.limpiarPrestamosPorProductoOEstudiante(id, -1);
				if (mod) 
					System.out.println("Persistencia: producto eliminado de archivos en disco.");
			} catch (java.io.IOException ioe) {
				System.out.println("Advertencia: no se pudo persistir la eliminación del producto: " + ioe.getMessage());
			}
		}
		return ok;
	}

	/**
	 * 
	 * @param codigo
	 * @return
	 * @throws PosicionIlegalException
	 */
	public boolean eliminarEstudiante(int codigo) throws PosicionIlegalException{
		if (this.universidad == null) {
			System.out.println("Operación no disponible: Universidad no inicializada en el servicio");
			return false;
		}

		boolean ok = this.universidad.eliminarEstudiante(codigo);
		if (ok && this.universidadDAO != null) {
			try {
				boolean mod = this.universidadDAO.eliminarEstudiantePorCodigo(codigo);
				// limpiar préstamos persistidos que apunten al estudiante eliminado
				this.universidadDAO.limpiarPrestamosPorProductoOEstudiante(-1, codigo);
				if (mod) System.out.println("Persistencia: estudiante eliminado de archivos en disco.");
			} catch (java.io.IOException ioe) {
				System.out.println("Advertencia: no se pudo persistir la eliminación del estudiante: " + ioe.getMessage());
			}
		}
		return ok;
	}

	/**
	 * Editar un estudiante y persistir el cambio (reescritura de archivo) si hay DAO.
	 */
	public boolean editarEstudiante(int codigo, String nombre,
			String email, Fecha fechaNac, String sexo, String programa) throws PosicionIlegalException{
		if (this.universidad == null) return false;
		boolean ok = this.universidad.editarEstudiante(codigo, nombre, email, fechaNac, sexo, programa);
		if (ok && this.universidadDAO != null) {
			try {
				this.universidadDAO.persistirEstudiantes(this.universidad.mostrarEstudiantes());
				System.out.println("Persistencia: estudiante editado en archivos.");
			} catch (java.io.IOException ioe) {
				System.out.println("Advertencia: no se pudo persistir la edición del estudiante: " + ioe.getMessage());
			}
		}
		return ok;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws PosicionIlegalException
	 */
	public String buscarNombreProducto(int id) throws PosicionIlegalException{
		// Intentar primero con el dominio en memoria (más rápido y actualizado)
		if (this.universidad != null) {
			String nombre = this.universidad.buscarNombreProducto(id);
			if (nombre != null) return nombre;
		}

		// Si no está en memoria, intentar con el DAO consultando cada familia
		if (this.universidadDAO != null) {
			for (FamiliaTipo fam : FamiliaTipo.values()) {
				try {
					Producto p = this.universidadDAO.obtenerProductoPorIdYFamilia(id, fam);
					if (p != null) return p.getNombre();
				} catch (PosicionIlegalException e) {
					// DAO puede lanzar cuando no encuentra; continuar con la siguiente familia
				}
			}
		}

		// No encontrado
		return null;
	}

	/**
	 * 
	 * @param codigo
	 * @return
	 * @throws PosicionIlegalException
	 */
	public String buscarNombreEstudiante(int codigo) throws PosicionIlegalException{
		// Preferir el dominio en memoria
		if (this.universidad != null) {
			String nombre = this.universidad.buscarNombreEstudiante(codigo);
			if (nombre != null) return nombre;
		}
		// No hay DAO específico para estudiantes por id; devolver null si no está en memoria
		return null;
	}

	
	 /**
	  * 
	  * @return
	  * @throws PosicionIlegalException
	  */
	
	public ListaDoble<Producto> mostrarProductos() throws PosicionIlegalException{
		if (this.universidad == null) return null;
		return this.universidad.mostrarProductos();
	}

	public ListaDoble<ProductosDeportivos> mostrarProductosDeportivos() throws PosicionIlegalException{
        
		return universidad.mostrarProductosDeportivos();
	}

	public ListaDoble<ProductosEscenicos> mostrarProductosEscenicos() throws PosicionIlegalException{
        
		return universidad.mostrarProductosEscenicos();
	}

	public ListaDoble<ProductosMusicales> mostrarProductosMusicales() throws PosicionIlegalException{
        
		return universidad.mostrarProductosMusicales();
	}

	/**
	 * 
	 * @return
	 * @throws PosicionIlegalException
	 */
	
	public ListaDoble<Estudiante> mostrarEstudiantes() throws PosicionIlegalException {
        
		return universidad.mostrarEstudiantes();
        
	}
	/**
	 * 
	 * @return una lista de los estudiantes que cumplan con la condición de tener mas
	 *       de tres préstamos
	 * @throws PosicionIlegalException
	 */
	
	public ListaDoble<Estudiante> mostrarEstudiantesMasDeTres() throws PosicionIlegalException {
         
        
		return null;
	}

	/**
	 * Recupera un Producto por id y familia. Método helper pensado para resolver
	 * referencias cuando Prestamo almacena sólo productoId + familia.
	 * Actualmente es un stub y debe implementarse usando el DAO.
	 */
	public Producto obtenerProductoPorIdYFamilia(int id, FamiliaTipo familia) throws PosicionIlegalException {
		// Preferir el dominio en memoria si está disponible
		if (this.universidad != null) {
			Producto p = this.universidad.buscarProductoPorIdYFamilia(familia, id);
			if (p != null) return p;
		}

		// Fallback a DAO (lee archivos por familia)
		if (this.universidadDAO != null) {
			try {
				return this.universidadDAO.obtenerProductoPorIdYFamilia(id, familia);
			} catch (PosicionIlegalException ex) {
				// DAO puede lanzar si no encuentra: devolver null
				return null;
			}
		}

		return null;
	}


	/**
	 * Busca un préstamo activo por id de producto. Primero consulta el dominio en memoria,
	 * si no está allí intenta cargar los préstamos desde la persistencia (DAO).
	 */
	public datos.Prestamo obtenerPrestamoActivoPorProducto(int id) throws PosicionIlegalException, java.io.IOException {
		// Intentar con el dominio en memoria
		if (this.universidad != null) {
			datos.Prestamo p = this.universidad.obtenerPrestamoActivoPorProducto(id);
			if (p != null) return p;
		}

		// Fallback a persistencia
		if (this.universidadDAO != null) {
			ListaDoble<datos.Prestamo> lista = this.universidadDAO.cargarPrestamos();
			if (lista != null) {
				for (int i = 0; i < lista.getTamanio(); i++) {
					datos.Prestamo p = lista.getValor(i);
					if (p.getProductoId() == id && p.getFechaDevolucion() == null) return p;
				}
			}
		}

		return null;
	}

	// Persistencia de préstamos: delegación al DAO
	public void guardarPrestamo(datos.Prestamo p) throws java.io.IOException {
		universidadDAO.guardarPrestamo(p);
	}

	public ListaDoble<datos.Prestamo> cargarPrestamos() throws java.io.IOException {
		ListaDoble<datos.Prestamo> lista = universidadDAO.cargarPrestamos();
		// Sincronizar estado de productos en memoria: si hay préstamos activos en persistencia
		// marcar los productos correspondientes como prestados para evitar inconsistencias
		if (lista != null && this.universidad != null) {
			try {
				for (int i = 0; i < lista.getTamanio(); i++) {
					datos.Prestamo p = lista.getValor(i);
					if (p != null && p.getFechaDevolucion() == null) {
						try {
							Producto prod = this.universidad.buscarProductoPorId(p.getProductoId());
							if (prod != null) prod.prestar();
						} catch (PosicionIlegalException pie) {
							// si no podemos localizar producto en memoria, ignorar
						}
					}
				}
			} catch (Exception ex) {
				// No queremos que la sincronización bloquee la carga; reportar y continuar
				System.out.println("Advertencia: no se pudo sincronizar estado de productos desde préstamos: " + ex.getMessage());
			}
		}
		return lista;
	}
    
	/** Devuelve el último préstamo (evento más reciente) para un estudiante. */
	public datos.Prestamo obtenerUltimoPrestamoEstudiante(int codigo) throws PosicionIlegalException {
		if (this.universidad == null) return null;
		return this.universidad.obtenerUltimoPrestamoEstudiante(codigo);
	}

	/** Devuelve el historial de préstamos de un estudiante */
	public ListaDoble<datos.Prestamo> obtenerPrestamosEstudiante(int codigo) throws PosicionIlegalException {
		if (this.universidad == null) return new ListaDoble<>();
		return this.universidad.obtenerPrestamosEstudiante(codigo);
	}
    

}