package musicBandRepository;

import entities.MusicBand;
import org.json.JSONArray;
import org.json.JSONException;
import printer.Printer;
import printer.PrinterStatus;
import utils.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Optional;

public class MusicBandRepository {
    private final List<MusicBand> bandList = new LinkedList<>();
    Printer printer;

    public MusicBandRepository (Printer printer, String fileUrl) {
        this.printer = printer;
        printer.printlnStartAction("Начинаем читать файл");
        JSONArray jsonArr = readFileUrl(fileUrl);
        createMusicBandsFromJSONArray(jsonArr);
        printer.printlnEndAction("Закончили читать файл");
    }

    private JSONArray readFileUrl(String fileUrl) {
        JSONArray jsonArr = null;
        try {
            jsonArr =  File.readJSONArray(fileUrl);
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

    private void createMusicBandsFromJSONArray (JSONArray jsonArr) {
        if (jsonArr.isEmpty()) {
            return;
        }

        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                MusicBand musicBand = new MusicBand(jsonArr.getJSONObject(i));
                this.create(musicBand);

                printer.printlnWithTab(String.format("Успешно добавлен элемент массива №%s", i + 1), PrinterStatus.SUCCESS);
            }
            catch (IllegalArgumentException | IOException exc) {
                printer.printlnWithTab(String.format("Не добавила элемент массива №%s. %s", i + 1, exc.getMessage()), PrinterStatus.ERROR);
            }
        }
    }

    public List<MusicBand> getAll() {
        return this.bandList;
    }

    public Optional<MusicBand> getById(long id) {
        return Optional.ofNullable(this.bandList.stream().filter(el -> el.getId() == id).findAny().orElse(null));
    }

    public void updateById(long id, MusicBand updatedFields) throws IllegalArgumentException {
        var musicBand = getById(id).orElseThrow(() -> 
                new IllegalArgumentException(String.format("Музыкальная группа с id №%s не существует", id))
        );
        musicBand.update(updatedFields);
    }

    public void create(MusicBand entity) throws IllegalArgumentException {
        if (this.getById(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Элемент с таким id уже создан");
        }
        bandList.add(entity);
    }
}
