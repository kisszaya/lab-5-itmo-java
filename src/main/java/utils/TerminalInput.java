package utils;

import java.util.Arrays;

public class TerminalInput {
    String[] args;
    String executedCommand;

    public TerminalInput(String input) {
        String[] parts = input.split(" ");
        this.executedCommand = parts[0];
        this.args = Arrays.copyOfRange(parts, 1, parts.length);
    }

    public String getExecutedCommand() {
        return this.executedCommand;
    }

    public String[] getArgs() {
        return this.args;
    }
}
