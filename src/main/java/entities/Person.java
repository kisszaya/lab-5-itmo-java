package entities;

import validation.*;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Строка не может быть пустой, Длина строки должна быть не меньше 6, Поле не может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(
            String name,
            String passportID,
            Color hairColor,
            Country nationality,
            Location location
    ) {
        this.name = name;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public static ValidationResult validateName(String field) {
        return new NotNullValidationStep().validate(field);
    }

    public static ValidationResult validatePassportID(String field) {
        return new NotNullValidationStep()
                .linkNext(new MoreThanSymbolsValidationStep(5))
                .validate(field);
    }

    public static ValidationResult validateHairColor(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsEnumValidationStep<>(Color.values()))
                .validate(field);
    }

    public static ValidationResult validateNationality(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsEnumValidationStep<>(Country.values()))
                .validate(field);
    }

    public String toString() {
        List<String> paragraphs = new ArrayList<>();

        paragraphs.add("Имя: " + this.name);
        paragraphs.add("Password ID: " + this.passportID);
        paragraphs.add("Цвет волос: " + this.hairColor.name());
        if (this.nationality != null) {
            paragraphs.add("Национальность: " + this.nationality.name());
        }
        paragraphs.add("Локация: " + this.location.toString());

        return String.join("; ", paragraphs);
    }
}