package main;

import java.net.URI;

public class Settings {
    /* Van loading <-> max value equals 1 */
    public static float ordinaryQualityCoeff = 0.5F;
    public static float higherQualityCoeff = 0.3F;
    public static float specialtyQualityCoeff = 0.2F;

    /* Logging */
    public static String logFilePath = "lib/files/logs.txt";

    /* DataBase */
    public static final String DB_URL = "jdbc:mysql://localhost:3306/?user=root";
    public static final String USER = "root";
    public static final String PASS = "Nastya9303";
}
