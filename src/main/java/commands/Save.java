package commands;

import printer.Printer;
import printer.PrinterStatus;
import entities.MusicBand;
import musicBandRepository.MusicBandRepository;
import scanner.ScannerWrapper;
import utils.File;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Save extends BandCommand {
    String fileUrl;

    public Save(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository, String fileUrl) {
        super(scanner, printer, musicBandRepository);
        this.fileUrl = fileUrl;
    }

    @Override
    public void execute(String[] args) {
        List<MusicBand> musicBandList = this.musicBandRepository.getAll();

        try {
            File.saveJSONArray(
                    fileUrl,
                    musicBandList.stream().map(MusicBand::toJsonObject).collect(Collectors.toCollection(LinkedList::new))
            );
            printer.println("Успешно сохранили созданные группы", PrinterStatus.SUCCESS);
        } catch (IOException exc) {
            printer.println("Не получилось сохранить данные", PrinterStatus.ERROR);
        }
    }
}
