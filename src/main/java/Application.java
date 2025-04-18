import commands.Command;
import fileManager.FileManagerFactory;
import jsonArrayFileManager.JSONArrayFileManager;
import jsonArrayFileManager.JSONArrayFileManagerFactory;
import musicBandCreationService.MusicBandCreationServiceFactory;
import printer.Printer;
import commandRegistry.CommandRegistry;
import commands.Invoker;
import musicBandRepository.MusicBandRepository;
import terminalInput.TerminalInput;
import terminalInput.TerminalInputFactory;

import scanner.ScannerWrapper;

import java.util.Scanner;

public class Application {
    private final Printer printer = new Printer();
    private final TerminalInputFactory terminalInputFactory = new TerminalInputFactory();
    private final Invoker invoker = new Invoker(printer);
    private final MusicBandFileRunner musicBandFileRunner;
    private final CommandRegistry commandRegistry;

    public Application(String[] args) {
        String fileUrl = args.length > 0 ? args[0] : "/Users/svetlanamatveeva/IdeaProjects/itmo-lab-5/meow.json";

        MusicBandRepository musicBandRepository = new MusicBandRepository();
        FileManagerFactory fileManagerFactory = new FileManagerFactory();
        JSONArrayFileManagerFactory jsonArrayFileManagerFactory = new JSONArrayFileManagerFactory(fileManagerFactory);
        JSONArrayFileManager jsonArrayFileManagerMusicBands = jsonArrayFileManagerFactory.createJSONArrayFileManager(fileUrl);
        MusicBandCreationServiceFactory musicBandCreationServiceFactory = new MusicBandCreationServiceFactory(printer);

        this.commandRegistry = new CommandRegistry(
                printer,
                musicBandRepository,
                jsonArrayFileManagerMusicBands,
                invoker,
                fileManagerFactory,
                terminalInputFactory,
                musicBandCreationServiceFactory
        );

        this.musicBandFileRunner = new MusicBandFileRunner(
                printer,
                jsonArrayFileManagerMusicBands,
                musicBandRepository
        );
    }

    public void run() {
        printer.printlnStartAction("Начинаем читать файл");
        musicBandFileRunner.createFromJson(musicBandFileRunner.read());
        printer.printlnEndAction("Закончили читать файл");

        ScannerWrapper scanner = new ScannerWrapper(new Scanner(System.in));

        while (true) {
            printer.print("Введи команду: ");
            TerminalInput terminalInput = terminalInputFactory.createTerminalInput(scanner.nextLine());
            Command command = commandRegistry.createCommandByName(terminalInput.getExecutedCommand(), scanner);
            boolean isExit = invoker.executeCommand(command, terminalInput.getArgs());
            if (isExit) {
                break;
            }
        }

        scanner.close();
    }
}
