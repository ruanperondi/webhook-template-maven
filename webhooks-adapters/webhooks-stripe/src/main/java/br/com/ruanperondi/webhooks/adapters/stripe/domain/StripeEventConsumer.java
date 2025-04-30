package br.com.ruanperondi.webhooks.adapters.stripe.domain;

import com.stripe.model.Event;

public interface StripeEventConsumer {
    void consume(Event event);
} 