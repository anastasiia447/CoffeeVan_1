package commands;

import main.Manager;
import java.util.logging.Logger;

public abstract class Command {
    protected final Manager manager;

    protected Command(Manager manager){
        this.manager = manager;
    }

    public abstract void execute();
}
