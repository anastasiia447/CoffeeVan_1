package helpers;

import main.Settings;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class AppLogger {
    public static Logger logger;

    public AppLogger() {
        logger = Logger.getLogger("ApplicationLogs");
        try {
            FileHandler fileHandler = new FileHandler(Settings.logFilePath);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
