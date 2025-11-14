package uI;
import datos.Usuario;
import negocios.excepciones.ErrorFisicoException;

import java.io.FileInputStream;
import java.util.Properties;

import datos.Configuracion;
public class Aplicacion{
    String ruta;

    public Usuario autenticarUsuario(String inputUsr, String inputPwd) throws ErrorFisicoException {
        FileInputStream fis = null;

        try {
            // Abrimos el archivo de propiedades para lectura
            fis = new FileInputStream("usuario.properties");
            Properties p = new Properties();
            p.load(fis);

            // Leemos los valores del archivo de propiedades
            String usr = p.getProperty("usrname");
            String pwd = p.getProperty("password");
            String nombre = p.getProperty("nombre");
            String email = p.getProperty("email");

            // Determinar la ruta de trabajo según el modo
            String ruta = Configuracion.getBoolean("modo.pruebas")
                    ? Configuracion.get("ruta.pruebas")
                    : Configuracion.get("ruta.produccion");

            // Validar credenciales
            if (usr.equals(inputUsr) && pwd.equals(inputPwd)) {
                System.out.println("Autenticación exitosa. ¡Bienvenido!");

                // Crear y retornar el objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setNombreUsuario(nombre);
                usuario.setContrasena(pwd);
                usuario.setEmail(email);
                usuario.setRuta(ruta);
                return usuario;
            } else {
                System.out.println("Credenciales incorrectas.");
                return null;
            }
        } catch (Exception ex) {
            throw new ErrorFisicoException("Error al autenticar al usuario", ex);
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
    
}