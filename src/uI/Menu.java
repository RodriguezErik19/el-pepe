package uI;
import java.util.Scanner;


import negocios.UniversidadService;
import negocios.utilidades.Util;

public class Menu {
    private UniversidadService universidadService;
    private String ruta;
    private String nombreUsuario;
    private String emailUsuario;
    public Menu( String ruta, String nombreUsuario, String emailUsuario) {
        this.ruta = ruta;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
    }
    

    public Menu(UniversidadService universidadService, String ruta, String nombreUsuario, String emailUsuario) {
        this.universidadService = universidadService;
        this.ruta = ruta;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
    }


    public void iniciar(){
        Scanner scanner = new Scanner(System.in);
       
        int opcion = -1; 
        do{
            mostrarMenu();
            opcion = Util.leerEntero("",scanner,1,99,false);
            
            switch (opcion) {
                case 1:
                    new MenuProductos(universidadService).agregarProductos(scanner);
                    break;
                case 2:
                    new MenuProductos(universidadService).eliminarProductos(scanner);
                    break;
                case 3:
                    new MenuProductos(universidadService).mostrarProductos(scanner);
                    break;
                case 4:
                    new MenuEstudiantes(universidadService).agregarEstudiantes(scanner);
                    break;
                case 5:
                    new MenuEstudiantes(universidadService).eliminarEstudiantes(scanner);
                    break;
                case 6:
                    new MenuEstudiantes(universidadService).editarEstudiantes(scanner);
                    break;
                case 7:
                    new MenuEstudiantes(universidadService).mostrarEstudiantes(scanner);
                    break;
                case 8:
                    new MenuPrestarDevolver(universidadService).prestarProducto(scanner);
                    break;
                case 9:
                    new MenuPrestarDevolver(universidadService).devolverProducto(scanner);
                    break;
                case 10:
                    new MenuConsultar(universidadService).consultarPorProductoPrestado();
                    break;
                case 11:
                    new MenuConsultar(universidadService).consultarPorEstudiantePrestado();
                    break;
                case 12:
                    new MenuConsultar(universidadService).consultarPorEstudiantesMasDeTres();
                    break;
                case 13:
                    new MenuConsultar(universidadService).consultarPorPalabraClave();
                    break;
                case 99:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        }while (opcion !=99);
        scanner.close();
    }
    private void mostrarMenu() {
        System.out.println("\t************ MENU PRINCIPAL *************");
			System.out.println("\t*  1. Agregar Productos                 *");
			System.out.println("\t*  2. Eliminar Productos                *");
			System.out.println("\t*  3. Mostrar Productos                 *");
            System.out.println("\t*  4. Agregar Estudiante                *");
            System.out.println("\t*  5. Eliminar Estudiante               *");
            System.out.println("\t*  6. Editar Estudiante                 *");
            System.out.println("\t*  7. Mostrar Estudiantes               *");
            System.out.println("\t*  8. Prestar un Producto               *");
            System.out.println("\t*  9. Devolución de un Producto         *");
            System.out.println("\t* 10. Consultar por producto prestado   *");
            System.out.println("\t* 11. Consultar por estudiante prestado *");
            System.out.println("\t* 12. Consultar por estudiante con mas  *");
            System.out.println("\t*     de TRES productos prestados       *");
            System.out.println("\t* 13. Buscar productos por palabra clave*");
			System.out.println("\t*                                       *");
			System.out.println("\t* 99. Salir                             *");
			System.out.println("\t*****************************************");
			System.out.print("Seleccione opción ->");
    }
    
    
}
