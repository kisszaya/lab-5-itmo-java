package exceptions;

public class InvalidFieldException extends Exception {
    public String messageError;

    InvalidFieldException(String messageError) {
        this.messageError = messageError;
    }
}
