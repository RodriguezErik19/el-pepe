package miPrincipal.modelo;

import listaDoble.ListaDoble;
import listaDoble.PosicionIlegalException;
import pila.Pila;
import cola.Cola;
import utilerias.Fecha;
import miPrincipal.servicio.ServicioDatos;
import java.util.Scanner;

public class Libreria{
    ServicioDatos dataService;
    ListaDoble<Libro> listaLibros;
    Cola<Libro> colaLibros;
    Pila<Libro> pilaLibrosEliminados;
    Scanner scanner; 
    ListaDoble<Pedido> listaPedidos; 

    public Libreria(){
        dataService = new ServicioDatos();
        scanner = new Scanner(System.in);
        listaLibros = new ListaDoble<>();
        colaLibros = new Cola<>();
        pilaLibrosEliminados = new Pila<>();
        listaPedidos = new ListaDoble<>();
    }
        public void agregarLibro(Libro libro){
        listaLibros.agregar(libro);
        System.out.println("Se agrego el libro: " + libro.getTitulo());
    }
           
        
    

   public ListaDoble<Libro> obtenerLibros(){
        return listaLibros;
    }

   public boolean agregarLibroCola(Libro libro){
        colaLibros.encolar(libro);
        System.out.println("see encolo correctamente " + libro.getTitulo());
        return true; 
    }
    public Libro obtenerLibroCola(){
        Libro libro = colaLibros.desencolar(); //usammos el desencolar de la clase cola
        if (libro != null) {
            System.out.println("libro retirado: " + libro.getTitulo());
        } else {
            System.out.println("no hay libros :V ");
        }
        return libro;
    }

       
    
    
  
    public Libro obtenerLibroPila(){
        
        Libro libro =pilaLibrosEliminados.cima();
        if (libro!= null) {
            System.out.println("tope de pila: " + libro.getTitulo());
        } else {
            System.out.println("la pila de elimadoss esta vacia");
        }
        return libro; 
    }

public Cola<Libro> mostrarReservaLibros(){
        return colaLibros;
    }
    
public Libro crearLibro(String titulo, String autor, String isbn){
        return new Libro(titulo, autor, isbn);
    }

    public Pedido crearPedido(Libro libro, Fecha fecha){
        
        Pedido nuevoPedido = new Pedido(libro, fecha);
        listaPedidos.agregar(nuevoPedido);
        System.out.println("tu pedido fue registrado: ");
        return nuevoPedido;
    }
   public boolean devolverLibro(Libro libro) {
        try {
            int posEliminada = listaLibros.remover(libro); 
            if (posEliminada != -1) {
                pilaLibrosEliminados.apilar(libro);
                System.out.println("se devolvio el libro : " + libro.getTitulo());
                return true;
            }
            return false;
        } catch (PosicionIlegalException e) {
            return false;
        }
    }

   public Libro eliminarUltimoLibro(){
        if (listaLibros.esVacia()) {
            System.out.println("la lista de libros se encuentra vacia");
            return null;
        }
        
        Libro libroEliminado = null;
        try {
          
            int ultimaPosicion = listaLibros.getTamanio() - 1; //eliminat el ultimo elemento de la lisa
            libroEliminado = listaLibros.remover(ultimaPosicion); 
            pilaLibrosEliminados.apilar(libroEliminado);
            System.out.println(" ultimo libro eliminado y apilado: " + libroEliminado.getTitulo());
        } catch (PosicionIlegalException e) {
            System.err.println("error al eliminar el libro");
        }
        return libroEliminado;
    }
   public Libro deshacerEliminarLibro(){
        if (pilaLibrosEliminados.esVacia()) {
            System.out.println("no hay nada para deshacer");
            return null;
        }
        
       
        Libro libroDeshecho = pilaLibrosEliminados.retirar();  // retira y devuelve el valor del topee
        listaLibros.agregar(libroDeshecho); // agrega de nuevo a la lissta principal
        
        System.out.println("eliminacion deshecha: " + libroDeshecho.getTitulo() + " reinsertado.");
        
        return libroDeshecho;
    }

    
public Libro buscarLibro(String isbn) {

        for (int i = 0; i<listaLibros.getTamanio(); i++) {
            try {
                Libro l =listaLibros.getValor(i);
                if (l.getIsbn().equals(isbn)) {
                    System.out.println("libro encontrado: "+l.getTitulo());
                    return l;
                }
            } catch (PosicionIlegalException e) {
            
            }
        }
        System.out.println("Libro " +isbn+"no encontrado");
        return null;
    }
    public void mostrarLibrosLista() {
    System.out.println("libros disponibles: ");
    
    System.out.println(listaLibros.toString()); 
    System.out.println("tamaño: " + listaLibros.getTamanio());
    System.out.println();
}
}


