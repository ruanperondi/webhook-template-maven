package br.com.ruanperondi.webhooks.adapters.stripe.consumers.impl;

import org.springframework.stereotype.Component;

import com.stripe.model.Event;

import br.com.ruanperondi.webhooks.adapters.stripe.consumers.StripeEventBind;
import br.com.ruanperondi.webhooks.adapters.stripe.consumers.StripeEventConsumer;
import br.com.ruanperondi.webhooks.adapters.stripe.domain.StripeEventType;
import lombok.extern.slf4j.Slf4j;

@StripeEventBind(StripeEventType.CHARGE_SUCCEEDED)
@Slf4j
@Component
public class StripeChargeSucceededConsumer implements StripeEventConsumer {

    @Override
    public void consume(Event event) {
        // TODO: Implement the logic to consume the charge succeeded event
    }
}
