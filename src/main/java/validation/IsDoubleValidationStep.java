package validation;

public class IsDoubleValidationStep extends ValidationStep<String> {

    @Override
    public ValidationResult validate(String toValidate) {
        if (IsNullHelper.isNull(toValidate)) {
            return this.checkNext(toValidate);
        }

        try {
            Double.parseDouble(toValidate);
        } catch (NumberFormatException err) {
            return ValidationResult.invalid("Поле должно быть типа double");
        }
        return this.checkNext(toValidate);
    }
}
