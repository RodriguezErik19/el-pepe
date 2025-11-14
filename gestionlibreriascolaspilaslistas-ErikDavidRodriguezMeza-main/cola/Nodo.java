package cola;

public class Nodo<T>{
    private T valor;
    private Nodo<T> siguiente;//apunta al siguiente como lo vimos en clase
    private Nodo<T> anterior;//apunta ak anterior
    
    public Nodo(){
        valor =null;
        siguiente = null;
        anterior = null;
    }
    
    public T getValor() {
        return valor;
    }
    public void setValor(T valor) {
        this.valor = valor;
    }
    public Nodo<T> getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
    public Nodo<T> getAnterior() {
        return anterior;
    }
    public void setAnterior(Nodo<T> anterior) {
        this.anterior = anterior;
    }
}