package entities;

import validation.ValidationResult;

@FunctionalInterface
interface RequestFieldValidation {
    ValidationResult validate(String field);
}
