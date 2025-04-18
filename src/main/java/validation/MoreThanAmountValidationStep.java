package validation;

public class MoreThanAmountValidationStep extends ValidationStep<String> {
    Integer moreThan;

    public MoreThanAmountValidationStep(Integer moreThan) {
        this.moreThan = moreThan;
    }

    @Override
    public ValidationResult validate(String toValidate) {
        if (IsNullHelper.isNull(toValidate)) {
            return this.checkNext(toValidate);
        }

        try {
            Double parsed = Double.parseDouble(toValidate);

            if (parsed.compareTo(Double.valueOf(moreThan)) <= 0) {
                return ValidationResult.invalid("Поле должно быть больше " + this.moreThan);
            }

            return this.checkNext(toValidate);
        } catch (NumberFormatException err) {
            return ValidationResult.invalid("Поле нельзя сравнить и понять больше оно или нет " + this.moreThan);
        }
    }
}
