package scanner;

import printer.Printer;
import printer.PrinterStatus;

import java.util.Scanner;

public class ScannerWrapperWithLogging extends ScannerWrapper {
    Printer printer;

    public ScannerWrapperWithLogging(Scanner scanner, Printer printer) {
        super(scanner);
        this.printer = printer;
    }

    public String nextLine() {
        String line = super.nextLine();
        printer.println(line, PrinterStatus.MAGENTA);
        return line;
    }
}
