package model;

import java.util.UUID;

public class CoffeeGoods {
    private final UUID id;
    private final UUID vanId;
    private final UUID coffeeId;
    private final int number;

    public CoffeeGoods(UUID vanId, UUID coffeeId, int number) {
        this.id = UUID.randomUUID();
        this.vanId = vanId;
        this.coffeeId = coffeeId;
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public UUID getVanId() {
        return vanId;
    }

    public UUID getCoffeeId() {
        return coffeeId;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Data is assigned to {" + id + "} van;" + "\n\t" +
                "Coffee id {" + coffeeId + "}; Number {" + number + "};";
    }

    public String toSQLvalue() {
        return "('" + id.toString() + "', '" + vanId.toString() + "', '" + coffeeId + "', " + number + ");";
    }

    public String toListView() {
        return "Coffee id {" + coffeeId + "}. Number of goods: " + number;
    }
}