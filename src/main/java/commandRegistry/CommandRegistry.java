package commandRegistry;

import commands.*;
import fileManager.FileManagerFactory;
import jsonArrayFileManager.JSONArrayFileManager;
import musicBandCreationService.MusicBandCreationServiceFactory;
import printer.Printer;
import musicBandRepository.MusicBandRepository;
import scanner.ScannerWrapper;
import terminalInput.TerminalInputFactory;

import java.util.*;

public class CommandRegistry {
    private final HashMap<String, CommandRegistryElement> commandMap = new HashMap<>();

    public CommandRegistry(
            Printer printer,
            MusicBandRepository musicBandRepository,
            JSONArrayFileManager jsonArrayFileManagerMusicBands,
            Invoker invoker,
            FileManagerFactory fileManagerFactory,
            TerminalInputFactory terminalInputFactory,
            MusicBandCreationServiceFactory musicBandCreationServiceFactory
    ) {
        add("help", null, "вывести справку по доступным командам", scanner -> new Help(scanner, printer, this));
        add("info", "{id}", "вывести в стандартный поток вывода информацию о коллекции", scanner -> new Info(scanner, printer, musicBandRepository));
        add("show", null, "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", scanner -> new Show(scanner, printer, musicBandRepository));
        add("add", null, "добавить новый элемент в коллекцию", scanner -> new Add(scanner, printer, musicBandRepository, musicBandCreationServiceFactory));
        add("update", "{id}", "обновить значение элемента коллекции, id которого равен заданному", scanner -> new Update(scanner, printer, musicBandRepository, musicBandCreationServiceFactory));
        //      ПОТОМ remove_by_id id : удалить элемент из коллекции по его id
        //      ПОТОМ clear : очистить коллекцию
        add("save", null, "сохранить коллекцию в файл", scanner -> new Save(scanner, printer, musicBandRepository, jsonArrayFileManagerMusicBands));
        add("execute_script", "{file_name}", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме", scanner -> new ExecuteScript(
                scanner,
                printer,
                invoker,
                fileManagerFactory,
                terminalInputFactory,
                this));
        add("exit", null, "завершить программу (без сохранения в файл)", scanner -> new Exit(scanner, printer));
        //      ПОТОМ remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)
        //      ПОТОМ shuffle : перемешать элементы коллекции в случайном порядке
        //      ПОТОМ reorder : отсортировать коллекцию в порядке, обратном нынешнему
        //      ПОТОМ remove_any_by_front_man frontMan : удалить из коллекции один элемент, значение поля frontMan которого эквивалентно заданному
        //      ПОТОМ count_by_number_of_participants numberOfParticipants : вывести количество элементов, значение поля numberOfParticipants которых равно заданному
        //      ПОТОМ filter_by_albums_count albumsCount : вывести элементы, значение поля albumsCount которых равно заданному

    }

    public String toString() {
        List<String> list = new ArrayList<>();
        for (CommandRegistryElement elem : this.commandMap.values()) {
            list.add(elem.toString());
        }
        return String.join("\n", list);
    }


    public Command createCommandByName(String name, ScannerWrapper scanner) {
        CommandRegistryElement elem = this.commandMap.get(name);
        if (elem != null) {
            return elem.createCommand.create(scanner);
        }
        return null;
    }

    private void add(String name, String args, String description, CreateCommandLambda createCommand) {
        commandMap.put(name, new CommandRegistryElement(name, description, args, createCommand));
    }
}