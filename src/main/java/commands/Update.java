package commands;

import entities.MusicBand;
import exceptions.MusicBandNotFound;
import musicBandCreationService.MusicBandCreationService;
import musicBandCreationService.MusicBandCreationServiceFactory;
import musicBandRepository.MusicBandRepository;
import printer.Printer;
import printer.PrinterStatus;
import scanner.ScannerWrapper;
import transformer.Transformer;
import validation.IsLongValidationStep;
import validation.ValidationResult;


public class Update extends BandCommand {
    private final MusicBandCreationService musicBandCreationService;

    public Update(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository, MusicBandCreationServiceFactory musicBandCreationServiceFactory) {
        super(scanner, printer, musicBandRepository);
        this.musicBandCreationService = musicBandCreationServiceFactory.createMusicBandCreationService(scanner);
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

        MusicBand updatedFields = musicBandCreationService.requestFromScanner();
        try {
            this.musicBandRepository.updateById(id, updatedFields);
        } catch (MusicBandNotFound exc) {
            printer.println("Не нашли MusicBand с id " + stringId, PrinterStatus.ERROR);
            return;
        }

        printer.println("MusicBand успешно обновлен!", PrinterStatus.SUCCESS);
    }
}
