package commands;

import printer.Printer;
import exceptions.ExitCommandException;
import printer.PrinterStatus;
import scanner.ScannerWrapper;

public class Exit extends Command {
    public Exit(ScannerWrapper scanner, Printer printer) {
        super(scanner, printer);
    }

    @Override
    public void execute(String[] args) throws ExitCommandException {
        printer.println("НАДЕЮСЬ Я СДАЛА ТЕПЕРЬ НАДА САСАЦ БУБЛИКУ ПРЕПОДУ", PrinterStatus.MAGENTA);
        throw new ExitCommandException();
    }
}
