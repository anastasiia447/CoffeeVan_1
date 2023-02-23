package commands.van;

import commands.Command;
import main.Manager;
import model.Van;

import java.util.UUID;

import static helpers.Extension.parseFloat;
import static helpers.Extension.parseId;

public class ChangeVanCommand extends Command {
    private final UUID id;
    private final float capacity;

    public ChangeVanCommand(String id, String capacity, Manager manager) {
        super(manager);
        this.id = parseId(id);
        this.capacity = parseFloat(capacity);
    }

    @Override
    public void execute() {
        manager.changeVan(id, new Van(capacity));
    }
}
