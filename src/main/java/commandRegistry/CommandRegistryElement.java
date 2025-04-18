package commandRegistry;

public class CommandRegistryElement {
    public String name;
    public String description;
    public String args;
    public CreateCommandLambda createCommand;

    public CommandRegistryElement(
            String name,
            String description,
            String args,
            CreateCommandLambda createCommand
    ) {
        this.name = name;
        this.description = description;
        this.args = args;
        this.createCommand = createCommand;
    }

    public String toString() {
        if (this.args != null) {
            return String.format("%s %s — %s", this.name, this.args, this.description);
        }
        return String.format("%s — %s", this.name, this.description);
    }

}
