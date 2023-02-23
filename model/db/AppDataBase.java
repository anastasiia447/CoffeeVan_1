package model.db;

import model.Coffee;
import model.CoffeeGoods;
import model.CultivationPlace;
import model.Van;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import static helpers.AppLogger.logger;
import static helpers.Extension.*;
import static main.Settings.*;

public class AppDataBase {
    public AppDataBase() {
        logger.info("Connecting to server...");
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);) {
            logger.info("Connected! Starting create database!");
            createDataBase(connection);
        } catch (SQLException e) {
            logger.warning("Cannot access to server." + e.getMessage());
        }
    }

    /* DATABASE */
    private void createDataBase(Connection connection) {
        ScriptRunner runner = new ScriptRunner(connection);
        try {
            Reader reader = new BufferedReader(new FileReader(CREATE_DB_FILEPATH));
            runner.runScript(reader);
            reader.close();
            logger.info("DB was created!");
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
