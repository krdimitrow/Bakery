package bakery.core;

import bakery.core.interfaces.Controller;

import bakery.entities.bakedFoods.Bread;
import bakery.entities.bakedFoods.Cake;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.Tea;
import bakery.entities.drinks.Water;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.tables.InsideTable;
import bakery.entities.tables.OutsideTable;
import bakery.entities.tables.interfaces.Table;
import bakery.repositories.DrinkRepositoryImpl;
import bakery.repositories.FoodRepositoryImpl;
import bakery.repositories.TableRepositoryImpl;
import bakery.repositories.interfaces.*;

import java.util.ArrayList;
import java.util.List;

import static bakery.common.ExceptionMessages.*;
import static bakery.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    protected FoodRepository<BakedFood> foodRepository;
    protected DrinkRepository<Drink> drinkRepository;
    protected TableRepository<Table> tableRepository;
    List<Double>bills;


    public ControllerImpl(FoodRepository<BakedFood> foodRepository, DrinkRepository<Drink> drinkRepository, TableRepository<Table> tableRepository) {
        this.foodRepository = new FoodRepositoryImpl();
        this.drinkRepository = new DrinkRepositoryImpl();
        this.tableRepository = new TableRepositoryImpl();
        this.bills = new ArrayList<>();
    }


    @Override
    public String addFood(String type, String name, double price) {
        BakedFood food = null;
        if ("Bread".equals(type)) {
            food = new Bread(name, price);
        } else if ("Cake".equals(type)) {
            food = new Cake(name, price);
        }
        if (foodRepository.getAll().contains(food)) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
        }
        foodRepository.add(food);

        return String.format(FOOD_ADDED, name, type);
    }

    @Override
    public String addDrink(String type, String name, int portion, String brand) {
        Drink drink = null;
        if ("Tea".equals(type)) {
            drink = new Tea(name, portion, brand);
        } else if ("Water".equals(type)) {
            drink = new Water(name, portion, brand);
        }

        if (drinkRepository.getAll().contains(drink)) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
        }
        drinkRepository.add(drink);

        return String.format(DRINK_ADDED, name, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        Table table = null;
        if ("InsideTable".equals(type)) {
            table = new InsideTable(tableNumber, capacity);
        } else if ("OutsideTable".equals(type)) {
            table = new OutsideTable(tableNumber, capacity);
        }
        if (tableRepository.getAll().contains(table)) {
            throw new IllegalArgumentException(String.format(TABLE_EXIST, tableNumber));
        }

        tableRepository.add(table);

        return String.format(TABLE_ADDED, tableNumber);
    }

    @Override
    public String reserveTable(int numberOfPeople) {
        Table table = null;
        for (Table t : tableRepository.getAll()) {
            if (!t.isReserved() && t.getCapacity() >= numberOfPeople) {
                table = t;
                break;
            }
        }
        if (table == null) {
            return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }
        table.reserve(numberOfPeople);
        return String.format(TABLE_RESERVED, table.getTableNumber(), numberOfPeople);
    }

    @Override
    public String orderFood(int tableNumber, String foodName) {
        Table table = null;
        BakedFood food = null;
        for (Table t : tableRepository.getAll()) {
            if (t.getTableNumber() == tableNumber && t.isReserved()) {
                table = t;
                break;
            }
        }
        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }


        for (BakedFood f : foodRepository.getAll()) {
            if (f.getName().equals(foodName)) {
                food = f;
                break;
            }
        }
        if (food == null) {
            return String.format(NONE_EXISTENT_FOOD, foodName);
        }

        table.orderFood(food);
        return String.format(FOOD_ORDER_SUCCESSFUL, tableNumber, foodName);

    }

    @Override
    public String orderDrink(int tableNumber, String drinkName, String drinkBrand) {
        Table table = null;
        Drink drink = null;
        for (Table t : tableRepository.getAll()) {
            if (t.getTableNumber() == tableNumber) {
                table = t;
                break;
            }
        }
        if (table == null) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }


        for (Drink d : drinkRepository.getAll()) {
            if (d.getName().equals(drinkName) && d.getBrand().equals(drinkBrand)) {
                drink = d;
                break;
            }
        }
        if (drink == null) {
            return String.format(NON_EXISTENT_DRINK, drinkName, drinkBrand);
        }


        table.orderDrink(drink);

        return String.format(DRINK_ORDER_SUCCESSFUL, tableNumber, drinkName, drinkBrand);

    }

    @Override
    public String leaveTable(int tableNumber) {
        Table table = null;
        for (Table t : tableRepository.getAll()) {
            if (t.getTableNumber() == tableNumber && t.isReserved()) {
                table = t;
                break;
            }
        }
        double bill = 0;

        assert table != null;
        bill = table.getBill();
        this.bills.add(bill);


        return String.format(BILL, tableNumber, bill);
    }

    @Override
    public String getFreeTablesInfo() {
        StringBuilder sb = new StringBuilder();
        for (Table table : tableRepository.getAll()) {
            if (table.getBill() == 0) {
                sb.append(table.getFreeTableInfo()).append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String getTotalIncome() {
        double sum = 0;

        for (Double bill : bills) {
            sum+=bill;
        }
        return String.format(TOTAL_INCOME, sum);
    }
}
