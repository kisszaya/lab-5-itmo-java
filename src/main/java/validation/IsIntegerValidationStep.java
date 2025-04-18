package validation;

public class IsIntegerValidationStep extends ValidationStep<String> {

    @Override
    public ValidationResult validate(String toValidate) {
        if (IsNullHelper.isNull(toValidate)) {
            return this.checkNext(toValidate);
        }

        try {
            Integer.parseInt(toValidate);
        } catch (NumberFormatException err) {
            return ValidationResult.invalid("Поле должно быть типа integer");
        }
        return this.checkNext(toValidate);
    }
}
