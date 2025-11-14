package miTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import datos.Usuario;
import negocios.excepciones.ErrorFisicoException;
import uI.Aplicacion;

public class AuthServiceBadCredsTest {

    @Test
    public void test_loginInvalido() throws ErrorFisicoException {
        Aplicacion app = new Aplicacion();
        Usuario u = app.autenticarUsuario("usuarioX", "badpass");
        assertNull(u, "El login con credenciales inválidas debe devolver null");
    }
}
