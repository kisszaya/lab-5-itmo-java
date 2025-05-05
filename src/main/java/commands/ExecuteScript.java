package commands;

import commandFactory.CommandFactory;
import exceptions.ExitCommandException;
import printer.Printer;
import printer.PrinterStatus;
import scanner.ScannerWrapper;
import scanner.ScannerWrapperWithLogging;
import utils.File;
import utils.TerminalInput;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private final Invoker invoker;
    private final CommandFactory commandFactory;

    public ExecuteScript(
            ScannerWrapper scanner,
            Printer printer,
            Invoker invoker,
            CommandFactory commandFactory
    ) {
        super(scanner, printer);
        this.invoker = invoker;
        this.commandFactory = commandFactory;
    }

    @Override
    public void execute(String[] args) throws ExitCommandException {
        String fileUrl = args.length > 0 ? args[0] : null;
        if (fileUrl == null || fileUrl.isEmpty()) {
            printer.println("Вы не передали путь до файла", PrinterStatus.ERROR);
            return;
        }

        String fileContent;
        try {
            fileContent = File.read(fileUrl);
        } catch (FileNotFoundException exc) {
            printer.printlnWithTab("Не найден файл", PrinterStatus.ERROR);
            return;
        } catch (IOException exc) {
            printer.printlnWithTab("Невалидный JSON файл", PrinterStatus.ERROR);
            return;

        }
        ScannerWrapper fileScanner = new ScannerWrapperWithLogging(new Scanner(fileContent), printer);
        commandFactory.setScanner(fileScanner);
        try {
            while (true) {
                printer.print("Введи команду: ");
                if (!fileScanner.hasNext()) {
                    break;
                }
                TerminalInput terminalInput = new TerminalInput(fileScanner.nextLine());
                Command command = commandFactory.createByName(terminalInput.getExecutedCommand());
                boolean isExit = invoker.executeCommand(command, terminalInput.getArgs());
                if (isExit) {
                    break;
                }
            }
        } catch (NoSuchElementException exc) {
            printer.println("Что-то не так со скриптом. Не получилось завершить команду так ка недостаточно данных в скрипте", PrinterStatus.ERROR);
        }

        commandFactory.setDefaultScanner();

        fileScanner.close();
    }
}
