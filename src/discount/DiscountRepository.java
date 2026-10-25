
package discount;

import java.util.ArrayList;
import java.util.List;

public class DiscountRepository {

    private final List<Discount> discounts = new ArrayList<>();

    public void addDiscount(Discount discount) {
        if (discount == null) {
            throw new IllegalArgumentException("Discount cannot be null");
        }

        if (findById(discount.getDiscountId()) != null) {
            throw new IllegalArgumentException("Discount ID already exists");
        }

        discounts.add(discount);
    }

    public boolean deleteDiscount(int discountId) {
        Discount discount = findById(discountId);

        if (discount == null) {
            return false;
        }

        discounts.remove(discount);
        return true;
    }

    public Discount findById(int discountId) {
        for (Discount discount : discounts) {
            if (discount.getDiscountId() == discountId) {
                return discount;
            }
        }

        return null;
    }

    public List<Discount> getAllDiscounts() {
        return new ArrayList<>(discounts);
    }

    public List<Discount> getActiveDiscounts() {
        List<Discount> activeDiscounts = new ArrayList<>();

        for (Discount discount : discounts) {
            if (discount.isActive()) {
                activeDiscounts.add(discount);
            }
        }

        return activeDiscounts;
    }
}
