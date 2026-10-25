
package subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepository {

    private final List<Subscription> subscriptions = new ArrayList<>();

    public boolean addSubscription(Subscription subscription) {

        if (subscription == null ||
                findById(subscription.getSubscriptionId()) != null) {
            return false;
        }

        subscriptions.add(subscription);
        return true;
    }

    public Subscription findById(int subscriptionId) {

        for (Subscription subscription : subscriptions) {
            if (subscription.getSubscriptionId() == subscriptionId) {
                return subscription;
            }
        }

        return null;
    }

    public List<Subscription> findByCustomerId(int customerId) {

        List<Subscription> result = new ArrayList<>();

        for (Subscription subscription : subscriptions) {
            if (subscription.getCustomerId() == customerId) {
                result.add(subscription);
            }
        }

        return result;
    }

    public List<Subscription> getAllSubscriptions() {
        return new ArrayList<>(subscriptions);
    }
}
