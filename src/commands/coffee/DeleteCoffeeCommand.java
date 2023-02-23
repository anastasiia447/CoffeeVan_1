package commands.coffee;

import commands.Command;
import main.Manager;

import java.util.UUID;

import static helpers.Extension.parseId;

public class DeleteCoffeeCommand extends Command {
    private final UUID id;

    public DeleteCoffeeCommand(String id, Manager manager) {
        super(manager);
        this.id = parseId(id);
    }

    @Override
    public void execute() {
        manager.deleteCoffee(id);
    }
}
