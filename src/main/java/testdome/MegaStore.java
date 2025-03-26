package testdome;

public class MegaStore {

    public enum DiscountType {
        Standard,
        Seasonal,
        Weight;
    }

    // standard 6%
    // season 12%
    // weigth <= 10 6%
    // weight > 10 / weight 18%
    public static double getDiscountedPrice(double cartWeight,
                                            double totalPrice,
                                            DiscountType discountType) {
        if (discountType == DiscountType.Standard) {
            return totalPrice * (1 - 0.06);
        } else if (discountType == DiscountType.Seasonal) {
            return totalPrice * (1 - 0.12);
        } else if (discountType == DiscountType.Weight) {
            if (cartWeight <= 10) {
                return totalPrice * (1 - 0.06);
            } else {
                return totalPrice * (1 - 0.18);
            }
        } else {
            return totalPrice;
        }
    }

    public static void main(String[] args) {
        System.out.println(getDiscountedPrice(12, 100, DiscountType.Weight));
    }
}
