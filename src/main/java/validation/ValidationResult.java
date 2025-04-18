package validation;

public class ValidationResult {
    private final boolean isValid;
    private final String message;

    private ValidationResult (boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    static public ValidationResult valid () {
        return new ValidationResult(true, null);
    }

    static public ValidationResult invalid (String message) {
        return new ValidationResult(false, message);
    }

    public boolean getIsValid () {
        return this.isValid;
    }

    public String getMessage () {
        return this.message;
    }
}
