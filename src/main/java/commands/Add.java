package commands;

import printer.Printer;
import printer.PrinterStatus;
import entities.MusicBand;
import musicBandRepository.MusicBandRepository;
import scanner.ScannerWrapper;

public class Add extends BandCommand {

    public Add(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository) {
        super(scanner, printer, musicBandRepository);
    }

    @Override
    public void execute(String[] args) {
        MusicBand newMusicBand = new MusicBand(this.scanner, this.printer);
        try {
            this.musicBandRepository.create(newMusicBand);
            printer.println("Группа успешно создана!", PrinterStatus.SUCCESS);
        } catch (IllegalThreadStateException exc) {
            printer.printlnWithTab(exc.getMessage(), PrinterStatus.ERROR);
        }
    }

}
