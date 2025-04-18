package commands;

import exceptions.MusicBandWithIdAlreadyExists;
import musicBandCreationService.MusicBandCreationService;
import musicBandCreationService.MusicBandCreationServiceFactory;
import printer.Printer;
import printer.PrinterStatus;
import entities.MusicBand;
import musicBandRepository.MusicBandRepository;
import scanner.ScannerWrapper;

public class Add extends BandCommand {
    private final MusicBandCreationService musicBandCreationService;

    public Add(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository, MusicBandCreationServiceFactory musicBandCreationServiceFactory) {
        super(scanner, printer, musicBandRepository);
        this.musicBandCreationService = musicBandCreationServiceFactory.createMusicBandCreationService(scanner);
    }

    @Override
    public void execute(String[] args) {
        MusicBand newMusicBand = musicBandCreationService.requestFromScanner();
        try {
            this.musicBandRepository.create(newMusicBand);
            printer.println("Группа успешно создана!", PrinterStatus.SUCCESS);
        } catch (MusicBandWithIdAlreadyExists exc) {
            printer.printlnWithTab("Элемент с таким id уже создан", PrinterStatus.ERROR);
        }

    }

}
