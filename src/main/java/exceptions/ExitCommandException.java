package exceptions;

public class ExitCommandException extends RuntimeException {
    public ExitCommandException() {
        super("Exit command received");
    }
}
