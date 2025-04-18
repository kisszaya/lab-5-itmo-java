package musicBandCreationService;

import printer.Printer;
import scanner.ScannerWrapper;

public class MusicBandCreationServiceFactory {
    private final Printer printer;

    public MusicBandCreationServiceFactory(Printer printer) {
        this.printer = printer;
    }

    public MusicBandCreationService createMusicBandCreationService(ScannerWrapper scanner) {
        return new MusicBandCreationService(scanner, printer);
    }
}
