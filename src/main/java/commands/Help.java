package commands;

import commandRegistry.CommandRegistry;
import printer.Printer;
import scanner.ScannerWrapper;

public class Help extends Command {
    CommandRegistry commandRegistry;

    public Help(ScannerWrapper scanner, Printer printer, CommandRegistry commandRegistry) {
        super(scanner, printer);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(String[] args) {
        printer.printlnWithTab(this.commandRegistry.toString());
    }
}
