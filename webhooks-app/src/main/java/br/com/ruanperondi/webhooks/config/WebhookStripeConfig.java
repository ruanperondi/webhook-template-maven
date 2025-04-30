package br.com.ruanperondi.webhooks.config;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import br.com.ruanperondi.webhooks.adapters.persistence.repository.WebhookClientRepository;
import br.com.ruanperondi.webhooks.domain.exceptions.WebhookException;

@Configuration
public class WebhookStripeConfig {

    @Value("${stripe.secret}")
    private String stripeSecret;

    @Bean
    public BiFunction<String, String, Event> stripeMapper(WebhookClientRepository webhookClientRepository) {
        return (payload, signature) -> {
            try {
                return Webhook.constructEvent(payload, signature, stripeSecret);
            } catch (SignatureVerificationException ex) {
                throw new WebhookException("Error processing Stripe webhook: " + ex.getMessage(), ex);
            }
        };
    }

}
