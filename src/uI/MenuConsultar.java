package uI;

import java.util.Scanner;

import negocios.UniversidadService;
import negocios.utilidades.Util;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

public class MenuConsultar {
    private UniversidadService universidadService;

    public MenuConsultar(UniversidadService universidadService) {
      this.universidadService = universidadService;
    }
    // Versión esqueleto para estudiantes: los métodos mantienen firmas
    // pero delegan a la implementación que el alumno debe completar.
    // TODO (Alumno): implementar navegación y llamadas a `UniversidadService`.

    public void consultarPorProductoPrestado(){
       // Guía corta para el alumno:
       // 1) Pedir id con Util.leerEntero(...)
       // 2) Usar universidadService.buscarNombreProducto(id) para mostrar nombre
       // 3) Usar universidadService.obtenerPrestamoActivoPorProducto(id) para mostrar si está prestado
       System.out.println("[TODO - Alumno] consultarPorProductoPrestado: implementar la UI de consulta por producto (ver README-STUDENT.md)");
    }

    public void consultarPorEstudiantePrestado(){
       System.out.println("[TODO - Alumno] consultarPorEstudiantePrestado: implementar la UI de consulta por estudiante (ver README-STUDENT.md)");
    }

    public void consultarPorEstudiantesMasDeTres(){
       System.out.println("[TODO - Alumno] consultarPorEstudiantesMasDeTres: implementar la UI que lista estudiantes con >3 prestamos");
    }

    // Métodos auxiliares que los tests o el resto de la app podrían invocar.
    // Mantener firmas públicas para compatibilidad con llamadas existentes en la app.
    public void consultarNavegacionPorProductoPrestado(Scanner scanner){
        System.out.println("[TODO - Alumno] implementar consultarNavegacionPorProductoPrestado(Scanner)");
    }

    public void consultarNavegacionPorEstudiantePrestado(Scanner scanner){
        System.out.println("[TODO - Alumno] implementar consultarNavegacionPorEstudiantePrestado(Scanner)");
    }

    public void consultarNavegacionPorEstudiantesMasDeTres(Scanner scanner){
        System.out.println("[TODO - Alumno] implementar consultarNavegacionPorEstudiantesMasDeTres(Scanner)");
    }

    public void consultarPorPalabraClave(){
        System.out.println("[TODO - Alumno] implementar consultarPorPalabraClave: pedir palabra y usar universidadService.buscarProductosPorPalabraClave");
    }
}
