package commands;

import printer.Printer;
import entities.MusicBand;
import musicBandRepository.MusicBandRepository;
import scanner.ScannerWrapper;

import java.util.List;

public class Show extends BandCommand {
    public Show(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository) {
        super(
                scanner,
                printer,
                musicBandRepository
        );
    }

    @Override
    public void execute(String[] args) {
        List<MusicBand> list = this.musicBandRepository.getAll();
        if (list.isEmpty()) {
            printer.printlnEndAction("Пока что не создано групп");
        }
        for (int i = 0; i < list.size(); i++) {
            MusicBand band = list.get(i);
            printer.println(String.format("Элемент №%s: ", i));
            printer.printlnWithTab(band.toString());
        }
    }
}
