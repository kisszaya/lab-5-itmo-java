package commands;

import printer.Printer;
import musicBandRepository.MusicBandRepository;
import scanner.ScannerWrapper;

public abstract class BandCommand extends Command {
    protected MusicBandRepository musicBandRepository;

    BandCommand(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository) {
        super(scanner, printer);
        this.musicBandRepository = musicBandRepository;
    }

    @Override
    public abstract void execute(String[] args);
}
