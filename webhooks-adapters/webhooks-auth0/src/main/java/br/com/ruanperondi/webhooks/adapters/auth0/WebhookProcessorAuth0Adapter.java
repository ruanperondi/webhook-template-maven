package br.com.ruanperondi.webhooks.adapters.auth0;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ruanperondi.webhooks.adapters.auth0.consumers.Auth0EventConsumer;
import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0Event;
import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0EventType;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;
import br.com.ruanperondi.webhooks.domain.ports.WebhookProcessorPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter implementation for processing Auth0 webhook events.
 * This class is responsible for:
 * - Receiving raw webhook payloads
 * - Converting them to Auth0Event objects
 * - Routing events to appropriate consumers based on event type
 * - Handling any processing errors gracefully
 */
@RequiredArgsConstructor
@Slf4j
public class WebhookProcessorAuth0Adapter implements WebhookProcessorPort {

    private final ObjectMapper mapper;
    private final Map<Auth0EventType, Auth0EventConsumer> consumers;

    @Override
    public void process(WebhookClient client, String payload, Map<String, Object> headers) {
        try {
            log.info("Processing event: {}", payload);
            Auth0Event event = mapper.readValue(payload, Auth0Event.class);
            Auth0EventConsumer consumer = consumers.get(event.getEventType());
            if (consumer == null) {
                log.warn("No consumer found for event type: {}", event.getEventType());
                return;
            }
            log.info("Consuming eventType: {}", event.getEventType());
            consumer.consume(event);
        } catch (JsonProcessingException e) {
            log.error("Error processing event: {}", e.getMessage());
        }
    }
}
