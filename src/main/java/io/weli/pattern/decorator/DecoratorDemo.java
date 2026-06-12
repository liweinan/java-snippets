package io.weli.pattern.decorator;

public class DecoratorDemo {

    public static void main(String[] args) {
        Drink drink = new MilkDecorator(new SimpleDrink());
        System.out.println(drink.description() + " => " + drink.price());
    }
}
