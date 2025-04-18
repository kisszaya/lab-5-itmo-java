package validation;

import java.util.Arrays;
import java.util.stream.Collectors;

public class IsEnumValidationStep<U extends Enum<U>> extends ValidationStep<String> {
    U[] enumArr;

    public IsEnumValidationStep(U[] enumArr) {
        this.enumArr = enumArr;
    }

    @Override
    public ValidationResult validate(String toValidate) {
        if (IsNullHelper.isNull(toValidate)) {
            return this.checkNext(toValidate);
        }

        boolean isFound = Arrays.stream(enumArr).anyMatch(en -> en.name().equals(toValidate));

        if (!isFound) {
            String allowed = Arrays.stream(enumArr).map(Enum::name).collect(Collectors.joining(", "));
            return ValidationResult.invalid("Поле должно быть одним из значений: " + allowed);
        }

        return this.checkNext(toValidate);
    }
}
