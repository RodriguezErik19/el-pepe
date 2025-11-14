package datos;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Email {

    private String correoElectronico;

    public Email(String correoElectronico) {
        if (!validarEmail(correoElectronico)) {
            throw new IllegalArgumentException("Correo electrónico no válido: " + correoElectronico);
        }
        this.correoElectronico = correoElectronico;
    }

    public String obtenerCorreoElectronico() {
        return correoElectronico;
    }

    private static boolean validarEmail(String email) {

        String regex = "^[\\w\\.-]+@([\\w\\.-]+\\.)+[\\w\\.-]{2,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return correoElectronico;
    }

}