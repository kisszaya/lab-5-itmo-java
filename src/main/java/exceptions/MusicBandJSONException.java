package exceptions;

public class MusicBandJSONException extends Exception {
    public String messageError;
    public String fieldName;

    public MusicBandJSONException(String messageError, String fieldName) {
        this.messageError = messageError;
        this.fieldName = fieldName;
    }
}
