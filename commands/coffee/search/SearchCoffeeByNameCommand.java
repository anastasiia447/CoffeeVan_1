package commands.coffee.search;

import commands.Command;
import main.Manager;

public class SearchCoffeeByNameCommand extends Command {
    private final String name;

    public SearchCoffeeByNameCommand(String name, Manager manager) {
        super(manager);
        this.name = name;
    }

    @Override
    public void execute() {
        manager.findCoffee(name);
    }
}
