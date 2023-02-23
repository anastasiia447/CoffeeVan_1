package helpers;

import model.enumerators.AggregateState;
import model.enumerators.CoffeeStatus;
import model.CultivationPlace;

import java.util.UUID;

public final class Extension {
    public static UUID parseId(String value) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            AppLogger.logger.warning("Incorrect id!");
            return null;
        }
    }

    public static float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            AppLogger.logger.warning("Incorrect data. Will be return '0'");
            return 0;
        }
    }

    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            AppLogger.logger.warning("Incorrect data. Will be return '0'");
            return 0;
        }
    }

    public static AggregateState parseAggregateState(String value) {
        try {
            return AggregateState.valueOf(value);
        } catch (IllegalArgumentException e) {
            AppLogger.logger.warning("Incorrect data. Will be return 'null'!");
            return null;
        }
    }

    public static CoffeeStatus parseCoffeeStatus(String value) {
        try {
            return CoffeeStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            AppLogger.logger.warning("Incorrect data. Will be return 'null'!");
            return null;
        }
    }

    public static CultivationPlace parseCultivationPlace(UUID id, String value) {
        if (id != null) {
            String[] data = value.split(" ");
            try {
                return new CultivationPlace(id, data[0], data[1], data[2]);
            } catch (IndexOutOfBoundsException e) {
                AppLogger.logger.warning("Incorrect data. Will be return CultivationPlace with 'none' data");
                return new CultivationPlace(id);
            }
        } else {
            return null;
        }
    }
}
