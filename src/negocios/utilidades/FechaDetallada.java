package negocios.utilidades;
public class FechaDetallada extends Fecha{
    private static final String[] MESES = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    
    
    public FechaDetallada(){
        super();

    }

    public FechaDetallada(int d, int m, int a){
        super(d,m,a);
        //setDia(d);
        //setMes(m);
        //setAnio(a);
    }

    public FechaDetallada(String f){
        super(f);
    }

    @Override
    public String toString() {
        
        return String.format("%02d de %s de %04d", getDia(), MESES[getMes() - 1], getAnio());
    }
}