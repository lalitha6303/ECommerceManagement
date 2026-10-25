
package discount;

public class DiscountServiceTest {

    public static void main(String[] args) {

        DiscountRepository repository = new DiscountRepository();
        DiscountService service = new DiscountService(repository);

        Discount discount = service.createDiscount(
                "Festival Offer",
                DiscountType.PERCENTAGE,
                10,
                1000
        );

        System.out.println("Created Discount:");
        discount.displayDiscount();

        System.out.println("\nTesting Discount Calculation:");

        System.out.println("Order Amount: 800");
        System.out.println("Discount: " +
                service.calculateDiscount(discount.getDiscountId(), 800));

        System.out.println("Order Amount: 1000");
        System.out.println("Discount: " +
                service.calculateDiscount(discount.getDiscountId(), 1000));

        System.out.println("Order Amount: 2000");
        System.out.println("Discount: " +
                service.calculateDiscount(discount.getDiscountId(), 2000));

        service.deactivateDiscount(discount.getDiscountId());

        System.out.println("\nAfter Deactivation:");
        System.out.println("Discount: " +
                service.calculateDiscount(discount.getDiscountId(), 2000));

        service.activateDiscount(discount.getDiscountId());

        System.out.println("\nAfter Reactivation:");
        System.out.println("Discount: " +
                service.calculateDiscount(discount.getDiscountId(), 2000));
    }
}
