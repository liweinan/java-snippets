package io.weli.testdome;

public class MegaStore2 {
    public enum DiscountType {
        Standard,
        Seasonal,
        Weight;
    }

    public static double getDiscountedPrice(double cartWeight,
                                            double totalPrice,
                                            DiscountType discountType) {
        double discount = 0.00;

        switch (discountType) {
            case Standard -> discount = 0.06;
            case Seasonal -> discount = 0.12;
            case Weight -> {
                if (cartWeight <= 10) {
                    discount = 0.06;
                } else {
                    discount = 0.18;
                }
            }
        }
        return totalPrice * (1 - discount);
    }

    public static void main(String[] args) {
        System.out.println(getDiscountedPrice(12, 100, DiscountType.Weight));
    }
}
