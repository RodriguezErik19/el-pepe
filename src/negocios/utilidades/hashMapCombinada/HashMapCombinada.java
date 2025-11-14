package negocios.utilidades.hashMapCombinada;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Collection;
import java.util.Map;

public class HashMapCombinada <T>{
    private Map<String,LinkedList<T>> tabla;
    private Vector<String> claves;
    public HashMapCombinada() {
        tabla = new HashMap<>();
        claves = new Vector<>();
    }
    public void poner(String key, T elm){
        //creamos una objeto lista y obtenemos el key
        //en caso de que no existe ese key anteriormente osea es null
        //creamo una nueva lista y agregar al Mapa y al Vector
        LinkedList<T> lst = tabla.get(key);
        if(lst == null){
            lst = new LinkedList<>();
            tabla.put(key,lst);
            claves.add(key);
        }
        //finalementr lo agregamos a  la lista
        lst.addLast(elm);
    }
    /*
     * Retorna la lista asociada a la key
     * si no retorna null
     */
    public LinkedList<T> obtener(String key){
        return tabla.get(key);

    }
    
    public Collection<String> claves(){
        return claves;
    }
    
    /**
     * Devuelve true si la clave existe y la lista asociada contiene el elemento.
     */
    
    /**
     * Elimina el elemento `elm` de la lista asociada a `key`.
     * Si la lista queda vacía, elimina la entrada del mapa y la clave del vector.
     * @return true si el elemento fue eliminado, false si no existía la clave o el elemento
     */
    public boolean quitar(String key, T elm){
        LinkedList<T> lst = tabla.get(key);
        if(lst == null) return false;
        boolean removed = lst.remove(elm);
        if(removed){
            if(lst.isEmpty()){
                tabla.remove(key);
                claves.remove(key);
            }
            return true;
        }
        return false;
    }

    /**
     * Comprueba si la lista asociada a `key` contiene el elemento `elm`.
     * @return true si contiene el elemento, false si la clave no existe o no lo contiene
     */
    public boolean contiene(String key, T elm){
        LinkedList<T> lst = tabla.get(key);
        if(lst == null) 
            return false;
        return lst.contains(elm);
    }
    
    
}
