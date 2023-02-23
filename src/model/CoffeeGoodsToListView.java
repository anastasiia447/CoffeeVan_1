package model;

public class CoffeeGoodsToListView {
    private final String name;
    private final String number;

    public CoffeeGoodsToListView(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Coffee name: " + name + ", number " + number + ";";
    }
}
