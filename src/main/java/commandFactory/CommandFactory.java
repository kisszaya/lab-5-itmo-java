package commandFactory;

import commands.*;
import printer.Printer;
import musicBandRepository.MusicBandRepository;
import scanner.ScannerWrapper;

import java.util.*;

public class CommandFactory {
    private final ScannerWrapper DEFAULT_SCANNER = new ScannerWrapper(new Scanner(System.in));
    private final HashMap<String, CommandFactoryElement> commandMap = new HashMap<>();
    private ScannerWrapper scanner = DEFAULT_SCANNER;

    public CommandFactory(
            Printer printer,
            MusicBandRepository musicBandRepository,
            Invoker invoker,
            String fileUrl
    ) {
        add("help", null, "вывести справку по доступным командам", scanner -> new Help(scanner, printer, this));
        add("info", "{id}", "вывести в стандартный поток вывода информацию о коллекции", scanner -> new Info(scanner, printer, musicBandRepository));
        add("show", null, "вывести в стандартный поток вывода все элементы коллекции в строковом представлении", scanner -> new Show(scanner, printer, musicBandRepository));
        add("add", null, "добавить новый элемент в коллекцию", scanner -> new Add(scanner, printer, musicBandRepository));
        add("update", "{id}", "обновить значение элемента коллекции, id которого равен заданному", scanner -> new Update(scanner, printer, musicBandRepository));
        //      ПОТОМ remove_by_id id : удалить элемент из коллекции по его id
        //      ПОТОМ clear : очистить коллекцию
        add("save", null, "сохранить коллекцию в файл", scanner -> new Save(scanner, printer, musicBandRepository, fileUrl));
        add("execute_script", "{file_name}", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме", scanner -> new ExecuteScript(
                scanner,
                printer,
                invoker,
                this));
        add("exit", null, "завершить программу (без сохранения в файл)", scanner -> new Exit(scanner, printer));
        //      ПОТОМ remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)
        //      ПОТОМ shuffle : перемешать элементы коллекции в случайном порядке
        //      ПОТОМ reorder : отсортировать коллекцию в порядке, обратном нынешнему
        //      ПОТОМ remove_any_by_front_man frontMan : удалить из коллекции один элемент, значение поля frontMan которого эквивалентно заданному
        //      ПОТОМ count_by_number_of_participants numberOfParticipants : вывести количество элементов, значение поля numberOfParticipants которых равно заданному
        //      ПОТОМ filter_by_albums_count albumsCount : вывести элементы, значение поля albumsCount которых равно заданному

    }

    public void setScanner(ScannerWrapper scanner) {
        this.scanner = scanner;
    }

    public void setDefaultScanner () {
        this.scanner = DEFAULT_SCANNER;
    }

    public String toString() {
        List<String> list = new ArrayList<>();
        for (CommandFactoryElement elem : this.commandMap.values()) {
            list.add(elem.toString());
        }
        return String.join("\n", list);
    }

    public Command createByName(String name) {
        CommandFactoryElement elem = this.commandMap.get(name);
        if (elem != null) {
            return elem.createCommand.create(scanner);
        }
        return null;
    }

    private void add(String name, String args, String description, CreateCommandLambda createCommand) {
        commandMap.put(name, new CommandFactoryElement(name, description, args, createCommand));
    }
}