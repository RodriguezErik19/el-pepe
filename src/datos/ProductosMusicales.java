package datos;

public class ProductosMusicales extends Producto {
    
    private String descripcion; 
    private String materialPredominante;//madera, metal, plástico, etc.).
    private String rangoPrecios; //Intervalo típico de precios (p. ej. “$500 – $10,000”)
    private String tipoInstrumento;//(cuerda, viento, percusión, teclado, electrónico).
    private Familia familia;
    private boolean prestado;
    public ProductosMusicales(String descripcion, String materialPredominante, String rangoPrecios,
            String tipoInstrumento, Familia familia, boolean prestado) {
        this.descripcion = descripcion;
        this.materialPredominante = materialPredominante;
        this.rangoPrecios = rangoPrecios;
        this.tipoInstrumento = tipoInstrumento;
        this.familia = familia;
        this.prestado = prestado;
    }
    public ProductosMusicales(int id, String nombre, String descripcion, String materialPredominante,
            String rangoPrecios, String tipoInstrumento, Familia familia, boolean prestado) {
        super(id, nombre);
        this.descripcion = descripcion;
        this.materialPredominante = materialPredominante;
        this.rangoPrecios = rangoPrecios;
        this.tipoInstrumento = tipoInstrumento;
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
    public String getTipoInstrumento() {
        return tipoInstrumento;
    }
    public void setTipoInstrumento(String tipoInstrumento) {
        this.tipoInstrumento = tipoInstrumento;
    }
    public Familia getFamilia() {
        return familia;
    }
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
   
    @Override
    public String toString() {
        return super.getId()+", "+super.getNombre()+", " + descripcion + ", " + materialPredominante
                + ", " + rangoPrecios + ", " + tipoInstrumento + ", " + familia
                + ", " + prestado ;
    }
    @Override
    public boolean esPrestable() {
        // Este recurso es prestable (la información real de si está prestado
        // se almacena en el campo `prestado`).
        return true;
    }
    @Override
    public void prestar() {
       this.prestado = true;
    }
    @Override
    public void devolver() {
        this.prestado = false;
        
    }
    @Override
    public boolean isDisponible() {
        return !this.prestado;
    }
    
    

    
    
    

    
    
    


    
}
