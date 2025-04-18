package validation;

public class MoreThanSymbolsValidationStep extends ValidationStep<String> {
    Integer moreThan;

    public MoreThanSymbolsValidationStep(Integer moreThan) {
        this.moreThan = moreThan;
    }

    @Override
    public ValidationResult validate(String toValidate) {
        if (IsNullHelper.isNull(toValidate)) {
            return this.checkNext(toValidate);
        }

        if (toValidate.length() <= moreThan) {
            return ValidationResult.invalid("Поле должно иметь символов больше чем " + this.moreThan);
        }

        return this.checkNext(toValidate);
    }
}
