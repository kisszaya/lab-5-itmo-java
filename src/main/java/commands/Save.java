package commands;

import jsonArrayFileManager.JSONArrayFileManager;
import printer.Printer;
import printer.PrinterStatus;
import entities.MusicBand;
import musicBandRepository.MusicBandRepository;
import scanner.ScannerWrapper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Save extends BandCommand {
    JSONArrayFileManager jsonArrayFileManagerMusicBands;

    public Save(ScannerWrapper scanner, Printer printer, MusicBandRepository musicBandRepository, JSONArrayFileManager jsonArrayFileManagerMusicBands) {
        super(scanner, printer, musicBandRepository);
        this.jsonArrayFileManagerMusicBands = jsonArrayFileManagerMusicBands;
    }

    @Override
    public void execute(String[] args) {
        List<MusicBand> musicBandList = this.musicBandRepository.getAll();

        try {
            this.jsonArrayFileManagerMusicBands.saveObjectListInArray(
                    musicBandList.stream().map(MusicBand::toJsonObject).collect(Collectors.toCollection(LinkedList::new))
            );
            printer.println("Успешно сохранили созданные группы", PrinterStatus.SUCCESS);
        } catch (IOException exc) {
            printer.println("Не получилось сохранить данные", PrinterStatus.ERROR);
        }
    }
}
