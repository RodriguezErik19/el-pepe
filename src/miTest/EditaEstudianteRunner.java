package miTest;

import negocios.Universidad;
import datos.UniversidadDAO;
import negocios.UniversidadService;
import datos.Estudiante;
import datos.Fecha;

public class EditaEstudianteRunner {
    public static void main(String[] args) {
        try {
            Universidad dominio = new Universidad();
            UniversidadDAO dao = new UniversidadDAO();
            UniversidadService svc = new UniversidadService(dominio, dao, "miTest/archivos", "Test", "test@example.com");

            // Agregar estudiante
            Fecha f = new Fecha(15,4,2000);
            boolean added = svc.agregarEstudiante(2000, "Juan Perez", "juan.perez@example.com", f, "M", "Ingenieria");
            System.out.println("Agregado: " + added);

            // Buscar y mostrar
            Estudiante e1 = svc.buscarEstudiante(2000);
            System.out.println("Antes: " + (e1==null?"NULL":e1.getNombre()+" / "+e1.getEmail()));

            // Editar
            boolean edited = svc.editarEstudiante(2000, "Juan P. Edited", null, null, null, null);
            System.out.println("Editado: " + edited);

            Estudiante e2 = svc.buscarEstudiante(2000);
            System.out.println("Despues: " + (e2==null?"NULL":e2.getNombre()+" / "+e2.getEmail()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
