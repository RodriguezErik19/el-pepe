package negocios.utilidades.listaDoble;

public class ListaDoble<T> {
    //atrubutos de la clase
    private Nodo<T> cabeza;
    private int tamanio;
    //constructor por defecto
    public ListaDoble() {
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
    public  void agregar(T valor){
        //crear un nuevo Nodo
        Nodo<T> nuevo = new Nodo<T>();
        //fijar el valor dentro del nodo
        nuevo.setValor(valor);
        if (esVacia()){
            //cabeza y cola apuntan al nuevo nodo
            cabeza = nuevo;
        }else{
           //Agregar al final de la lista
           Nodo<T> aux = cabeza;
           while (aux.getSiguiente() != null){
                aux = aux.getSiguiente();
           }
           aux.setSiguiente(nuevo);
           nuevo.setAnterior(aux);///ESTA ES LA NUEVA LINEA QUE AGREGAMOS
        }
        //incrementamos el tamaño de la pila
        tamanio++;
    }
    public void insertar(T valor, int pos) throws PosicionIlegalException{
        if (pos>=0 && pos <=tamanio){
            Nodo<T> nuevo = new Nodo<T>();
            nuevo.setValor(valor);
            //si se desea insertar al comenzar la lisra
            if (pos == 0){
                cabeza.setAnterior(nuevo); //ESTA ES UNA NUEVA LINEA
                nuevo.setSiguiente(cabeza);
                cabeza = nuevo;

            }else{
                //el nodo se quiere insertar al final de la lista
                if(pos == tamanio){
                    Nodo<T> aux = cabeza;
                    while (aux.getSiguiente() !=null){
                        aux = aux.getSiguiente();
                    }
                    aux.setSiguiente(nuevo);
                    nuevo.setAnterior(aux); //esta es una nueva linea

                }else{ //se quiere insertar en medio de la lista
                    Nodo<T> aux = cabeza;
                    for (int i=0;i<=pos-2;i++){
                        aux = aux.getSiguiente();
                    }
                    Nodo<T> siguiente = aux.getSiguiente();
                    aux.setSiguiente(nuevo);
                    nuevo.setSiguiente(siguiente);
                    nuevo.setAnterior(aux);////ESTAS SON NUEVAS LINEAS
                    siguiente.setAnterior(nuevo);///ESTAS SON NUEVAS LINEAS

                }
            }
            tamanio++;
            
            //se puede inserta
        }else{
            //es una posicion inválida
            throw new PosicionIlegalException();
        }

    }
    /*
     * Elimina un nodo en determinada posición
     * @param pos: Posición a eliminar
     * @thows : PosicionIlegalException
     */
    public void remover(int pos) throws PosicionIlegalException{
        if (pos>=0 && pos<tamanio){
            //posición válida
            if(pos == 0){
                //El nodo a eliminar esta en la primera posición
                cabeza = cabeza.getSiguiente();
                cabeza.setAnterior(null); //ESTA ES LA NUEVA LINEA
                tamanio--;
            }else{
                if(pos == tamanio-1){//eliminar al final
                    Nodo<T> aux = cabeza;
                    for (int i=0;i<=pos-2;i++){
                        aux = aux.getSiguiente();
                    }
                    Nodo<T> prox = aux.getSiguiente();
                    aux.setSiguiente(prox.getSiguiente());
                    tamanio--;


                } else{ //elimina en medio
                    Nodo<T> aux = cabeza;
                    for (int i=0;i<=pos-2;i++){
                        aux = aux.getSiguiente();
                    }
                    Nodo<T> prox = aux.getSiguiente();
                    prox.getSiguiente().setAnterior(aux); //ESTA ES LA NUEVA LINEA
                    aux.setSiguiente(prox.getSiguiente());
                    tamanio--;
                }

            }
        }else{
            throw new PosicionIlegalException();

        }
        

    }
    /*
     * Devuelve el valor de una determinada posicion
     * @param pos: posicion
     * @return : el valor tipo T
     * @throws : Posición ilegal Exception
     * 
     */
    
    public T getValor(int pos) throws PosicionIlegalException{
        if (pos>=0 && pos<tamanio){
            if(pos == 0){
                return cabeza.getValor();
            }else{
                Nodo<T> aux = cabeza;
                for (int i=0;i<=pos-1;i++){
                    aux = aux.getSiguiente();
                }
                return aux.getValor();
            }

        }else{
            throw new PosicionIlegalException();
        }        

    }   
    public void limpiar(){
        cabeza = null;
        tamanio = 0;
    }
    
}
