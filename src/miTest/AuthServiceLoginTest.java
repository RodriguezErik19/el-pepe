package miTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import datos.Usuario;
import negocios.excepciones.ErrorFisicoException;
import uI.Aplicacion;

public class AuthServiceLoginTest {

    @Test
    public void test_loginValido() throws ErrorFisicoException {
        Aplicacion app = new Aplicacion();
        // Credenciales tomadas de usuario.properties en la rama student-version
        Usuario u = app.autenticarUsuario("LourdesA", "123");
        assertNotNull(u, "El login con credenciales válidas debe devolver un Usuario");
        assertEquals("Maria Lourdes Armenta Lindoro", u.getNombreUsuario(), "El nombre debe coincidir con el registrado en usuario.properties");
    }
}
