package uI;

import java.util.Scanner;

import datos.Email;
import datos.Fecha;

import negocios.UniversidadService;
import negocios.utilidades.Util;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

public class MenuEstudiantes {
    private int codigo;
    private String nombre;
    private Email email;
    private Fecha fechaNac;
    private String sexo;
    private String programa;

    private UniversidadService universidadService;

    public MenuEstudiantes(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }

    public void agregarEstudiantes(Scanner scanner) {
        agregarConNavegacionEstudiantes(scanner);
    }

    public void eliminarEstudiantes(Scanner scanner) {
        eliminarConNavegacionEstudiantes(scanner);
    }

    public void mostrarEstudiantes(Scanner scanner) {
        try {
            ListaDoble<datos.Estudiante> lista = universidadService.mostrarEstudiantes();
            if (lista == null || lista.getTamanio() == 0) {
                System.out.println("No hay estudiantes cargados.");
                return;
            }
            System.out.println("Lista de estudiantes:");
            for (int i = 0; i < lista.getTamanio(); i++) {
                datos.Estudiante e = lista.getValor(i);
                System.out.println("Cód: " + e.getCodigo() + "  Nom: " + e.getNombre() +
                        "  Email: " + e.getEmail() + "  Fec: " + e.getFechaNac() +
                        "  Gén: " + e.getSexo() + "  Prog: " + e.getPrograma());
            }
        } catch (PosicionIlegalException ex) {
            System.out.println("Error mostrando estudiantes: " + ex.getMessage());
        }
    }

    public void agregarConNavegacionEstudiantes(Scanner scanner) {
        // Guía concreta para alumnos: ejemplo de flujo y llamada al servicio.
        System.out.println("[GUÍA] Captura de estudiante (ejemplo práctico):");
        System.out.println("Paso 1: leer código (ej:) int codigo = Util.leerEntero(\"Código:\", scanner, 0, 99999999, false);");
        System.out.println("Paso 2: leer nombre: System.out.print(\"Nombre:\"); String nombre = scanner.nextLine();");
        System.out.println("Paso 3: leer email: System.out.print(\"Email:\"); String email = scanner.nextLine();");
        System.out.println("Paso 4: validar email: new datos.Email(email);  // lanza IllegalArgumentException si es inválido");
        System.out.println("Paso 5: leer fecha (DD/MM/AAAA): System.out.print(\"Fecha:\"); String s = scanner.nextLine();");
        System.out.println("Paso 6: convertir fecha: negocios.utilidades.Fecha f = new negocios.utilidades.Fecha(s); datos.Fecha df = new datos.Fecha(f.getDia(), f.getMes(), f.getAnio());");
        System.out.println("Paso 7: llamar al servicio: universidadService.agregarEstudiante(codigo, nombre, email, df, \"F\", \"Sistemas\");");
        System.out.println("Ejemplo real (código):");
        System.out.println("  try {\n    universidadService.agregarEstudiante(codigo, nombre, email, df, sexo, programa);\n  } catch (negocios.utilidades.listaDoble.PosicionIlegalException e) {\n    System.out.println(\"No se pudo agregar estudiante: \"+e.getMessage());\n  }");
        System.out.println("NOTA: maneje excepciones y valide entradas antes de invocar el servicio.");

    }

    public void eliminarConNavegacionEstudiantes(Scanner scanner) {
        // Guía concreta para eliminar (ejemplo con llamada al servicio)
        System.out.println("[GUÍA] Eliminar estudiante (ejemplo práctico):");
        System.out.println("1) Mostrar estudiantes: ListaDoble<datos.Estudiante> lista = universidadService.mostrarEstudiantes();");
        System.out.println("2) Leer código: int codigo = Util.leerEntero(\"Código a eliminar (-1 cancelar):\", scanner, 0, 99999999, true);");
        System.out.println("3) Confirmar con el usuario y luego: universidadService.eliminarEstudiante(codigo);");
        System.out.println("Ejemplo real (código):");
        System.out.println("  try {\n    boolean ok = universidadService.eliminarEstudiante(codigo);\n    if(ok) System.out.println(\"Eliminado\"); else System.out.println(\"No existe o no se pudo eliminar\");\n  } catch (negocios.utilidades.listaDoble.PosicionIlegalException e) {\n    System.out.println(\"Error: \"+e.getMessage());\n  }");
        System.out.println("NOTA: verifique existencia antes y maneje excepciones en la implementación del alumno.");

    }

    public void editarEstudiantes(Scanner scanner) {
        // Guía concreta para editar (ejemplo con llamadas al servicio)
        System.out.println("[GUÍA] Editar estudiante (ejemplo práctico):");
        System.out.println("1) Mostrar estudiantes y pedir código: int codigo = Util.leerEntero(...)");
        System.out.println("2) Pedir nuevos valores (o -1 para mantener actual). Validar email y fecha como en agregar.");
        System.out.println("3) Llamar al servicio:");
        System.out.println("  try {\n    boolean ok = universidadService.editarEstudiante(codigo, nombre, email, df, sexo, programa);\n    if(ok) System.out.println(\"Editado\"); else System.out.println(\"No se pudo editar\");\n  } catch (negocios.utilidades.listaDoble.PosicionIlegalException e) {\n    System.out.println(\"Error: \"+e.getMessage());\n  }");
        System.out.println("NOTA: Esta guía muestra la llamada concreta al servicio que debe implementar el alumno desde la UI.");
    }

    // Nota para el alumno: las variables de instancia (codigo, nombre, email, fechaNac, sexo, programa)
    // se mantienen en la clase para que puedan reutilizarse en implementaciones de los métodos
    // que el alumno desarrollará. Es normal que en la versión esqueleto no se usen aún.

}
