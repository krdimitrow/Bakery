package bakery.entities.drinks;

import bakery.entities.drinks.interfaces.Drink;

import static bakery.common.ExceptionMessages.*;

public abstract class BaseDrink implements Drink {
    private String name;
    private int portion;
    private double price;
    private String brand;

    public BaseDrink(String name, int portion, double price, String brand) {
        setName(name);
        setPortion(portion);
        setPrice(price);
        setBrand(brand);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(INVALID_NAME);
        }
        this.name = name;
    }

    @Override
    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        if (portion <= 0) {
            throw new IllegalArgumentException(INVALID_PORTION);
        }
        this.portion = portion;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException(INVALID_PORTION);
        }
        this.price = price;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException(INVALID_BRAND);
        }
        this.brand = brand;
    }

    @Override
    public String toString() {
        return String.format("%s %s - %dml - %.2flv",getName(),getBrand(),getPortion(),getPrice());
    }
}
