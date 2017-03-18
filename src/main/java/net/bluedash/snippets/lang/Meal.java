package net.bluedash.snippets.lang;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public enum Meal {

    BREAKFAST(7, 30) {
        public double mealPrice(Day day) {
            double breakfastPrice = 10.50;
            if (day.equals(Day.SATURDAY) || day == Day.SUNDAY)
                breakfastPrice *= 1.5;
            return breakfastPrice;
        }

        public String toString() {
            return "Breakfast";
        }
    };

    private int hh;
    private int mm;

    public enum Day { // implicitly static
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public static int A = 0;

    abstract double mealPrice(Day day);

    public int getHh() {
        return hh;
    }

    public void setHh(int hh) {
        this.hh = hh;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    Meal(int hh, int mm) {
        assert (hh >= 0 && hh <= 23) : "Illegal hour.";
        assert (mm >= 0 && mm <= 59) : "Illegal mins.";
        this.hh = hh;
        this.mm = mm;
    }

}
