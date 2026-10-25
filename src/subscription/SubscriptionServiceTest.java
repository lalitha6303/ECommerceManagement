
package subscription;

public class SubscriptionServiceTest {

    public static void main(String[] args) {

        SubscriptionRepository repository =
                new SubscriptionRepository();

        SubscriptionService service =
                new SubscriptionService(repository);

        System.out.println("===== CREATE SUBSCRIPTION =====");

        Subscription subscription =
                service.createSubscription(
                        101, SubscriptionPlan.YEARLY
                );

        System.out.println("\n===== CHECK FREE SHIPPING =====");

        System.out.println("Eligible: " +
                service.hasFreeShipping(101));

        System.out.println("\n===== CANCEL SUBSCRIPTION =====");

        service.cancelSubscription(
                subscription.getSubscriptionId()
        );

        System.out.println("\n===== CHECK AGAIN =====");

        System.out.println("Eligible: " +
                service.hasFreeShipping(101));
    }
}
