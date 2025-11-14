package miTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import negocios.Universidad;
import negocios.utilidades.listaDoble.PosicionIlegalException;
import datos.Fecha;

public class EditarEstudianteValidationTest {

    @Test
    public void testEditarEstudianteConEmailInvalidoNoModificaEmail() throws PosicionIlegalException {
        Universidad u = new Universidad();
        int codigo = 777777;
        // agregar estudiante con email válido
        u.agregarEstudiante(codigo, "Test Usuario", "user.valid@example.com", new Fecha("1/1/1990"), "M", "CS");

        // intentar editar con email inválido -> Universidad.editarEstudiante lanzará IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            u.editarEstudiante(codigo, null, "email-invalido", null, null, null);
        });

        // el email debe permanecer igual al original
    datos.Estudiante e = u.buscarEstudiante(codigo);
    assertNotNull(e);
    assertEquals("user.valid@example.com", e.getEmail().obtenerCorreoElectronico());
    }

}
