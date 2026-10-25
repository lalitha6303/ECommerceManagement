
package discount;

public class Discount {

    private final int discountId;
    private String discountName;
    private DiscountType discountType;
    private double discountValue;
    private double minimumOrderAmount;
    private boolean active;

    public Discount(int discountId, String discountName,
                    DiscountType discountType, double discountValue,
                    double minimumOrderAmount) {

        if (discountId <= 0) {
            throw new IllegalArgumentException("Discount ID must be positive");
        }

        if (discountName == null || discountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Discount name is required");
        }

        if (discountType == null) {
            throw new IllegalArgumentException("Discount type is required");
        }

        if (discountValue <= 0) {
            throw new IllegalArgumentException("Discount value must be positive");
        }

        if (minimumOrderAmount < 0) {
            throw new IllegalArgumentException("Minimum order amount cannot be negative");
        }

        if (discountType == DiscountType.PERCENTAGE && discountValue > 100) {
            throw new IllegalArgumentException("Percentage cannot exceed 100");
        }

        this.discountId = discountId;
        this.discountName = discountName;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minimumOrderAmount = minimumOrderAmount;
        this.active = true;
    }

    public int getDiscountId() {
        return discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public double getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public boolean isActive() {
        return active;
    }

    public void setDiscountName(String discountName) {
        if (discountName == null || discountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Discount name is required");
        }
        this.discountName = discountName;
    }

    public void setDiscountValue(double discountValue) {
        if (discountValue <= 0) {
            throw new IllegalArgumentException("Discount value must be positive");
        }

        if (discountType == DiscountType.PERCENTAGE && discountValue > 100) {
            throw new IllegalArgumentException("Percentage cannot exceed 100");
        }

        this.discountValue = discountValue;
    }

    public void setMinimumOrderAmount(double minimumOrderAmount) {
        if (minimumOrderAmount < 0) {
            throw new IllegalArgumentException("Minimum order amount cannot be negative");
        }
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double calculateDiscount(double orderAmount) {
        if (!active || orderAmount < minimumOrderAmount || orderAmount <= 0) {
            return 0;
        }

        double discountAmount;

        if (discountType == DiscountType.PERCENTAGE) {
            discountAmount = orderAmount * discountValue / 100;
        } else {
            discountAmount = discountValue;
        }

        return Math.min(discountAmount, orderAmount);
    }

    public void displayDiscount() {
        System.out.println("Discount ID: " + discountId);
        System.out.println("Discount Name: " + discountName);
        System.out.println("Discount Type: " + discountType);
        System.out.println("Discount Value: " + discountValue);
        System.out.println("Minimum Order Amount: " + minimumOrderAmount);
        System.out.println("Active: " + active);
    }
}
