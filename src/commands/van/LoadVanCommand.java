package commands.van;

import commands.Command;
import main.Manager;

import java.util.UUID;

import static helpers.Extension.parseId;

public class LoadVanCommand extends Command {
    private final UUID id;

    public LoadVanCommand(String id, Manager manager) {
        super(manager);
        this.id = parseId(id);
    }

    @Override
    public void execute() {
        manager.loadVan(id);
    }
}
