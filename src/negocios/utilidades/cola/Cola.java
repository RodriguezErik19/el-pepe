package negocios.utilidades.cola;

public class Cola<T> {
    //atrubutos de la clase
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;
    //constructor por defecto
    public Cola() {
        cabeza = null;
        tamanio = 0;
        cola =null;
    }
    public int getTamanio() {
        return tamanio;
    }
    //verificar si la pila esta vacia
    public boolean esVacia(){
        return (cabeza == null);
    }
    //Apilar un nuevo elemento
    public  void encolar(T valor){
        //crear un nuevo Nodo
        Nodo<T> nuevo = new Nodo<T>();
        //fijar el valor dentro del nodo
        nuevo.setValor(valor);
        if (esVacia()){
            //cabeza y cola apuntan al nuevo nodo
            cabeza = nuevo;
            cola = nuevo;
        }else{
            //enlazamos el campo siguinete de la cola con el nuevo elemento
            cola.setSiguiente(nuevo);
          
            //la nueva cola a ser el nuevo
            cola = nuevo;
        }
        //incrementamos el tamaño de la pila
        tamanio++;
    }
    //Elimina un elemento de la pila
    public void desencolar(){
        if (!esVacia()){
            //verificamos si hay un colo elemento en la cola
            if (cabeza == cola){
                cabeza = null;
                cola = null;

            }else{
                //se elimina el primer elemento de la cola
                //dezplazamos la cabeza al siguiente
                cabeza = cabeza.getSiguiente();

            }
            //se dismimyte tambiel el total de elementos
            tamanio--;
        }

    }
    //Devuelve el primer elemento de la cola
    public T frente(){
        if (!esVacia())
           return cabeza.getValor();
        else
           return null;
    }   
    
}
