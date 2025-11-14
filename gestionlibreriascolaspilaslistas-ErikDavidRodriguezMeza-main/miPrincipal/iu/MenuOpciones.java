package miPrincipal.iu;
import miPrincipal.modelo.Libro;
import miPrincipal.modelo.Pedido;
import miPrincipal.modelo.Libreria;
import java.util.Scanner;
import utilerias.Fecha;
import listaDoble.PosicionIlegalException;

public class MenuOpciones{
    
    
    static Scanner scanner =new Scanner(System.in);
    static private Libreria libreria =new Libreria();

    public static void agregarLibro(){      
        System.out.print("ingrese el título del libro:");
        String titulo = scanner.nextLine();
        System.out.print("ingrese el autor del libro:");
        String autor = scanner.nextLine();
        System.out.print("ingrese el ISBN del libro:");
        String isbn = scanner.nextLine();
        Libro libro = libreria.crearLibro(titulo, autor, isbn);
        if (libro != null) {        //sino es nullo se agregara
            libreria.agregarLibro(libro);
        } else {
            System.out.println("error al crear el libro, intentelo nuevamente");
        }
    }
    
    public static void mostrarLibros() { 
        libreria.mostrarLibrosLista();
    }

    public static void agregarLibroCola(){
        System.out.print("ingrese el titulo del libro a reservar:");
        String titulo = scanner.nextLine();
        System.out.print("ingrese el ISBN del libro a reservar:");
        String isbn = scanner.nextLine();
        
        Libro libroReserva =libreria.buscarLibro(isbn);
        if (libroReserva !=null) {
            libreria.agregarLibroCola(libroReserva);
        } else {
          
            Libro nuevoLibro =libreria.crearLibro(titulo, "Desconocido", isbn);
            libreria.agregarLibroCola(nuevoLibro);
        }
    }

    public static Libro obtenerLibroCola(){
        Libro libroAtendido =libreria.obtenerLibroCola();
        if (libroAtendido != null) {
            System.out.println("libro atendido de la cola: " + libroAtendido.getTitulo());
        } else {
            System.out.println("La cola de reservas esta vacia.");
        }
        return libroAtendido;
    }

    public static void mostrarReservaLibros(){
        libreria.mostrarReservaLibros();
    }

    public static void crearPedido(){
        System.out.print("Ingrese el título del libro para el pedido:");
        String tituloPedido = scanner.nextLine();
        System.out.print("Ingrese el autor del libro para el pedido:");
        String autorPedido = scanner.nextLine();
        System.out.print("Ingrese el ISBN del libro para el pedido:");
        String isbnPedido = scanner.nextLine();
        Libro libroPedido = libreria.crearLibro(tituloPedido, autorPedido, isbnPedido);
        Pedido pedido=null;
        if (libroPedido!=null){
            pedido = libreria.crearPedido(libroPedido, new Fecha());
            if (pedido !=null)
                System.out.println("Pedido creado exitosamente: "+pedido);
            else
                System.out.println("No fue posible crear el pedido");
        }else{
            System.out.println("Error: no fue posible crear el Libro");
        }
        
    }

    public static void devolverLibro() throws PosicionIlegalException{
        System.out.print("ingrese el isbn del libro a devolver :");
        String isbn =scanner.nextLine();
        Libro libroADevolver =libreria.buscarLibro(isbn);
        if (libroADevolver != null) {
            if (libreria.devolverLibro(libroADevolver)) {
                System.out.println("libro devuelto y apilado");
            } else {
                System.out.println("el libro no estaba en la lista");
            }
        } else {
            System.out.println("libro no encontrado");
        }
    }

    public static Libro eliminarUltimoLibro() throws PosicionIlegalException{
        Libro eliminado =libreria.eliminarUltimoLibro();
        if (eliminado != null) {
            System.out.println("ultimo libbro eliminado: " +eliminado.getTitulo());
        }
        return eliminado;
    }

    public static void deshacerEliminarLibro(){
        Libro deshecho =libreria.deshacerEliminarLibro();
        if (deshecho != null) {
            System.out.println("operacion de eliminacion deshecha para: " + deshecho.getTitulo());
        }
    }

}