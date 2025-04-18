package entities;

import validation.*;

import java.util.ArrayList;
import java.util.List;

public class Coordinates {
    private double x; //Значение поля должно быть больше -645
    private Long y; //Поле не может быть null

    public Coordinates (double x, Long y ) {
        this.x = x;
        this.y = y;
    }

    public static ValidationResult validateX (String field) {
        return new NotNullValidationStep()
                .linkNext(new IsDoubleValidationStep())
                .linkNext(new MoreThanAmountValidationStep(-645))
                .validate(field);
    }

    public static ValidationResult validateY (String field) {
        return new NotNullValidationStep()
                .linkNext(new IsLongValidationStep())
                .validate(field);
    }

    public String toString() {
        List<String> paragraphs = new ArrayList<>();

        paragraphs.add("X: " + this.x);
        paragraphs.add("Y: " + this.y);

        return String.join("; ", paragraphs);
    }

}