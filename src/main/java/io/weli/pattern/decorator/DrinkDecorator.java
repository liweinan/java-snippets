package io.weli.pattern.decorator;

public abstract class DrinkDecorator implements Drink {

    protected final Drink drink;

    protected DrinkDecorator(Drink drink) {
        this.drink = drink;
    }
}
