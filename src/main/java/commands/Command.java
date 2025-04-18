package commands;

import printer.Printer;
import exceptions.ExitProgramException;

import scanner.ScannerWrapper;

public abstract class Command {
    protected final ScannerWrapper scanner;
    protected final Printer printer;

    /**
     * Command class for implementation of Command pattern
     */
    Command(ScannerWrapper scanner, Printer printer) {
        this.scanner = scanner;
        this.printer = printer;
    }

    public abstract void execute(String[] args) throws ExitProgramException;
}
