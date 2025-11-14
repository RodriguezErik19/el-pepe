package negocios.utilidades.pila;

public class Pila<T> {
    //atrubutos de la clase
    private Nodo<T> cabeza;
    private int tamanio;
    //constructor por defecto
    public Pila() {
        cabeza = null;
        tamanio = 0;
    }
    public int getTamanio() {
        return tamanio;
    }
    //verificar si la pila esta vacia
    public boolean esVacia(){
        return (cabeza == null);
    }
    //Apilar un nuevo elemento
    public  void apilar(T valor){
        //crear un nuevo Nodo
        Nodo<T> nuevo = new Nodo<T>();
        //fijar el valor dentro del nodo
        nuevo.setValor(valor);
        if (esVacia()){
            //cabeza apuntar al nuevo nodo
            cabeza = nuevo;
        }else{
            //enlazamos el campo siguinete de nuevo con la cabeza
            nuevo.setSiguiente(cabeza);
            //la nueva cabeza de la pila pasa a ser el nuevo
            cabeza = nuevo;
        }
        //incrementamos el tamaño de la pila
        tamanio++;
    }
    //Elimina un elemento de la pila
    public void retirar(){
        if (!esVacia()){
            cabeza = cabeza.getSiguiente();
            tamanio--;
        }
    }
    //Devuelve el elemento en el tope de la pila
    public T cima(){
        if (!esVacia())
           return cabeza.getValor();
        else
           return null;
    }   
    
}
