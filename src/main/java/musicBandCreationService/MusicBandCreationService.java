package musicBandCreationService;

import entities.*;
import printer.Printer;
import printer.PrinterStatus;
import scanner.ScannerWrapper;
import transformer.Transformer;
import validation.*;

import java.util.Arrays;

public class MusicBandCreationService {
    private final ScannerWrapper scanner;
    private final Printer printer;

    public MusicBandCreationService(ScannerWrapper scanner, Printer printer) {
        this.scanner = scanner;
        this.printer = printer;
    }

    public MusicBand requestFromScanner() {
        String name = requestField("название группы", MusicBand::validateName, Transformer::toString);
        Coordinates coordinates = requestCoordinates();
        Integer numberOfParticipants = requestField("количество участников", MusicBand::validateNumberOfParticipants, Transformer::toInteger);
        Integer albumsCount = requestField("количество альбомов", MusicBand::validateAlbumsCount, Transformer::toInteger);
        MusicGenre genre = requestField("жанр " + Arrays.toString(MusicGenre.values()), MusicBand::validateGenre, value -> Transformer.toEnum(MusicGenre.class, value));
        Person frontMan = requestFrontMan();

        return new MusicBand(name, coordinates, numberOfParticipants, albumsCount, genre, frontMan);
    }

    private Person requestFrontMan() {
        YesNoEnum isRequest = requestField("хотите ли добавить frontMan " + Arrays.toString(YesNoEnum.values()),
                value -> new NotNullValidationStep().linkNext(new IsEnumValidationStep<>(YesNoEnum.values())).validate(value),
                value -> Transformer.toEnum(YesNoEnum.class, value));
        if (isRequest.equals(YesNoEnum.NO)) {
            return null;
        }
        String name = requestField("имя frontName", Person::validateName, Transformer::toString);
        String passportId = requestField("паспорт ID у frontName", Person::validatePassportID, Transformer::toString);
        Color hairColor = requestField(
                "цвет волос у frontName " + Arrays.toString(Color.values()),
                Person::validateHairColor,
                value -> Transformer.toEnum(Color.class, value)
        );
        Country nationality = requestField(
                "национальность у frontName " + Arrays.toString(Country.values()),
                Person::validateNationality,
                value -> Transformer.toEnum(Country.class, value)
        );
        Location location = this.requestFrontManLocation();

        return new Person(name, passportId, hairColor, nationality, location);
    }

    private Location requestFrontManLocation() {
        Integer x = requestField("локацию frontName, координата x", Location::validateX, Transformer::toInteger);
        Integer y = requestField("локацию frontName, координата y", Location::validateY, Transformer::toInteger);
        Long z = requestField("локацию frontName, координата z", Location::validateZ, Transformer::toLong);
        String name = requestField("локацию frontName, название", Location::validateName, Transformer::toString);
        return new Location(x, y, z, name);
    }

    private Coordinates requestCoordinates() {
        double x = requestField("координату x", Coordinates::validateX, Transformer::toDouble);
        Long y = requestField("координату y", Coordinates::validateY, Transformer::toLong);
        return new Coordinates(x, y);
    }

    @FunctionalInterface
    interface RequestFieldTransformation<T> {
        T transform(String value);
    }

    @FunctionalInterface
    interface RequestFieldValidation {
        ValidationResult validate(String field);
    }


    private <T> T requestField(String messageName, RequestFieldValidation validate, RequestFieldTransformation<T> transform) {
        String field;
        printer.print(String.format("Введите %s: ", messageName));
        while (true) {
            field = this.scanner.nextLine();
            ValidationResult result = validate.validate(field);

            if (result.getIsValid()) {
                break;
            } else {
                printer.println(result.getMessage(), PrinterStatus.ERROR);
                printer.print(String.format("Введите еще раз %s: ", messageName));
            }
        }

        return transform.transform(field);
    }
}
