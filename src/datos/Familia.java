package datos;

public class Familia {
    private int idFamilia;
    private String nombreFamilia;
    private String descripcion;
    public Familia(int idFamilia, String nombreFamilia, String descripcion) {
        this.idFamilia = idFamilia;
        this.nombreFamilia = nombreFamilia;
        this.descripcion = descripcion;
    }
    public int getIdFamilia() {
        return idFamilia;
    }
    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }
    public String getNombreFamilia() {
        return nombreFamilia;
    }
    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    @Override
    public String toString() {
        return  idFamilia + ", " + nombreFamilia ;
                
    }
    
    
}
