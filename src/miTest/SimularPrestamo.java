package miTest;
import datos.*;
import negocios.*;

public class SimularPrestamo {
    public static void main(String[] args) throws Exception {
        Universidad u = new Universidad();
        // agregar estudiantes
        u.agregarEstudiante(20240001, "Ana Lopez", "anal@itculiacan.edu.mx", new Fecha("01/01/2006"), "Femenino", "Sistemas");
        u.agregarEstudiante(20240002, "Pedro Buelna", "pedrob@itculiacan.edu.mx", new Fecha("24/12/2005"), "Masculino", "Sistemas");
        // agregar producto 40
        u.agregarProducto(FamiliaTipo.DEPORTIVOS, 40, "Bat de beisbol");

        Fecha f1 = new Fecha("30/10/2025");
        Fecha f2 = new Fecha("04/11/2025");

        System.out.println("Estado inicial producto 40 disponible? " + u.buscarProductoPorId(40).isDisponible());
        boolean ok1 = u.prestarProducto(20240001, 40, f1);
        System.out.println("Prestado a 20240001: " + ok1);
        System.out.println("Disponible después de prestar? " + u.buscarProductoPorId(40).isDisponible());

        boolean dev = u.devolverProducto(40, f2);
        System.out.println("Devolución realizada: " + dev);
        System.out.println("Disponible después de devolución? " + u.buscarProductoPorId(40).isDisponible());

        boolean ok2 = u.prestarProducto(20240002, 40, f2);
        System.out.println("Intento prestar a 20240002 (debería ser true): " + ok2);
        System.out.println("Disponible final? " + u.buscarProductoPorId(40).isDisponible());

        // mostrar préstamo activo para el producto 40
        System.out.println("Préstamo activo para producto 40: " + u.obtenerPrestamoActivoPorProducto(40));
        // mostrar historial por estudiante
        System.out.println("Historial estudiante 20240001: " + u.obtenerPrestamosEstudiante(20240001));
        System.out.println("Historial estudiante 20240002: " + u.obtenerPrestamosEstudiante(20240002));
    }
}
