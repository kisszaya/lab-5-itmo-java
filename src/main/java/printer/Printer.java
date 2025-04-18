package printer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Printer {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String MAGENTA = "\u001B[35m";

    public void println(String str, PrinterStatus status) {
        if (status.equals(PrinterStatus.ERROR)) {
            System.out.printf("%s%s%s%n", RED, str, RESET);
            return;
        }
        if (status.equals(PrinterStatus.SUCCESS)) {
            System.out.printf("%s%s%s%n", GREEN, str, RESET);
            return;
        }
        if (status.equals(PrinterStatus.MAGENTA)) {
            System.out.printf("%s%s%s%n", MAGENTA, str, RESET);
            return;
        }
        System.out.printf("%s%n", str);
    }

    public void println(String str) {
        System.out.printf("%s%n", str);
    }

    public void print(String str) {
        System.out.printf("%s", str);
    }

    public void printlnWithTab(String str, PrinterStatus status) {
        String[] arr = str.split("\n");
        String formattedStr = Arrays.stream(arr).map(el -> "\t" + el).collect(Collectors.joining("\n"));
        println(formattedStr, status);
    }

    public void printlnWithTab(String str) {
        String[] arr = str.split("\n");
        String formattedStr = Arrays.stream(arr).map(el -> "\t" + el).collect(Collectors.joining("\n"));
        println(formattedStr);
    }

    public void printlnStartAction(String str) {
        System.out.printf("""
                **********
                %s
                """, str);
    }

    public void printlnEndAction(String str) {
        System.out.printf("""
                %s
                **********%n
                """, str);
    }
}
