package commands.coffee.search;

import commands.Command;
import main.Manager;

import static helpers.Extension.parseInt;

public class SearchCoffeeByQualityRangeCommand extends Command {
    private final int minQuality;
    private final int maxQuality;

    public SearchCoffeeByQualityRangeCommand(String minQuality, String maxQuality, Manager manager) {
        super(manager);
        this.minQuality = parseInt(minQuality);
        this.maxQuality = parseInt(maxQuality);
    }

    @Override
    public void execute() {
        manager.findCoffee(minQuality, maxQuality);
    }
}
