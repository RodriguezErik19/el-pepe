package listaDoble;

public class ListaDoble<T>{
    
    private Nodo<T> cabeza;
    private Nodo<T> cola; 
    private int tamanio;
    
   
    public ListaDoble(){
        cabeza = null;
        cola = null; 
        tamanio = 0;
    }
 
    public int getTamanio() {
        return tamanio;
    }
   
    public boolean esVacia(){     
        return cabeza == null;
    }
    
    //agrega un nuevo nodo al final de la lista
    public void agregar(T valor){
        Nodo<T> nuevo = new Nodo<>();
        nuevo.setValor(valor);
        if (esVacia()){
            cabeza = nuevo;
            cola = nuevo; //inicializa colaa
        }else{
     
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
        tamanio++;
    }
    
   
    public void insertar(T valor, int pos) throws PosicionIlegalException{// inserta un nodo unuevo al final de la lista
        if (pos >= 0 && pos <= tamanio){
            Nodo<T> nuevo = new Nodo<>();
            nuevo.setValor(valor);
            if(pos==0){//inserta al incio
                if (esVacia()) {
                    cabeza = nuevo;
                    cola = nuevo;
                } else {
                    nuevo.setSiguiente(cabeza);
                    cabeza.setAnterior(nuevo);
                    cabeza = nuevo;
                }
            }else if(pos == tamanio){ 
                cola.setSiguiente(nuevo);
                nuevo.setAnterior(cola);
                cola = nuevo;
            }else{ 
                Nodo<T> aux = cabeza;
                for (int i=0; i<=pos-2; i++){
                    aux = aux.getSiguiente();
                }
                Nodo<T> siguiente = aux.getSiguiente();
                
                aux.setSiguiente(nuevo);
                nuevo.setAnterior(aux); 
                
                nuevo.setSiguiente(siguiente);
                siguiente.setAnterior(nuevo);  
            }
            tamanio++;
        }else{
            throw new PosicionIlegalException();
        }
    }
    
   
    public T remover(int pos) throws PosicionIlegalException{
        if (pos < 0 || pos >= tamanio) {
            throw new PosicionIlegalException();
        }

        T valor;
        Nodo<T> auxremov;

        if (pos == 0) { 
            auxremov = cabeza;
            cabeza = cabeza.getSiguiente();
            if (cabeza != null) {
                cabeza.setAnterior(null); 
            } else {
                cola = null; 
            }
        } else {
          
            auxremov=(pos<tamanio/2) ?cabeza :cola;
            
            if (pos < tamanio / 2) {
                for(int i =0; i<pos; i++) auxremov =auxremov.getSiguiente();
            } else {
                for (int i =tamanio-1; i>pos; i--) auxremov =auxremov.getAnterior();
            }
            
            Nodo<T> anterior =auxremov.getAnterior();
            Nodo<T> siguiente =auxremov.getSiguiente();

         
            anterior.setSiguiente(siguiente);
            if (siguiente != null) {
                siguiente.setAnterior(anterior); 
            } else {
                cola = anterior; // si se elimina el ultimo se actualia la cola
            }
        }
        
        valor = auxremov.getValor();
        tamanio--;
        return valor;
    }
    
    
    public int remover(T valor) throws PosicionIlegalException{
        Nodo<T> aux = cabeza;
        int pos = 0;

        while (aux != null) {
          
            if (aux.getValor().equals(valor)) { 
                remover(pos); 
                return pos;
            }
            aux = aux.getSiguiente();
            pos++;
        }
        return -1; // valor no encontrado
    }
    
   
    public T getValor(int pos) throws PosicionIlegalException{
        if(pos >= 0 && pos < tamanio){
           Nodo<T> aux = cabeza;
            for(int i=0; i<pos; i++){ 
                aux =aux.getSiguiente();
            }
            return aux.getValor();
        }else{
            throw new PosicionIlegalException();
        }
    }
    
    public void limpiar(){
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    
    @Override
    public String toString() {
        String resultado = "["; 
        if (esVacia()) {
            return "Lista Vacia";
        }
        
        Nodo<T> aux = cabeza;
        while (aux != null) {
            resultado = resultado + aux.getValor().toString(); 
            
            if (aux.getSiguiente() != null) {
                resultado = resultado + ", "; 
            }
            aux = aux.getSiguiente();
        }
        resultado = resultado + "]"; 
        return resultado;
    }
    
    public boolean contiene(T valor){
        Nodo<T> aux = cabeza;
        while (aux != null) {
            if (aux.getValor().equals(valor)) {
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }
}