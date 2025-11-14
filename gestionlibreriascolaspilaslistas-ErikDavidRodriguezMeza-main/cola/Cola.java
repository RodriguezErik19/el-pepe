package cola;



public class Cola<T> {

    private Nodo<T> frente;
    private Nodo<T> fin; 
    private int tamanio;

    public Cola() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    public boolean esVacia() {
        return frente == null;
    }

    public void encolar(T valor) {
        Nodo<T> nuevo = new Nodo<>();
        nuevo.setValor(valor);
        
        if (esVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

  
    public T desencolar() {
        if (esVacia()) {
            return null;
        }
        
        T valor = frente.getValor();
        frente = frente.getSiguiente();
        
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return valor;
    }

    public T frente() {
        if (esVacia()) {
            return null;
        }
        return frente.getValor();
    }

    public int getTamanio() {
        return tamanio;
    }
    
    public void mostrar() {
        if (esVacia()) {
            System.out.println("nom hay nada");
            return;
        }
        
        Nodo<T> actual = frente;
        System.out.println("increible");
        int contador = 1;
        while (actual != null) {
            System.out.println(contador + ". " + actual.getValor());
            actual = actual.getSiguiente();
            contador++;
        }
    }
}