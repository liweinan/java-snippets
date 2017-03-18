package net.bluedash.snippets.lang;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class UseDay {
    public static void main(String[] args) {
        Meal.BREAKFAST.mealPrice(Meal.Day.SATURDAY);

        System.out.println(Meal.A);
        System.out.println(Meal.BREAKFAST.A);
        System.out.println(Meal.class.equals(Meal.BREAKFAST.getClass()));
        System.out.printf("%s : %s", Meal.class, Meal.BREAKFAST.getClass());
    }
}
