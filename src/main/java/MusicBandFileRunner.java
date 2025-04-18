import exceptions.MusicBandWithIdAlreadyExists;
import jsonArrayFileManager.JSONArrayFileManager;
import printer.Printer;
import printer.PrinterStatus;
import entities.MusicBand;
import exceptions.MusicBandJSONException;
import musicBandRepository.MusicBandRepository;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MusicBandFileRunner {
    Printer printer;
    JSONArrayFileManager JSONArrayFileManager;
    MusicBandRepository musicBandRepository;

    public MusicBandFileRunner(Printer printer, JSONArrayFileManager JSONArrayFileManager, MusicBandRepository musicBandRepository) {
        this.printer = printer;
        this.JSONArrayFileManager = JSONArrayFileManager;
        this.musicBandRepository = musicBandRepository;
    }

    public JSONArray read() {
        JSONArray jsonArr = null;
        try {
            jsonArr = JSONArrayFileManager.read();
        } catch (FileNotFoundException exc) {
            printer.printlnWithTab("Не найден файл", PrinterStatus.ERROR);
            System.exit(0);
        } catch (JSONException exc) {
            printer.printlnWithTab("Невалидный JSON файл", PrinterStatus.ERROR);
            System.exit(0);
        } catch (IOException exc) {
            printer.printlnWithTab("Не получилось прочитать файл", PrinterStatus.ERROR);
            System.exit(0);
        }
        return jsonArr;
    }

    public void createFromJson(JSONArray jsonArr) {
        if (jsonArr.isEmpty()) {
            printer.printlnWithTab("Нет элементов пока что в файле");
            return;
        }

        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                MusicBand musicBand = new MusicBand(jsonArr.getJSONObject(i));
                musicBandRepository.create(musicBand);

                printer.printlnWithTab(String.format("Успешно добавлен элемент массива №%s", i + 1), PrinterStatus.SUCCESS);
            } catch (MusicBandJSONException exc) {
                printer.printlnWithTab(String.format("Не добавила элемент массива №%s. %s", i + 1, exc.messageError), PrinterStatus.ERROR);
            } catch (MusicBandWithIdAlreadyExists exc) {
                printer.printlnWithTab(String.format("Не добавила элемент массива №%s. Элемент с таким id уже создан", i + 1), PrinterStatus.ERROR);
            }
        }
    }

}
