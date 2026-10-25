
package discount;

import java.util.List;

public class DiscountService {

    private final DiscountRepository discountRepository;
    private int nextDiscountId = 1;

    public DiscountService(DiscountRepository discountRepository) {
        if (discountRepository == null) {
            throw new IllegalArgumentException("Discount repository is required");
        }

        this.discountRepository = discountRepository;

        for (Discount discount : discountRepository.getAllDiscounts()) {
            if (discount.getDiscountId() >= nextDiscountId) {
                nextDiscountId = discount.getDiscountId() + 1;
            }
        }
    }

    public Discount createDiscount(String discountName,
                                   DiscountType discountType,
                                   double discountValue,
                                   double minimumOrderAmount) {

        Discount discount = new Discount(
                nextDiscountId,
                discountName,
                discountType,
                discountValue,
                minimumOrderAmount
        );

        discountRepository.addDiscount(discount);
        nextDiscountId++;

        return discount;
    }

    public boolean deleteDiscount(int discountId) {
        return discountRepository.deleteDiscount(discountId);
    }

    public Discount getDiscountById(int discountId) {
        return discountRepository.findById(discountId);
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.getAllDiscounts();
    }

    public List<Discount> getActiveDiscounts() {
        return discountRepository.getActiveDiscounts();
    }

    public void activateDiscount(int discountId) {
        Discount discount = discountRepository.findById(discountId);

        if (discount == null) {
            throw new IllegalArgumentException("Discount not found");
        }

        discount.setActive(true);
    }

    public void deactivateDiscount(int discountId) {
        Discount discount = discountRepository.findById(discountId);

        if (discount == null) {
            throw new IllegalArgumentException("Discount not found");
        }

        discount.setActive(false);
    }

    public double calculateDiscount(int discountId, double orderAmount) {
        if (orderAmount < 0) {
            throw new IllegalArgumentException("Order amount cannot be negative");
        }

        Discount discount = discountRepository.findById(discountId);

        if (discount == null) {
            throw new IllegalArgumentException("Discount not found");
        }

        return discount.calculateDiscount(orderAmount);
    }
}
