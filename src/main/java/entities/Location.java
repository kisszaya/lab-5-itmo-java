package entities;

import validation.*;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private int x;
    private Integer y; //Поле не может быть null
    private long z;
    private String name; //Поле может быть null

    public Location(
            int x,
            Integer y,
            long z,
            String name
    ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public String toString() {
        List<String> paragraphs = new ArrayList<>();

        paragraphs.add("X: " + this.x);
        paragraphs.add("Y: " + this.y);
        paragraphs.add("Z: " + this.z);
        if (this.name != null) {
            paragraphs.add("Название локации: " + this.name);
        }

        return String.join("; ", paragraphs);
    }

    public static ValidationResult validateX(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsIntegerValidationStep())
                .validate(field);
    }

    public static ValidationResult validateY(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsIntegerValidationStep())
                .validate(field);
    }

    public static ValidationResult validateZ(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsLongValidationStep())
                .validate(field);
    }

    public static ValidationResult validateName(String field) {
        return new NotNullValidationStep().validate(field);
    }
}