package terminalInput;

public class TerminalInputFactory {

    public TerminalInput createTerminalInput(String input) {
        return new TerminalInput(input);
    }
}
