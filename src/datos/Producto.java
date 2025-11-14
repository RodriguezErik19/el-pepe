package datos;
import negocios.Prestable;

public abstract class Producto implements Prestable{

    private int id ;
	private String nombre;
    public Producto() {
    }
    public Producto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + "]";
    }
    /**
     * Indica si el producto está disponible para préstamo.
     * Las subclases deben implementar esta verificación usando su
     * propio campo `prestado` o lógica equivalente.
     */
    /**
     * Versión por defecto para la rama esqueleto: delega en
     * el comportamiento de `esPrestable()` de la subclase.
     *
     * Nota para alumnos: varias pruebas usan clases anónimas que
     * implementan `esPrestable()` como el indicador interno de
     * "prestado". Esta implementación por defecto permite que
     * esas pruebas sigan compilando y ejecutándose en la versión
     * esqueleto. Los alumnos pueden sobreescribir este método
     * si su clase necesita lógica distinta.
     */
    public boolean isDisponible() {
        try {
            return !esPrestable();
        } catch (UnsupportedOperationException u) {
            // Si la subclase aún no implementó esPrestable(), indicar no disponible
            throw u;
        }
    }
    // TODO (Alumno): esta clase esqueleto debe contener sólo la estructura mínima
    // (campos, constructores, getters/setters). El comportamiento concreto de
    // préstamo (`esPrestable`, `prestar`, `devolver`) debe implementarse en las
    // subclases. Por eso aquí no se proporciona lógica adicional.
    

	
}

