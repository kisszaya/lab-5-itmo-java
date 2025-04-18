package commands;

import commandRegistry.CommandRegistry;
import exceptions.ExitProgramException;
import fileManager.FileManager;
import fileManager.FileManagerFactory;
import printer.Printer;
import printer.PrinterStatus;
import scanner.ScannerWrapper;
import scanner.ScannerWrapperWithLogging;
import terminalInput.TerminalInput;
import terminalInput.TerminalInputFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private final Invoker invoker;
    private final FileManagerFactory fileManagerFactory;
    private final TerminalInputFactory terminalInputFactory;
    private final CommandRegistry commandRegistry;

    public ExecuteScript(
            ScannerWrapper scanner,
            Printer printer,
            Invoker invoker,
            FileManagerFactory fileManagerFactory,
            TerminalInputFactory terminalInputFactory,
            CommandRegistry commandRegistry
    ) {
        super(scanner, printer);
        this.invoker = invoker;
        this.fileManagerFactory = fileManagerFactory;
        this.terminalInputFactory = terminalInputFactory;
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(String[] args) throws ExitProgramException {
        String fileUrl = args.length > 0 ? args[0] : null;
        if (fileUrl == null || fileUrl.isEmpty()) {
            printer.println("Вы не передали путь до файла", PrinterStatus.ERROR);
            return;
        }

        FileManager fileManager = fileManagerFactory.createFileManager(fileUrl);
        String fileContent;
        try {
            fileContent = fileManager.read();
        } catch (FileNotFoundException exc) {
            printer.printlnWithTab("Не найден файл", PrinterStatus.ERROR);
            return;
        } catch (IOException exc) {
            printer.printlnWithTab("Невалидный JSON файл", PrinterStatus.ERROR);
            return;

        }
        ScannerWrapper fileScanner = new ScannerWrapperWithLogging(new Scanner(fileContent), printer);

        try {
            while (true) {
                printer.print("Введи команду: ");
                if (!fileScanner.hasNext()) {
                    break;
                }
                TerminalInput terminalInput = terminalInputFactory.createTerminalInput(fileScanner.nextLine());
                Command command = commandRegistry.createCommandByName(terminalInput.getExecutedCommand(), fileScanner);
                boolean isExit = invoker.executeCommand(command, terminalInput.getArgs());
                if (isExit) {
                    break;
                }
            }
        } catch (NoSuchElementException exc) {
            printer.println("Что-то не так со скриптом. Не получилось завершить команду так ка недостаточно данных в скрипте", PrinterStatus.ERROR);
        }

        fileScanner.close();
    }
}
