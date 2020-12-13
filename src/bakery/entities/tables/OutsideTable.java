package bakery.entities.tables;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;

public class OutsideTable extends BaseTable{


    public OutsideTable(int tableNumber, int capacity) {
        super(tableNumber, capacity, 3.50);

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
        return drinkSum + foodSum;
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
        StringBuilder sb1 = new StringBuilder();
        sb1.append(String.format("Table: %d",this.tableNumber)).append(System.lineSeparator());
        sb1.append("Type: OutsideTable").append(System.lineSeparator());
        sb1.append(String.format("Capacity: %d",getCapacity())).append(System.lineSeparator());
        sb1.append(String.format("Price per Person: %.2f",getPricePerPerson())).append(System.lineSeparator());

        return sb1.toString().trim();
    }
}
