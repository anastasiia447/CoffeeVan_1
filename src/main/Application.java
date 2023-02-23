package main;

import commands.Command;
import view.AppGUI;
import helpers.AppLogger;
import model.db.AppDataBase;

import javax.swing.*;
import java.awt.*;

public class Application {
    private final Manager manager;
    
    public Application() {
        new AppLogger();
        new AppDataBase();
        manager = new Manager();
    }

    public void initUISecond() {
        JFrame frame = new JFrame("CoffeeVan");
        frame.setContentPane(new AppGUI(this).getContentPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(700, 300));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public Manager getManager() {
        return manager;
    }

    public void executeCommand(Command command) {
        command.execute();
    }
}
