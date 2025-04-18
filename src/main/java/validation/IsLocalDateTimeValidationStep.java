package validation;

import java.time.LocalDateTime;

public class IsLocalDateTimeValidationStep extends ValidationStep<String> {

    @Override
    public ValidationResult validate(String toValidate) {
        if (IsNullHelper.isNull(toValidate)) {
            return this.checkNext(toValidate);
        }

        try {
            LocalDateTime.parse(toValidate);
        } catch (NumberFormatException err) {
            return ValidationResult.invalid("Поле должно быть date");
        }
        return this.checkNext(toValidate);
    }
}
