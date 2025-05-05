package commands;

import entities.MusicBand;
import musicBandRepository.MusicBandRepository;
import printer.Printer;
import printer.PrinterStatus;
import scanner.ScannerWrapper;
import utils.Transformer;
import validation.IsLongValidationStep;
import validation.ValidationResult;

public class Update extends BandCommand {

    public Update(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository) {
        super(scanner, printer, musicBandRepository);
    }

    @Override
    public void execute(String[] args) {
        String stringId = args[0];
        if (stringId == null || stringId.isEmpty()) {
            printer.println("Не передали id", PrinterStatus.ERROR);
            return;
        }

        ValidationResult validationResult = new IsLongValidationStep().validate(stringId);
        if (!validationResult.getIsValid()) {
            printer.println("Id имеет некорректный тип", PrinterStatus.ERROR);
            return;
        }
        long id = Transformer.toLong(stringId);

        MusicBand updatedMusicBand = new MusicBand(scanner, printer);
        try {
            this.musicBandRepository.updateById(id, updatedMusicBand);
        } catch (IllegalArgumentException exc) {
            printer.println("Не нашли MusicBand с id " + stringId, PrinterStatus.ERROR);
            return;
        }

        printer.println("MusicBand успешно обновлен!", PrinterStatus.SUCCESS);
    }
}
