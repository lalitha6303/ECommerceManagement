
package subscription;

import java.time.LocalDate;

public class Subscription {

    private final int subscriptionId;
    private final int customerId;
    private final SubscriptionPlan plan;
    private final LocalDate startDate;
    private final LocalDate expiryDate;
    private SubscriptionStatus status;

    public Subscription(int subscriptionId, int customerId,
                        SubscriptionPlan plan, LocalDate startDate) {

        if (subscriptionId <= 0) {
            throw new IllegalArgumentException("Invalid subscription ID");
        }

        if (customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        if (plan == null || startDate == null) {
            throw new IllegalArgumentException(
                    "Plan and start date are required"
            );
        }

        this.subscriptionId = subscriptionId;
        this.customerId = customerId;
        this.plan = plan;
        this.startDate = startDate;
        this.expiryDate = calculateExpiryDate(startDate, plan);
        this.status = SubscriptionStatus.ACTIVE;
    }

    private LocalDate calculateExpiryDate(LocalDate date,
                                          SubscriptionPlan plan) {

        switch (plan) {
            case MONTHLY:
                return date.plusMonths(1);

            case QUARTERLY:
                return date.plusMonths(3);

            case HALF_YEARLY:
                return date.plusMonths(6);

            case YEARLY:
                return date.plusMonths(12);

            default:
                throw new IllegalArgumentException("Invalid subscription plan");
        }
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE
                && !LocalDate.now().isAfter(expiryDate);
    }

    public void cancel() {
        if (status == SubscriptionStatus.ACTIVE) {
            status = SubscriptionStatus.CANCELLED;
        }
    }

    public void refreshStatus() {
        if (status == SubscriptionStatus.ACTIVE
                && LocalDate.now().isAfter(expiryDate)) {
            status = SubscriptionStatus.EXPIRED;
        }
    }

    public void displaySubscription() {
        refreshStatus();

        System.out.println("Subscription ID: " + subscriptionId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Plan: " + plan);
        System.out.println("Start Date: " + startDate);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("Status: " + status);
        System.out.println("Free Shipping Eligible: " + isActive());
    }
}
