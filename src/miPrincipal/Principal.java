package miPrincipal;
import uI.Menu;
import uI.Aplicacion;
import negocios.Universidad;
import negocios.UniversidadService;
import datos.UniversidadDAO;
import java.util.Scanner;

import datos.Usuario;

public class Principal {
    public static void main(String[] args) {
        try {
            // Instanciamos la clase Aplicacion
            Aplicacion app = new Aplicacion();
            Scanner scanner = new Scanner(System.in);

            // Solicitar credenciales al usuario con hasta 3 intentos
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║               AUTENTICACIÓN DE USUARIO           ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            Usuario u = null;
            int intentos = 0;
            final int MAX_INTENTOS = 3;
            while (intentos < MAX_INTENTOS && u == null) {
                System.out.print("║ Ingrese su nombre de usuario: ");
                String username = scanner.nextLine();
                System.out.println("║");
                System.out.print("║ Ingrese su contraseña: ");
                String password = scanner.nextLine();
                System.out.println("║");

                u = app.autenticarUsuario(username, password);
                if (u == null) {
                    intentos++;
                    int restantes = MAX_INTENTOS - intentos;
                    if (restantes > 0) {
                        System.out.println("Usuario y/o contraseña incorrectos. Le quedan " + restantes + " intento(s).\n");
                    } else {
                        System.out.println("Usuario y/o contraseña incorrectos. Se han agotado los intentos. Saliendo.");
                    }
                }
            }
            System.out.println("╚══════════════════════════════════════════════════╝");

            // Validar si el login fue exitoso
            if (u == null) {
                return; // Salir del programa si el login falla
            }

            // Obtener la ruta de trabajo resuelta por el DAO (muestra la ruta efectiva en disco)
            UniversidadDAO universidadDAO = new UniversidadDAO();
            String rutaTrabajo = universidadDAO.getBaseDir();
            // Obtener la ruta configurada en properties (lo que el usuario especificó)
            boolean modoPruebas = datos.Configuracion.getBoolean("modo.pruebas");
            String rutaConfigurada = modoPruebas
                ? datos.Configuracion.get("ruta.pruebas")
                : datos.Configuracion.get("ruta.produccion");

            // Mostrar mensaje de bienvenida incluyendo modo y ruta
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║               BIENVENIDO A LA APLICACIÓN         ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            System.out.println("║ " + ajustarTexto("Nombre: " + u.getNombreUsuario(), 49) + "║");
            System.out.println("║ " + ajustarTexto("Email: " + u.getEmail(), 49) + "║");
            String modoStr = modoPruebas ? "PRUEBAS" : "PRODUCCIÓN";
            System.out.println("║ " + ajustarTexto("Modo: " + modoStr + "  Ruta: " + rutaConfigurada, 49) + "║");
            System.out.println("║ " + ajustarTexto("Ruta usada: " + rutaTrabajo, 49) + "║");
            System.out.println("╚══════════════════════════════════════════════════╝");

            // Inicializar dominio y servicio y luego iniciar el menú
            Universidad dominio = new Universidad();
            UniversidadService universidadService = new UniversidadService(dominio, universidadDAO, rutaTrabajo, u.getNombreUsuario(), u.getEmail());
            Menu menu = new Menu(universidadService, rutaTrabajo, u.getNombreUsuario(), u.getEmail());
            menu.iniciar();

        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Ajusta el texto al ancho especificado, recortándolo si es demasiado largo
     * o rellenándolo con espacios si es demasiado corto.
     *
     * @param texto El texto a ajustar.
     * @param ancho El ancho máximo permitido.
     * @return El texto ajustado.
     */
    private static String ajustarTexto(String texto, int ancho) {
        if (texto.length() > ancho) {
            return texto.substring(0, ancho - 3) + "..."; // Recortar y agregar "..."
        } else {
            return String.format("%-" + ancho + "s", texto); // Rellenar con espacios
        }
    }
    


}
