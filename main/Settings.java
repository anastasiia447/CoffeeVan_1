package main;

import java.net.URI;

public class Settings {
    /* Van loading <-> max value equals 1 */
    public static float ordinaryQualityCoeff = 0.5F;
    public static float higherQualityCoeff = 0.3F;
    public static float specialtyQualityCoeff = 0.2F;

    /* Logging */
    public static String logFilePath = "C:/Users/Khomenko/Desktop/Work/files/logs.txt";

    /* DataBase */
    public static final String DB_URL = "jdbc:mysql://localhost/";
    public static final String USER = "user";
    public static final String PASS = "Lm1z~Fv~";

    /* DataBase script */
    public static final String CREATE_DB_FILEPATH = "lib/create_db_script.sql";
}
