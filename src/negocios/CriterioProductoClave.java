package negocios;
import datos.Producto;
public class CriterioProductoClave implements Criterio<Producto>{

    public int comparar(Producto a, Producto b){
        return 1;
    }
}