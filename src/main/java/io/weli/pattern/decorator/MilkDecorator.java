package io.weli.pattern.decorator;

public class MilkDecorator extends DrinkDecorator {

    public MilkDecorator(Drink drink) {
        super(drink);
    }

    @Override
    public String description() {
        return drink.description() + " + Milk";
    }

    @Override
    public double price() {
        return drink.price() + 2.0;
    }
}
