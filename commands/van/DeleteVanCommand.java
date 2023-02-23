package commands.van;

import commands.Command;
import main.Manager;

import java.util.UUID;

import static helpers.Extension.parseId;

public class DeleteVanCommand extends Command {
    private UUID id;

    public DeleteVanCommand(String id, Manager manager) {
        super(manager);
        this.id = parseId(id);
    }

    @Override
    public void execute() {
        manager.deleteVan(id);
    }
}