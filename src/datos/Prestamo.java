package datos;

import negocios.utilidades.listaDoble.PosicionIlegalException;

/**
 * Representa un préstamo referenciando un producto por su id y familia.
 * Esta implementación usa agregación por id (no mantiene ownership de la instancia concreta),
 * lo que facilita persistencia y serialización: solo se guarda el identificador y la familia.
 */
public class Prestamo {
	private int productoId;           // id del producto prestado
	private FamiliaTipo familia;      // familia/tipo del producto
	private Estudiante estudiante;    // estudiante que toma el préstamo
	private Fecha fechaPrestamo;
	private Fecha fechaDevolucion;

	public Prestamo() {
		// constructor por defecto
	}

	/**
	 * Constructor principal.
	 * @param productoId id del producto (debe ser > 0)
	 * @param familia familia del producto (no nula)
	 * @param estudiante estudiante responsable (no nulo)
	 * @param fechaPrestamo fecha de inicio del préstamo (no nula)
	 */
	public Prestamo(int productoId, FamiliaTipo familia, Estudiante estudiante, Fecha fechaPrestamo) {
		if (productoId <= 0) throw new IllegalArgumentException("productoId debe ser mayor que 0");
		if (familia == null) throw new IllegalArgumentException("familia no puede ser nula");
		if (estudiante == null) throw new IllegalArgumentException("estudiante no puede ser nulo");
		if (fechaPrestamo == null) throw new IllegalArgumentException("fechaPrestamo no puede ser nula");

		this.productoId = productoId;
		this.familia = familia;
		this.estudiante = estudiante;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = null;
	}

	// Nota: los constructores que aceptaban el tipo antiguo fueron eliminados en favor del
	// constructor principal que usa productoId + familia (terminología: 'Producto').

	public int getProductoId() {
		return productoId;
	}

	public FamiliaTipo getFamilia() {
		return familia;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public Fecha getFechaPrestamo() {
		return fechaPrestamo;
	}

	public Fecha getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Fecha fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	/** Permite reemplazar el objeto Estudiante asociado al préstamo. Útil
	 *  cuando la instancia proviene de persistencia y sólo contiene el código;
	 *  así podemos vincular el objeto completo cargado en memoria.
	 */
	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	// No hay compatibilidad con el tipo antiguo: use resolveProducto(service) si necesita
	// obtener la instancia concreta a partir de productoId + familia.

	/**
	 * Resuelve la instancia concreta de Producto usando el servicio provisto.
	 * Esto no se usa para persistencia (aquí sólo se resuelve bajo demanda).
	 * @param service servicio que sabe recuperar productos
	 * @return Producto concreto o null si no se encuentra
	 */
	public Producto resolveProducto(negocios.UniversidadService service) {
		if (service == null) return null;
		try {
			return service.obtenerProductoPorIdYFamilia(productoId, familia);
		} catch (negocios.utilidades.listaDoble.PosicionIlegalException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Prestamo[productoId=" + productoId + ", familia=" + familia + ", estudiante=" + estudiante
				+ ", fechaPrestamo=" + fechaPrestamo + ", fechaDevolucion=" + fechaDevolucion + "]";
	}
}

