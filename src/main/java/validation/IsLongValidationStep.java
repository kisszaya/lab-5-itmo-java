package validation;

public class IsLongValidationStep extends ValidationStep<String> {

    @Override
    public ValidationResult validate(String toValidate) {
        if (IsNullHelper.isNull(toValidate)) {
            return this.checkNext(toValidate);
        }

        try {
            Long.parseLong(toValidate);
        } catch (NumberFormatException err) {
            return ValidationResult.invalid("Поле должно быть типа long");
        }
        return this.checkNext(toValidate);
    }
}
