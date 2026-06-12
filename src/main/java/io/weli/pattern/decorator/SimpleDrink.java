package io.weli.pattern.decorator;

public class SimpleDrink implements Drink {

    @Override
    public String description() {
        return "Coffee";
    }

    @Override
    public double price() {
        return 10.0;
    }
}
