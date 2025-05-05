package validation;

public abstract class ValidationStep<T> {
    ValidationStep<T> next = null;

    public ValidationStep<T> linkNext(ValidationStep<T> newStep) {
        if (this.next == null) {
            this.next = newStep;
            return this;
        }

        ValidationStep<T> lastStep = this.next;
        while (lastStep.next != null) {
            lastStep = lastStep.next;
        }
        lastStep.next = newStep;
        return this;
    }

    abstract public ValidationResult validate(T toValidate);

    protected ValidationResult checkNext(T toValidate) {
        if (next == null) {
            return ValidationResult.valid();
        }

        return next.validate(toValidate);
    }

}
