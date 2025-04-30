package br.com.ruanperondi.webhooks.adapters.stripe;

import java.util.Map;
import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import com.stripe.model.Event;

import br.com.ruanperondi.webhooks.adapters.stripe.consumers.StripeEventConsumer;
import br.com.ruanperondi.webhooks.adapters.stripe.domain.StripeEventType;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;
import br.com.ruanperondi.webhooks.domain.ports.WebhookProcessorPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebhookProcessorStripeAdapter implements WebhookProcessorPort {

    private final BiFunction<String, String, Event> stripeMapper;
    private final Map<StripeEventType, StripeEventConsumer> consumers;

    @Override
    public void process(WebhookClient client, String payload, Map<String, Object> headers) {
        try {
            String signature = (String) headers.get("stripe-signature");
            if (signature == null) {
                log.error("Missing stripe-signature header");
                return;
            }

            Event event = stripeMapper.apply(payload, signature);
            log.info("Processing Stripe event: {}", event.getType());

            StripeEventType eventType = StripeEventType.fromString(event.getType());
            StripeEventConsumer consumer = consumers.get(eventType);

            if (consumer == null) {
                log.warn("No consumer found for event type: {}", eventType);
                return;
            }

            log.info("Consuming eventType: {}", eventType);
            consumer.consume(event);
        } catch (Exception e) {
            log.error("Error processing Stripe event: {}", e.getMessage());
        }
    }
}
