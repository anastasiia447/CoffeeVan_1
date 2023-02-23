package commands.van;

import commands.Command;
import main.Application;
import main.Manager;
import model.Van;

import java.util.logging.Logger;

import static helpers.Extension.parseFloat;

public class AddVanCommand extends Command {
    private float capacity;

    public AddVanCommand(String capacity, Manager manager) {
        super(manager);
        this.capacity = parseFloat(capacity);
    }

    @Override
    public void execute() {
        manager.addVan(new Van(capacity));
    }
}
