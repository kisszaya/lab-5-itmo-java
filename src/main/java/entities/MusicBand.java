package entities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import printer.Printer;
import scanner.ScannerWrapper;
import utils.Transformer;
import validation.*;

public class MusicBand {
    private final long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int numberOfParticipants; //Значение поля должно быть больше 0
    private int albumsCount; //Значение поля должно быть больше 0
    private MusicGenre genre; //Поле не может быть null
    private Person frontMan; //Поле может быть null

    public MusicBand (ScannerWrapper scanner, Printer printer) {
        MusicBandFromScanner musicBandFromScanner = new MusicBandFromScanner(scanner, printer);
        this.name = musicBandFromScanner.requestField("название группы", MusicBand::validateName, Transformer::toString);
        this.coordinates = musicBandFromScanner.requestCoordinates();
        this.creationDate = LocalDateTime.now();
        this.numberOfParticipants = musicBandFromScanner.requestField("количество участников", MusicBand::validateNumberOfParticipants, Transformer::toInteger);
        this.albumsCount = musicBandFromScanner.requestField("количество альбомов", MusicBand::validateAlbumsCount, Transformer::toInteger);
        this.genre = musicBandFromScanner.requestField("жанр " + Arrays.toString(MusicGenre.values()), MusicBand::validateGenre, value -> Transformer.toEnum(MusicGenre.class, value));
        this.frontMan = musicBandFromScanner.requestFrontMan();
        this.id = generateId();
    }

    public MusicBand(String name, Coordinates coordinates, int numberOfParticipants, int albumsCount, MusicGenre genre, Person frontMan, LocalDateTime creationDate, long id) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.albumsCount = albumsCount;
        this.genre = genre;
        this.frontMan = frontMan;
        this.id = id;
    }

    public MusicBand(JSONObject obj) throws IOException {
        this.id = this.validateJsonField(obj, "id", MusicBand::validateId, Transformer::toLong);
        this.name = this.validateJsonField(obj, "name", MusicBand::validateName, Transformer::toString);
        this.creationDate = this.validateJsonField(obj, "creationDate", MusicBand::validateCreationDate, Transformer::toLocalDateTime);
        this.numberOfParticipants = this.validateJsonField(obj, "numberOfParticipants", MusicBand::validateNumberOfParticipants, Transformer::toInteger);
        this.albumsCount = this.validateJsonField(obj, "albumsCount", MusicBand::validateAlbumsCount, Transformer::toInteger);
        this.genre = this.validateJsonField(obj, "genre", MusicBand::validateGenre, value -> Transformer.toEnum(MusicGenre.class, value));
    }

    public void update(MusicBand updatedFields) {
        this.name = updatedFields.name;
        this.coordinates = updatedFields.coordinates;
        this.numberOfParticipants = updatedFields.numberOfParticipants;
        this.albumsCount = updatedFields.albumsCount;
        this.genre = updatedFields.genre;
        this.frontMan = updatedFields.frontMan;
    }

    public long getId() {
        return this.id;
    }

    public static ValidationResult validateCreationDate(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsLocalDateTimeValidationStep())
                .validate(field);
    }

    public static ValidationResult validateId(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsLongValidationStep())
                .validate(field);
    }

    public static ValidationResult validateName(String field) {
        return new NotNullValidationStep().validate(field);
    }

    public static ValidationResult validateNumberOfParticipants(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsIntegerValidationStep())
                .linkNext(new MoreThanAmountValidationStep(0))
                .validate(field);
    }

    public static ValidationResult validateAlbumsCount(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsIntegerValidationStep())
                .linkNext(new MoreThanAmountValidationStep(0))
                .validate(field);
    }

    public static ValidationResult validateGenre(String field) {
        return new NotNullValidationStep()
                .linkNext(new IsEnumValidationStep<>(MusicGenre.values()))
                .validate(field);
    }

    private static long generateId() {
        return Math.abs((new Random()).nextLong());
    }

    private <T> T validateJsonField(JSONObject obj, String fieldName, RequestFieldValidation validate, RequestFieldTransformation<T> transform) throws IOException {
        try {
            Object field = obj.get(fieldName);
            String fieldString = field.toString();
            ValidationResult validationResult = validate.validate(fieldString);
            if (!validationResult.getIsValid()) {
                throw new IOException(
                        String.format(
                                "Проблемы с полем %s: %s",
                                fieldName,
                                validationResult.getMessage()
                        )
                );
            }
            return transform.transform(fieldString);
        } catch (JSONException err) {
            throw new IOException("Не получается прочитать поле из JSON");
        }
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        obj.put("name", this.name);
        obj.put("id", this.id);
        obj.put("creationDate", this.creationDate.toString());
        obj.put("numberOfParticipants", this.numberOfParticipants);
        obj.put("albumsCount", this.albumsCount);
        obj.put("genre", this.genre);
        if (this.frontMan != null) {
            obj.put("frontMan", true);
        }
        return obj;
    }

    public String toString() {
        List<String> paragraphs = new ArrayList<>();

        paragraphs.add("id: " + this.id);
        paragraphs.add("Название: " + this.name);
        paragraphs.add("Координаты: " + this.coordinates);
        paragraphs.add("Дата основания: " + this.creationDate);
        paragraphs.add("Количество участников: " + this.numberOfParticipants);
        paragraphs.add("Количество альбомов: " + this.albumsCount);
        paragraphs.add("Жанр: " + this.genre);
        if (this.frontMan != null) {
            paragraphs.add("Солист группы: " + this.frontMan);
        }

        return String.join("\n", paragraphs);
    }
}