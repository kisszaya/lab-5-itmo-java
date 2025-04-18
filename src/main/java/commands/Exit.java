package commands;

import printer.Printer;
import exceptions.ExitProgramException;
import printer.PrinterStatus;
import scanner.ScannerWrapper;

public class Exit extends Command {
    public Exit(ScannerWrapper scanner, Printer printer) {
        super(scanner, printer);
    }

    @Override
    public void execute(String[] args) throws ExitProgramException {
        printer.println("НАДЕЮСЬ Я СДАЛА ТЕПЕРЬ НАДА САСАЦ БУБЛИКУ ПРЕПОДУ", PrinterStatus.MAGENTA);
        throw new ExitProgramException();
    }
}
