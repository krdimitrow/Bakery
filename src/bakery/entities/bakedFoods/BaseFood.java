package bakery.entities.bakedFoods;

import bakery.entities.bakedFoods.interfaces.BakedFood;

import static bakery.common.ExceptionMessages.*;

public abstract class BaseFood implements BakedFood {
    protected String name;
    protected double portion;
    protected double price;

    public BaseFood(String name, double portion, double price) {
        setName(name);
        setPortion(portion);
        setPrice(price);
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
    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
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
            throw new IllegalArgumentException(INVALID_PRICE);
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2fg - %.2f%n", getName(), getPortion(), getPrice());
    }
}
