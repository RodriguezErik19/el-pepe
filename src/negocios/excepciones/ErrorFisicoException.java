package negocios.excepciones;
public class ErrorFisicoException extends Exception {
    public ErrorFisicoException(String message) {
        super(message);
    }

    public ErrorFisicoException(String message, Throwable cause) {
        super(message, cause);
    }
}