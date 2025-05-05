package commands;

import commandFactory.CommandFactory;
import printer.Printer;
import scanner.ScannerWrapper;

public class Help extends Command {
    CommandFactory commandFactory;

    public Help(ScannerWrapper scanner, Printer printer, CommandFactory commandFactory) {
        super(scanner, printer);
        this.commandFactory = commandFactory;
    }

    @Override
    public void execute(String[] args) {
        printer.printlnWithTab(this.commandFactory.toString());
    }
}
