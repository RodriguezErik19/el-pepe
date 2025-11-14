package datos;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class Configuracion{
    private static Properties propiedades;

    static {
        propiedades = new Properties();
        try (//FileInputStream fis = new FileInputStream("datos/usuario.properties")) 
            FileInputStream fis = new FileInputStream("usuario.properties")){
            propiedades.load(fis);
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de propiedades: " + e.getMessage());
        }
    }

    public static String get(String clave) {
        return propiedades.getProperty(clave);
    }

    public static boolean getBoolean(String clave) {
        return Boolean.parseBoolean(propiedades.getProperty(clave));
    }

}