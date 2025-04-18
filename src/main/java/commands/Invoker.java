package commands;

import printer.Printer;
import exceptions.ExitProgramException;
import printer.PrinterStatus;

public class Invoker {
    Printer printer;

    public Invoker(Printer printer) {
        this.printer = printer;
    }

    public boolean executeCommand(Command command, String[] args) {
        if (command == null) {
            printer.println("Такой команды не существует. Если хотите получить список команд выведите help", PrinterStatus.ERROR);
            return false;
        }
        try {
            printer.printlnStartAction("Начало выполнения команды");
            command.execute(args);
            printer.printlnEndAction("Окончание выполнения команды");
        } catch (ExitProgramException exception) {
            return true;
        }
        return false;
    }
}
