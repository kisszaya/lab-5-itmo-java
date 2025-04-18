package validation;

public class NotNullValidationStep extends ValidationStep<String> {

    @Override
    public ValidationResult validate(String toValidate) {
        if (IsNullHelper.isNull(toValidate)) {
            return ValidationResult.invalid("Поле не должно быть null");
        }

        return this.checkNext(toValidate);
    }
}
