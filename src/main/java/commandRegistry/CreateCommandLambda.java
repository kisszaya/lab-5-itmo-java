package commandRegistry;

import commands.Command;
import scanner.ScannerWrapper;

@FunctionalInterface
interface CreateCommandLambda {
    Command create(ScannerWrapper scanner);
}
