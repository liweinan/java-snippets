package io.weli.testdome;

// https://www.testdome.com/library?page=1&skillArea=30&questionId=126358
/*
Example Case: Correct answer
Standard Discount: Correct answer
Seasonal Discount: Correct answer
Weighted Discount: Correct answer
Your score is 100%, perfect!
 */
public class MegaStore {
    // standard : 6%
    // seasonal / weight = any / 12%
    // weight / <= 10 / 6%
    //  > 10 / 18%
    public enum DiscountType {
        Standard,
        Seasonal,
        Weight;
    }

    public static double getDiscountedPrice(double cartWeight,
                                            double totalPrice,
                                            DiscountType discountType) {
        if (discountType == DiscountType.Seasonal) {
            return totalPrice * (1 - 0.12);
        } else if (discountType == DiscountType.Weight) {
            if (cartWeight <= 10) {
                return totalPrice * (1 - 0.06);
            } else {
                return totalPrice * (1 - 0.18);
            }
        } else {
            return totalPrice * (1 - 0.06);
        }
    }

    public static void main(String[] args) {
        System.out.println(getDiscountedPrice(12, 100, DiscountType.Weight));
    }

}
