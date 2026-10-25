
package subscription;

import java.time.LocalDate;

public class SubscriptionTest {

    public static void main(String[] args) {

        Subscription subscription = new Subscription(
                1,
                101,
                SubscriptionPlan.QUARTERLY,
                LocalDate.now()
        );

        subscription.displaySubscription();

        System.out.println("\nCancelling subscription...");

        subscription.cancel();

        subscription.displaySubscription();
    }
}
