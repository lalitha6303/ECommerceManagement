
package subscription;

import java.time.LocalDate;

public class SubscriptionRepositoryTest {

    public static void main(String[] args) {

        SubscriptionRepository repository =
                new SubscriptionRepository();

        Subscription s1 = new Subscription(
                1, 101, SubscriptionPlan.MONTHLY, LocalDate.now()
        );

        Subscription s2 = new Subscription(
                2, 101, SubscriptionPlan.YEARLY, LocalDate.now()
        );

        System.out.println("Added s1: " +
                repository.addSubscription(s1));

        System.out.println("Added s2: " +
                repository.addSubscription(s2));

        System.out.println("Duplicate ID: " +
                repository.addSubscription(s1));

        System.out.println("\nSubscriptions for customer 101:");

        for (Subscription subscription :
                repository.findByCustomerId(101)) {
            subscription.displaySubscription();
            System.out.println();
        }

        System.out.println("Total subscriptions: " +
                repository.getAllSubscriptions().size());
    }
}
