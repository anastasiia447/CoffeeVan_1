package commands.sort;

import commands.Command;
import main.Manager;

import java.util.logging.Logger;

public class SortCoffeeByCorrelationCommand extends Command {
    public SortCoffeeByCorrelationCommand(Manager manager) {
        super(manager);
    }

    @Override
    public void execute() {
        manager.sortCoffeeByCorrelation();
    }
}
