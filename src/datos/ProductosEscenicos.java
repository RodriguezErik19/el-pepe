package datos;



public class ProductosEscenicos extends Producto {
    
    private String descripcion; 
    private String materialPredominante;//madera, metal, plástico, etc.).
    private String rangoPrecios; //Intervalo típico de precios (p. ej. “$500 – $10,000”)
    private String tallasDisponibles;//Rango de tallas aplicable (S–XL, infantil–adulto, etc.)
    private String generoUso;//Indica si son de uso femenino, masculino o unisex.
    private String tipoEvento;//Tipo de actividad para la que se utilizan (baile, teatro, desfile, ceremonia, etc.).
    private Familia familia;
    private boolean prestado;
    public ProductosEscenicos() {
        // Constructor vacío: esqueleto para alumnos
    }

    public ProductosEscenicos(int id, String nombre, String descripcion, String materialPredominante,
            String rangoPrecios, String tallasDisponibles, String generoUso, String tipoEvento, Familia familia,
            boolean prestado) {
        super(id, nombre);
        this.descripcion = descripcion;
        this.materialPredominante = materialPredominante;
        this.rangoPrecios = rangoPrecios;
        this.tallasDisponibles = tallasDisponibles;
        this.generoUso = generoUso;
        this.tipoEvento = tipoEvento;
        this.familia = familia;
        this.prestado = prestado;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getMaterialPredominante() {
        return materialPredominante;
    }
    public void setMaterialPredominante(String materialPredominante) {
        this.materialPredominante = materialPredominante;
    }
    public String getRangoPrecios() {
        return rangoPrecios;
    }
    public void setRangoPrecios(String rangoPrecios) {
        this.rangoPrecios = rangoPrecios;
    }
    public String getTallasDisponibles() {
        return tallasDisponibles;
    }
    public void setTallasDisponibles(String tallasDisponibles) {
        this.tallasDisponibles = tallasDisponibles;
    }
    public String getGeneroUso() {
        return generoUso;
    }
    public void setGeneroUso(String generoUso) {
        this.generoUso = generoUso;
    }
    public String getTipoEvento() {
        return tipoEvento;
    }
    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
    public Familia getFamilia() {
        return familia;
    }
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
   
    @Override
    public String toString() {
        // Esqueleto: representación simple para el alumno.
        return "ProductosEscenicos[id=" + super.getId() + ", nombre=" + super.getNombre() + "]";
    }

    // Métodos de préstamo: esqueleto para implementar en la práctica.
    @Override
    public boolean esPrestable() {
        throw new UnsupportedOperationException("TODO (Alumno): implementar esPrestable() en ProductosEscenicos");
    }

    @Override
    public void prestar() {
        throw new UnsupportedOperationException("TODO (Alumno): implementar prestar() en ProductosEscenicos");
    }

    @Override
    public void devolver() {
        throw new UnsupportedOperationException("TODO (Alumno): implementar devolver() en ProductosEscenicos");
    }
    
    
    
    
    

}
