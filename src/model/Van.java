package model;

import main.Settings;
import model.db.AppDataBase;
import model.enumerators.CoffeeStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

import static helpers.AppLogger.logger;
import static model.db.Repository.insertCoffeeGoods;

public class Van {
    private UUID id;
    private float capacity; //m^3
    private ArrayList<CoffeeGoods> coffeeGoods;

    public Van(float capacity) {
        id = UUID.randomUUID();
        this.capacity = capacity;
        this.coffeeGoods = null;
    }

    public Van(UUID id, float capacity) {
        this.id = id;
        this.capacity = capacity;
        this.coffeeGoods = null;
    }

    @Override
    public String toString() {
        return"Van {" + id + "}\n\t" +
                "Capacity: " + capacity;
    }
    
    public UUID getId() {
        return id;
    }

    public float getCapacity() {
        return capacity;
    }

    public ArrayList<CoffeeGoods> getCoffeeGoods() {
        return coffeeGoods;
    }

    public void printCoffeeGoods() {
        if (coffeeGoods != null) {
            for (CoffeeGoods good : coffeeGoods) {
                System.out.println(good.toString());
            }
        } else {
            System.out.println("Van is empty!");
        }
    }

    public void loadVan(ArrayList<Coffee> coffeeList) {
        logger.info("'load van' method was executed.");
        // Convert m^3 in sm^3
        float ordinaryMaxVolume = this.capacity * 100000 * Settings.ordinaryQualityCoeff;
        float higherMaxVolume = this.capacity * 100000 * Settings.higherQualityCoeff;
        float specialtyMaxVolume = this.capacity * 100000 * Settings.specialtyQualityCoeff;

        // Fill the arrays with the same CoffeeStatus types
        ArrayList<Coffee> ordinaryCoffee = getCoffeesByStatus(coffeeList, CoffeeStatus.ordinary);
        ArrayList<Coffee> higherCoffee = getCoffeesByStatus(coffeeList, CoffeeStatus.higher);
        ArrayList<Coffee> specialtyCoffee = getCoffeesByStatus(coffeeList, CoffeeStatus.specialty);

        // Initialize coffeeGoods
        coffeeGoods = new ArrayList<>();

        // Choose goods to the van
        selectGoods(ordinaryCoffee, ordinaryMaxVolume);
        selectGoods(higherCoffee, higherMaxVolume);
        selectGoods(specialtyCoffee, specialtyMaxVolume);
    }

    // Writing new CoffeeGoods to the db and to the variable coffeeGoods
    private void selectGoods(@NotNull ArrayList<Coffee> coffee, float volume) {
        // Foreach goods...
        for (int i = 0; i < coffee.size(); i++) {
            // Count available space for this goods
            float currentVolume = volume / coffee.size() - i;
            // Integer num to count number of this goods
            int currNumber = (int) (currentVolume / coffee.get(i).getVolume());
            // Plus remained volume to general volume
            volume += (currentVolume % coffee.get(i).getVolume());

            // write down new data to array
            CoffeeGoods goods = new CoffeeGoods(this.id, coffee.get(i).getId(), currNumber);
            coffeeGoods.add(goods);
            insertCoffeeGoods(goods);
        }
    }

    // Return coffees array list where all coffees are the same coffee status
    private ArrayList<Coffee> getCoffeesByStatus(ArrayList<Coffee> coffeeList, CoffeeStatus status) {
        ArrayList<Coffee> result = new ArrayList<>();
        for (Coffee coffee : coffeeList) {
            if (coffee.getCoffeeStatus() == status) {
                result.add(coffee);
            }
        }
        return result;
    }

    // Print all data in SQL type
    public String toSQLvalues() {
        return "('" + id.toString() + "', " + capacity + ");";
    }

    // Print data in list view
    public String toListView() {
        return "Van: capacity: " + capacity;
    }
}