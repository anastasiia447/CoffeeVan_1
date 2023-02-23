package model.db;

import model.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static helpers.AppLogger.logger;
import static helpers.Extension.*;
import static helpers.Extension.parseId;
import static main.Settings.*;
import static main.Settings.PASS;

public class Repository {
    public static final String selectAllCoffeeSQL = "SELECT * FROM coffee_van_db.coffee";
    public static final String selectAllVanSQL = "SELECT * FROM coffee_van_db.van";
    /* INSERT METHODS */

    public static void insertVan(@NotNull Van van) {
        String sql = "INSERT INTO coffee_van_db.van (id, capacity) VALUES  " + van.toSQLvalues();
        if (execute(sql)) {
            logger.warning("Van {" + van.getId() + "} wasn't added to db!");
        }
    }

    public static void insertCoffee(@NotNull Coffee coffee) {
        String sql = "INSERT INTO coffee_van_db.coffee " +
                "(id, cultivation_place_id, name, aggregate_state, volume, netto_weight, " +
                "price, quality_mark, coffee_status, transportation_company, capping_mark) VALUES " + coffee.toSQLvalues();
        if (execute(sql)) {
            logger.warning("Coffee {" + coffee.getId() + "} wasn't added to db!");
        }
    }

    public static void insertCoffeeGoods(@NotNull CoffeeGoods goods) {
        String sql = "INSERT INTO coffee_van_db.coffee_goods (id, id_van, id_coffee, goods_number) VALUES " + goods.toSQLvalue();
        if (execute(sql)) {
            logger.warning("Goods {" + goods.getId() + "} wasn't added to db!");
        }
    }

    public static void insertCultivationPlace(@NotNull CultivationPlace place) {
        String sql = "INSERT INTO coffee_van_db.cultivation_place (id, id_coffee, country, state, farm) VALUES " + place.toSQLvalue();
        if (execute(sql)) {
            logger.warning("Cultivation place {" + place.getId() + "} wasn't added to db!");
        }
    }

    /* SELECT METHODS*/

    public static @Nullable ArrayList<Van> selectVans(String sql) {
        System.out.println("WE ARE IN SELECT VANS!");
        ResultSet result = executeQuery(sql);

        if (result != null) {
            try {
                ArrayList<Van> vans = new ArrayList<>();

                while (result.next()) {
                    vans.add(new Van(
                            parseId(result.getString("id")),
                            result.getFloat("capacity")
                    ));
                }
                result.close();
                return vans;
            } catch (SQLException e) {
                logger.warning(e.getMessage());
                return null;
            }
        }
        return null;
    }

    public static @Nullable ArrayList<Coffee> selectCoffees(String sql) {
        ResultSet result = executeQuery(sql);

        if (result != null) {
            try {
                ArrayList<Coffee> coffees = new ArrayList<>();

                while (result.next()) {
                    CultivationPlace place = selectCultivationPlace(parseId(result.getString("id")));
                    var temp = new Coffee(
                            parseId(result.getString("id")),
                            result.getString("name"),
                            parseAggregateState(result.getString("aggregate_state")),
                            result.getFloat("volume"),
                            result.getFloat("netto_weight"),
                            result.getFloat("price"),
                            result.getInt("quality_mark"),
                            parseCoffeeStatus(result.getString("coffee_status")),
                            place,
                            result.getString("transportation_company"),
                            result.getInt("capping_mark")
                    );
                    coffees.add(temp);
                }
                result.close();
                return coffees;
            } catch (SQLException e) {
                logger.warning(e.getMessage());
                return null;
            }
        }
        return null;
    }

    public static @NotNull CultivationPlace selectCultivationPlace(UUID coffeeId) {
        String sql = "SELECT * FROM coffee_van_db.cultivation_place WHERE id_coffee = '" + coffeeId.toString() + "';";
        ResultSet result = executeQuery(sql);
        if (result != null) {
            try {
                if (result.next()) {
                    return new CultivationPlace(
                            parseId(result.getString("id")),
                            parseId(result.getString("id_coffee")),
                            result.getString("country"),
                            result.getString("state"),
                            result.getString("farm")
                    );
                }
                result.close();
            } catch (SQLException e) {
                logger.warning(e.getMessage());
                return new CultivationPlace(coffeeId);
            }
        }

        return new CultivationPlace(coffeeId);
    }

    public static @Nullable ArrayList<CoffeeGoodsToListView> selectCoffeeGoods(UUID vanId) {
        String sql = "SELECT coffee_van_db.coffee.name, coffee_van_db.coffee_goods.goods_number " +
                "FROM coffee_van_db.coffee_goods " +
                "INNER JOIN coffee_van_db.coffee " +
                "ON coffee_van_db.coffee.id = coffee_van_db.coffee_goods.id_coffee  " +
                "WHERE (coffee_van_db.coffee_goods.id_van = '" + vanId.toString() + "');";
        try {
            ResultSet result = executeQuery(sql);
            ArrayList<CoffeeGoodsToListView> goods = new ArrayList<>();
            if (result != null) {
                while (result.next()) {
                    goods.add(new CoffeeGoodsToListView(
                            result.getString("name"),
                            result.getString("goods_number")
                    ));
                }
                result.close();
            }
            return goods;
        } catch (
                SQLException e) {
            logger.warning(e.getMessage());
        }
        return null;
    }


    /* UPDATE METHODS */

    public static void updateVan(UUID id, @NotNull Van van) {
        String sql = "UPDATE coffee_van_db.van " +
                "SET capacity = " + van.getCapacity() + " " +
                "WHERE id = '" + id.toString() + "';";
        if (execute(sql)) {
            logger.warning("Van {" + id + "} wasn't change!");
        }
    }

    public static boolean updateCoffee(UUID id, @NotNull Coffee coffee) {
        String sql = "UPDATE coffee_van_db.coffee " +
                "SET name = '" + coffee.getName() + "', " +
                "aggregate_state = '" + coffee.getAggregateState() + "', " +
                "volume = " + coffee.getVolume() + ", " +
                "netto_weight = " + coffee.getNettoWeight() + ", " +
                "price = " + coffee.getPrice() + ", " +
                "quality_mark = '" + coffee.getQualityMark() + "', " +
                "coffee_status = '" + coffee.getCoffeeStatus() + "', " +
                "transportation_company = '" + coffee.getTransportationCompany() + "', " +
                "capping_mark = " + coffee.getCappingMark() + " " +
                "WHERE id = '" + id.toString() + "';";
        if (execute(sql)) {
            logger.warning("Coffee {" + id + "} wasn't change!");
            return false;
        }
        return true;
    }

    /* DELETE METHODS */

    public static void deleteVan(UUID id) {
        String sql = "DELETE FROM `coffee_van_db`.`van` WHERE (`id` = '" + id.toString() + "');";

        if (execute(sql)) {
            logger.warning("Cannot delete van with {" + id + "} id");
        }
    }

    public static void deleteCoffee(UUID id) {
        String sql = "DELETE FROM coffee_van_db.coffee WHERE id = '" + id.toString() + "';";
        if (execute(sql)) {
            logger.warning("Cannot delete coffee with {" + id + "} id");
        }
    }

    public static void deleteVanCoffeeGoods(UUID id) {
        String sql = "DELETE FROM coffee_van_db.coffee_goods WHERE coffee_goods.id_van = '" + id.toString() + "';";
        if (execute(sql)) {
            logger.info("Previous coffee goods were deleted.");
        }
    }

    /**
     * Execute sql command.
     *
     * @param sql Sql command to execute;
     * @return true if error happens;
     */
    private static boolean execute(String sql) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);) {
            connection.createStatement().executeUpdate(sql);
            connection.close();
            return false;
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return true;
        }
    }

    /**
     * Execute sql query.
     *
     * @param sql command to execute
     * @return ResultSet with data or null, if cannot execute;
     */
    private static @Nullable ResultSet executeQuery(String sql) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            ResultSet result = connection.createStatement().executeQuery(sql);
            return result;
        } catch (SQLException e) {
            logger.warning("Error while executing query: " + e.getMessage());
            return null;
        }
    }
}
