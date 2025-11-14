package miTest;
import datos.*;
import negocios.*;

public class SimularPrestamoDAO {
    public static void main(String[] args) throws Exception {
        Universidad u = new Universidad();
        UniversidadDAO dao = new UniversidadDAO();
        UniversidadService svc = new UniversidadService(u, dao, "src/miTest/archivos", "tester", "t@t");

        // ensure clean start: remove any prestamos for product 40
        try { dao.limpiarPrestamosPorProductoOEstudiante(40, -1); } catch (Exception e) {}

        u.agregarEstudiante(20240001, "Ana Lopez", "anal@itculiacan.edu.mx", new Fecha("01/01/2006"), "Femenino", "Sistemas");
        u.agregarEstudiante(20240002, "Pedro Buelna", "pedrob@itculiacan.edu.mx", new Fecha("24/12/2005"), "Masculino", "Sistemas");
        u.agregarProducto(FamiliaTipo.DEPORTIVOS, 40, "Bat de beisbol");

        Fecha f1 = new Fecha("30/10/2025");
        Fecha f2 = new Fecha("04/11/2025");

        System.out.println("Estado inicial producto 40 disponible? " + u.buscarProductoPorId(40).isDisponible());
        boolean ok1 = svc.prestarProducto(20240001, 40, f1);
        System.out.println("Prestado a 20240001: " + ok1);
        System.out.println("Disponible después de prestar? " + u.buscarProductoPorId(40).isDisponible());

        boolean dev = svc.devolverProducto(40, f2);
        System.out.println("Devolución realizada: " + dev);
        System.out.println("Disponible después de devolución? " + u.buscarProductoPorId(40).isDisponible());

        boolean ok2 = svc.prestarProducto(20240002, 40, f2);
        System.out.println("Intento prestar a 20240002: " + ok2);
        System.out.println("Disponible final? " + u.buscarProductoPorId(40).isDisponible());

        System.out.println("Prestamo activo desde servicio: " + svc.obtenerPrestamoActivoPorProducto(40));
    }
}
