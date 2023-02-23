package main;

import model.Coffee;
import model.CoffeeGoods;
import model.Van;
import model.db.Repository;
import model.enumerators.AggregateState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

import static helpers.AppLogger.logger;
import static model.db.Repository.*;

public class Manager {
    Manager() {
        logger.info("Initialized manager!");
    }

    public void addVan(@NotNull Van van) {
        if (van.getCapacity() != 0) {
            insertVan(van);
            logger.info("Van {" + van.getId() + "} was added!");
        } else {
            logger.warning("method 'addVan' was skipped");
        }
    }

    public void changeVan(UUID id, Van van) {
        if (id != null) {
            updateVan(id, van);
            logger.info("Van {" + id + "} was changed!");
        } else {
            logger.warning("method 'changeVan' was skipped");
        }
    }

    public void deleteVan(UUID id) {
        if (id != null) {
            Repository.deleteVan(id);
            logger.info("Van was deleted!");
        } else {
            logger.warning("method 'deleteVan' was skipped");
        }
    }

    public void loadVan(UUID id) {
        if (id != null) {
            // Get van
            String sql = selectAllVanSQL + " WHERE id = '" + id.toString() + "';";

            var van = Repository.selectVans(sql);
            if (van != null) {
                // Load van by coffees, from db
                van.get(0).loadVan(selectCoffees(selectAllCoffeeSQL));
                // Load coffeeGoods to the db
                saveCoffeeGoods(van.get(0));
                logger.info("Van {" + id + "} was uploaded!");
            }
        } else {
            logger.warning("Method 'loadVan' was skipped!");
        }
    }

    private void saveCoffeeGoods(Van van) {
        if (van.getCoffeeGoods() != null) {
            for (CoffeeGoods goods : van.getCoffeeGoods()) {
                insertCoffeeGoods(goods);
            }
            logger.info("All loaded goods in van with id {" + van.getId() + "} was loaded to db!");
        }
    }

    /* Coffee methods*/

    public void addCoffee(Coffee coffee) {
        if (!coffee.isNull()) {
            insertCoffee(coffee);
            insertCultivationPlace(coffee.getCultivationPlace());
        } else {
            logger.warning("method 'addCoffee' was skipped!");
        }
    }

    public void changeCoffee(UUID id, Coffee coffee) {
        if (id != null) {
            boolean flag = updateCoffee(id, coffee);
            if (flag) {
                logger.info("Coffee {" + id + "} was changed!");
            }
        } else {
            logger.warning("method 'changeCoffee' was skipped!");
        }
    }

    public void deleteCoffee(UUID id) {
        if (id != null) {
            Repository.deleteCoffee(id);
            logger.info("Coffee was deleted!");
        } else {
            logger.warning("method 'deleteCoffee' was skipped!");
        }
    }

    public ArrayList<Coffee> sortCoffeeByQuality() {
        String sql = "SELECT * FROM coffee_van_db.coffee ORDER BY quality_mark;";
        var result = Repository.selectCoffees(sql);
        logger.info("Coffee was sorted by quality!");
        return result;
    }

    public ArrayList<Coffee> sortCoffeeByCorrelation() {
        String sql = "SELECT * FROM coffee_van_db.coffee ORDER BY (price / netto_weight);";
        var result = Repository.selectCoffees(sql);
        logger.info("Coffee was sorted by correlation!");
        return result;
    }

    // Find by name
    public ArrayList<Coffee> findCoffee(String name) {
        String sql = "SELECT * FROM coffee_van_db.coffee WHERE name = '" + name + "';";
        var foundCoffees = Repository.selectCoffees(sql);
        logger.info("Method 'findCoffee(String name)' finished his work!");
        return foundCoffees;
    }

    // Find by name, aggregated state
    public ArrayList<Coffee> findCoffee(String name, AggregateState aggregateState) {
        String sql = "SELECT * FROM coffee_van_db.coffee WHERE name = '" + name + "' AND aggregate_state = '" + aggregateState + "';";
        var foundCoffees = Repository.selectCoffees(sql);
        logger.info("Method 'findCoffee(String name, AggregateState aggregateState)' finished his work!");
        return foundCoffees;
    }

    // Find by quality mark
    public ArrayList<Coffee> findCoffee(int quality) {
        String sql = "SELECT * FROM coffee_van_db.coffee WHERE quality_mark = " + quality;
        var foundCoffees = Repository.selectCoffees(sql);
        logger.info("Method 'findCoffee(int quality)' finished his work!");
        return foundCoffees;
    }

    // Find by quality in range()
    public ArrayList<Coffee> findCoffee(int minQuality, int maxQuality) {
        String sql = "SELECT * FROM coffee_van_db.coffee WHERE quality_mark >= " + minQuality + " AND quality_mark <= " + maxQuality;
        var foundCoffees = Repository.selectCoffees(sql);
        logger.info("Method 'findCoffee(int minQuality, int maxQuality)' finished his work!");
        return foundCoffees;
    }

    // Find by correlation in range()
    public ArrayList<Coffee> findCoffee(float minCorrelation, float maxCorrelation) {
        String sql = "SELECT * FROM coffee_van_db.coffee WHERE (price / quality_mark) >= " + minCorrelation + " AND (price / netto_weight) <= " + maxCorrelation;
        var foundCoffees = Repository.selectCoffees(sql);
        logger.info("Method 'findCoffee(float minCorrelation, float maxCorrelation)' finished his work!");
        return foundCoffees;
    }
}