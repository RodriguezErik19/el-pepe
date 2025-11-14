package miPrincipal.iu;
import java.util.Scanner;

import listaDoble.PosicionIlegalException;
public class Menu{
    static Scanner scanner = new  Scanner(System.in);
	
		
	public void lectura() throws PosicionIlegalException {
			
		imprimirMenu();	
				
	}
    private static void imprimirMenu() throws PosicionIlegalException {
        int opcion;
       
        do {
            System.out.println("Menú:");
            System.out.println("1. Agregar libro a la lista (Prestar Libros)");
            System.out.println("2. Mostrar libros de la lista (Libros Prestados)");
            System.out.println("3. Agregar libro a la cola (Reserva de  Libros)");
            System.out.println("4. Obtener libro de la cola (Siguiente Libro para Prestamo)");
            System.out.println("5. Mostrar cola de libros en reserva");
            System.out.println("6. Elaborar Pedido");
            System.out.println("7. Devolver libro prestado");
            System.out.println("8. Eliminar ultimo libro prestada (correccion prestamo)");
            System.out.println("9. Deshacer eliminacion de libro (Restaura operacion de eliminar ultimo prestamo)");
            System.out.println("0. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcion) {
                case 1:
                    MenuOpciones.agregarLibro();
                    break;
                case 2:
                    
                    MenuOpciones.mostrarLibros();
                    break;
                case 3:
                    
                    MenuOpciones.agregarLibroCola();
                    break;
                case 4:
                    
                    MenuOpciones.obtenerLibroCola();
                    break;
                case 5:
                   
                    MenuOpciones.mostrarReservaLibros();
                    break;
                case 6:
                   
                    MenuOpciones.crearPedido();
                    break;
                case 7:
                  
                    MenuOpciones.devolverLibro();
                    break;
                case 8:
                    
                    MenuOpciones.eliminarUltimoLibro();
                    break;
                case 9:
                   
                    MenuOpciones.deshacerEliminarLibro();
                    break;
                case 0:
                    salir();
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        } while (opcion != 0);

        scanner.close();
    }
    private static void salir() {
		System.out.println("Sesion Finalizada");
		System.out.println("Adios!");
		System.exit(0);
	}

}