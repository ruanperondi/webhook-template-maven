package br.com.ruanperondi.webhooks.adapters.stripe.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ruanperondi.webhooks.adapters.stripe.consumers.StripeEventBind;
import br.com.ruanperondi.webhooks.adapters.stripe.consumers.StripeEventConsumer;

@Configuration
public class StripeEventConfiguration {

    @Bean
    public Map<StripeEventType, StripeEventConsumer> getConsumers(List<StripeEventConsumer> consumerList) {
        final Map<StripeEventType, StripeEventConsumer> consumers = new EnumMap<>(StripeEventType.class);
        consumerList.forEach(consumer -> {
            StripeEventBind annotation = consumer.getClass().getAnnotation(StripeEventBind.class);
            if (annotation != null) {
                consumers.put(annotation.value(), consumer);
            }
        });

        return consumers;
    }

}
