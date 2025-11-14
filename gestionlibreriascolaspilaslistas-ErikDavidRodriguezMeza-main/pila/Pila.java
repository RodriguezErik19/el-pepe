package pila;

public class Pila<T> {

    
    private Nodo<T> cima; 
    private int tamanio; 

    public Pila() {
        cima = null;
        tamanio = 0;
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean esVacia(){
        return(cima == null);
    }

   // quite el metodo buscar que tenia antes porque modificada la pola

    public void apilar(T valor){
        Nodo<T> nuevo = new Nodo<>();
        
        nuevo.setValor(valor);
        if(esVacia()){
            cima = nuevo;
        }else{
            nuevo.setSiguiente(cima);
            cima = nuevo;
        }

        tamanio++;
    }

    
    public T retirar(){
        if(esVacia()){
            return null; 
        }
        T valor = cima.getValor();
        cima = cima.getSiguiente();
        tamanio--;
        return valor; // devolver el valort retirado
    }

    public T cima(){
        if(!esVacia()){
            return cima.getValor();
        }else{
            return null;
        }
    }

}