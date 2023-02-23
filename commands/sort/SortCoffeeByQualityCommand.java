package commands.sort;

import commands.Command;
import main.Manager;

import java.util.logging.Logger;

public class SortCoffeeByQualityCommand extends Command {
    public SortCoffeeByQualityCommand(Manager manager) {
        super(manager);
    }

    @Override
    public void execute() {
        manager.sortCoffeeByQuality();
    }
}
