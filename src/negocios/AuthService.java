package negocios;

import datos.Usuario;
import negocios.excepciones.ErrorFisicoException;

/**
 * Esqueleto de servicio de autenticación para la práctica.
 * Los alumnos deben implementar la lógica de verificación, persistencia
 * y políticas (intentos, bloqueo, hashing opcional).
 */
public class AuthService {

    /**
     * Intenta autenticar con las credenciales proporcionadas.
     * Debe devolver el objeto Usuario en caso de éxito, o null si falla.
     *
     * Implementación recomendada para la práctica:
     *  - leer archivo de usuarios (p. ej. usuario.properties o usuarios.txt)
     *  - comprobar nombre + contraseña (opcional: usar hash)
     *  - en caso de éxito, retornar Usuario con nombre, email y ruta
     *
     * @throws ErrorFisicoException en errores de E/S
     */
    public Usuario login(String username, String password) throws ErrorFisicoException {
        // TODO (Alumno): implementar autenticación. Para empezar, pueden delegar
        // en uI.Aplicacion.autenticarUsuario que ya existe, o leer usuario.properties.
        throw new UnsupportedOperationException("TODO (Alumno): implementar AuthService.login");
    }

    /**
     * Método opcional: registrar o actualizar credenciales de usuario.
     * Los alumnos pueden implementarlo si desean permitir múltiples usuarios.
     */
    public boolean register(Usuario u, String password) throws ErrorFisicoException {
        // TODO (Alumno): persistir usuario en archivo seguro (o en memoria para pruebas)
        throw new UnsupportedOperationException("TODO (Alumno): implementar AuthService.register");
    }
}
