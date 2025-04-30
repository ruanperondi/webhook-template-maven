package br.com.ruanperondi.webhooks.adapters.stripe.consumers;

import com.stripe.model.Event;

public interface StripeEventConsumer {

    void consume(Event event);
} 