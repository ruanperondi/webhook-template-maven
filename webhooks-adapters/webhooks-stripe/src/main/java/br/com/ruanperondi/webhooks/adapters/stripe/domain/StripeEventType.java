package br.com.ruanperondi.webhooks.adapters.stripe.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StripeEventType {
    PAYMENT_INTENT_SUCCEEDED("payment_intent.succeeded"),
    PAYMENT_INTENT_FAILED("payment_intent.payment_failed"),
    CHARGE_SUCCEEDED("charge.succeeded"),
    CHARGE_FAILED("charge.failed"),
    CUSTOMER_SUBSCRIPTION_CREATED("customer.subscription.created"),
    CUSTOMER_SUBSCRIPTION_UPDATED("customer.subscription.updated"),
    CUSTOMER_SUBSCRIPTION_DELETED("customer.subscription.deleted");

    private final String type;

    public static StripeEventType fromString(String type) {
        for (StripeEventType eventType : values()) {
            if (eventType.type.equals(type)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Event type not found: " + type);
    }
}
