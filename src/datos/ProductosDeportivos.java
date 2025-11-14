package datos;



public class ProductosDeportivos extends Producto {
    
    private String descripcion; //Descripción general de la familia y sus características comunes.
    private String tipoDeporte; //(fútbol, tenis, natación, etc.).
    private String materialPredominante;//madera, metal, plástico, etc.).
    private String rangoPrecios; //Intervalo típico de precios (p. ej. “$500 – $10,000”)
    private Familia familia;
    private boolean prestado;
    public ProductosDeportivos() {
        // Constructor vacío para el esqueleto del alumno
    }

    public ProductosDeportivos(int id, String nombre, String descripcion, String tipoDeporte,
            String materialPredominante, String rangoPrecios, Familia familia, boolean prestado) {
        super(id, nombre);
        this.descripcion = descripcion;
        this.tipoDeporte = tipoDeporte;
        this.materialPredominante = materialPredominante;
        this.rangoPrecios = rangoPrecios;
        this.familia = familia;
        this.prestado = prestado;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getTipoDeporte() {
        return tipoDeporte;
    }
    public void setTipoDeporte(String tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
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
    public Familia getFamilia() {
        return familia;
    }
    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
    public boolean isPrestado() {
        return prestado;
    }
    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }
    @Override
    public String toString() {
        // Esqueleto: representación simple para el alumno.
        return "ProductosDeportivos[id=" + super.getId() + ", nombre=" + super.getNombre() + "]";
    }

    // Métodos de préstamo: esqueleto para que el alumno implemente.
    @Override
    public boolean esPrestable() {
        throw new UnsupportedOperationException("TODO (Alumno): implementar esPrestable() en ProductosDeportivos");
    }

    @Override
    public void prestar() {
        throw new UnsupportedOperationException("TODO (Alumno): implementar prestar() en ProductosDeportivos");
    }

    @Override
    public void devolver() {
        throw new UnsupportedOperationException("TODO (Alumno): implementar devolver() en ProductosDeportivos");
    }

    @Override
    public boolean isDisponible() {
        throw new UnsupportedOperationException("TODO (Alumno): implementar isDisponible() en ProductosDeportivos");
    }


    

    
    

    



    
    
}
