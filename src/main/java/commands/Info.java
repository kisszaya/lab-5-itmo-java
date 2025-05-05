package commands;

import entities.MusicBand;
import printer.Printer;
import musicBandRepository.MusicBandRepository;
import printer.PrinterStatus;
import scanner.ScannerWrapper;
import utils.Transformer;
import validation.ValidationResult;

public class Info extends BandCommand {

    public Info(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository) {
        super(
                scanner,
                printer,
                musicBandRepository
        );
    }

    @Override
    public void execute(String[] args) {
        String rawId = args.length > 0 ? args[0] : null;
        ValidationResult validationResult = MusicBand.validateId(rawId);
        if (!validationResult.getIsValid()) {
            printer.println(validationResult.getMessage(), PrinterStatus.ERROR);
            return;
        }
        long id = Transformer.toLong(rawId);
        var musicBand = this.musicBandRepository.getById(id);
        if (musicBand.isEmpty()) {
            printer.println("MusicBand с id не существует", PrinterStatus.ERROR);
            return;
        }
        printer.println("Информация о элементе с id " + rawId);
        printer.printlnWithTab(musicBand.toString());
    }
}
