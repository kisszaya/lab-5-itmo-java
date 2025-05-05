import commands.Command;
import printer.Printer;
import commandFactory.CommandFactory;
import commands.Invoker;
import musicBandRepository.MusicBandRepository;

import scanner.ScannerWrapper;
import utils.TerminalInput;

import java.util.Scanner;

public class Application {
    private final Printer printer = new Printer();
    private final Invoker invoker = new Invoker(printer);
    private final CommandFactory commandFactory;

    public Application(String[] args) {
        String fileUrl = args.length > 0 ? args[0] : "/Users/svetlanamatveeva/IdeaProjects/itmo-lab-5/meow.json";

        MusicBandRepository musicBandRepository = new MusicBandRepository(this.printer, fileUrl);

        this.commandFactory = new CommandFactory(
                printer,
                musicBandRepository,
                invoker,
                fileUrl
        );
    }

    public void run() {
        ScannerWrapper scanner = new ScannerWrapper(new Scanner(System.in));

        while (true) {
            printer.print("Введи команду: ");
            TerminalInput terminalInput = new TerminalInput(scanner.nextLine());
            Command command = commandFactory.createByName(terminalInput.getExecutedCommand());
            boolean isExit = invoker.executeCommand(command, terminalInput.getArgs());
            if (isExit) {
                break;
            }
        }

        scanner.close();
    }
}
