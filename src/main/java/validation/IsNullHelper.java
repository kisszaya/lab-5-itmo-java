package validation;

public class IsNullHelper {
    static public boolean isNull(String value) {
        if (value == null) {
            return true;
        }

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            return true;
        }
        return false;
    }
}
