package bakery.entities.tables;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;

public class InsideTable extends BaseTable {


    public InsideTable(int tableNumber, int capacity) {
        super(tableNumber, capacity, 2.50);

    }

    @Override
    public void reserve(int numberOfPeople) {
        this.setReserved(true);
        this.setNumberOfPeople(numberOfPeople);
    }

    @Override
    public void orderFood(BakedFood food) {
        getFoodOrders().add(food);
    }

    @Override
    public void orderDrink(Drink drink) {
        getDrinkOrders().add(drink);
    }

    @Override
    public double getBill() {

        double drinkSum = getDrinkOrders().stream().mapToDouble(Drink::getPrice).sum();
        double foodSum = getFoodOrders().stream().mapToDouble(BakedFood::getPrice).sum();
        return drinkSum + foodSum + (this.numberOfPeople * getPricePerPerson());
    }

    @Override
    public void clear() {
        getDrinkOrders().clear();
        getFoodOrders().clear();
        setReserved(false);
        setNumberOfPeople(0);

    }


    @Override
    public String getFreeTableInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Table: %d", this.tableNumber)).append(System.lineSeparator());
        sb.append("Type: InsideTable").append(System.lineSeparator());
        sb.append(String.format("Capacity: %d", this.getCapacity())).append(System.lineSeparator());
        sb.append(String.format("Price per Person: %.2f", this.getPricePerPerson())).append(System.lineSeparator());

        return sb.toString().trim();
    }
}
