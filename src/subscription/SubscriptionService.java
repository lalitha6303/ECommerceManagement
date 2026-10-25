
package subscription;

import java.time.LocalDate;
import java.util.List;

public class SubscriptionService {

    private final SubscriptionRepository repository;
    private int nextSubscriptionId = 1;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public Subscription createSubscription(
            int customerId, SubscriptionPlan plan) {

        if (customerId <= 0 || plan == null) {
            System.out.println("Invalid customer ID or plan.");
            return null;
        }

        Subscription subscription = new Subscription(
                nextSubscriptionId++,
                customerId,
                plan,
                LocalDate.now()
        );

        if (!repository.addSubscription(subscription)) {
            System.out.println("Could not create subscription.");
            return null;
        }

        System.out.println("Subscription created successfully.");
        subscription.displaySubscription();

        return subscription;
    }

    public Subscription getSubscriptionById(int subscriptionId) {
        Subscription subscription = repository.findById(subscriptionId);

        if (subscription != null) {
            subscription.refreshStatus();
        }

        return subscription;
    }

    public List<Subscription> getSubscriptionsByCustomer(int customerId) {
        List<Subscription> subscriptions =
                repository.findByCustomerId(customerId);

        for (Subscription subscription : subscriptions) {
            subscription.refreshStatus();
        }

        return subscriptions;
    }

    public List<Subscription> getAllSubscriptions() {
        List<Subscription> subscriptions =
                repository.getAllSubscriptions();

        for (Subscription subscription : subscriptions) {
            subscription.refreshStatus();
        }

        return subscriptions;
    }

    public boolean cancelSubscription(int subscriptionId) {
        Subscription subscription = getSubscriptionById(subscriptionId);

        if (subscription == null) {
            System.out.println("Subscription not found.");
            return false;
        }

        if (!subscription.isActive()) {
            System.out.println("Subscription is not active.");
            return false;
        }

        subscription.cancel();
        System.out.println("Subscription cancelled successfully.");
        return true;
    }

    public boolean hasFreeShipping(int customerId) {
        List<Subscription> subscriptions =
                getSubscriptionsByCustomer(customerId);

        for (Subscription subscription : subscriptions) {
            if (subscription.isActive()) {
                return true;
            }
        }

        return false;
    }
}
