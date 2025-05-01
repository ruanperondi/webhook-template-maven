package br.com.ruanperondi.webhooks.adapters.persistence;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookEventJPAEntity;
import br.com.ruanperondi.webhooks.adapters.persistence.repository.WebhookEventRepository;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;
import br.com.ruanperondi.webhooks.domain.ports.WebhookProcessorPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JPA adapter implementation for persisting webhook events.
 * This class is responsible for:
 * - Converting domain events to JPA entities
 * - Persisting events in the database
 * - Handling any persistence errors gracefully
 * - Providing audit information for all webhook events
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WebhookProcessorJPAAdapter implements WebhookProcessorPort {

    private final WebhookEventRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void process(WebhookClient client, String payload, Map<String, Object> headers) {
        String headersJson = null;

        try {
            headersJson = objectMapper.writeValueAsString(headers);
        } catch (JsonProcessingException e) {
            log.error("Error serializing headers", e);
        }

        repository.save(WebhookEventJPAEntity.builder()
                .id(UUID.randomUUID())
                .payload(payload)
                .headers(headersJson)
                .clientId(client.getId())
                .build());
    }
}