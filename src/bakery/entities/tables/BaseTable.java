package bakery.entities.tables;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;

import static bakery.common.ExceptionMessages.*;

public abstract class BaseTable implements Table {
    private Collection<BakedFood> foodOrders;
    private Collection<Drink> drinkOrders;
    protected int tableNumber;
    protected int capacity;
    protected int numberOfPeople;
    private double pricePerPerson;
    private boolean isReserved;
    private double price;


    public BaseTable(int tableNumber, int capacity, double pricePerPerson) {
        setTableNumber(tableNumber);
        setCapacity(capacity);
        setPricePerPerson(pricePerPerson);
        this.foodOrders = new ArrayList<>();
        this.drinkOrders = new ArrayList<>();
        this.isReserved = false;
        setPrice();
        setNumberOfPeople(0);

    }

    public Collection<BakedFood> getFoodOrders() {
        return foodOrders;
    }


    public Collection<Drink> getDrinkOrders() {
        return drinkOrders;
    }


    @Override
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber ;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(INVALID_TABLE_CAPACITY);
        }
        this.capacity = capacity;
    }

    @Override
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople < 0) {
            throw new IllegalArgumentException(INVALID_NUMBER_OF_PEOPLE);
        }
        this.numberOfPeople = numberOfPeople;
    }

    @Override
    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice() {
        this.price = this.numberOfPeople * this.pricePerPerson;
    }

}
