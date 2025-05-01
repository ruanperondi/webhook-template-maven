package br.com.ruanperondi.webhooks.adapters.persistence;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookEventJPAEntity;
import br.com.ruanperondi.webhooks.adapters.persistence.repository.WebhookEventRepository;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

class WebhookProcessorJPAAdapterTest {

    private WebhookEventRepository repository;
    private ObjectMapper objectMapper;
    private WebhookProcessorJPAAdapter adapter;

    @BeforeEach
    void setup() {
        repository = mock(WebhookEventRepository.class);
        objectMapper = new ObjectMapper();
        adapter = new WebhookProcessorJPAAdapter(repository, objectMapper);
    }

    @Test
    void shouldProcessEvent() {
        // Given
        WebhookClient client = WebhookClient.builder()
            .id(UUID.randomUUID())
            .name("Test Client")
            .headerKey("X-Test-Key")
            .secretKey("test-secret")
            .timestampHeaderKey("X-Timestamp")
            .signatureHeaderKey("X-Signature")
            .separator("|")
            .build();
        String payload = "test payload";
        Map<String, Object> headers = Map.of("header1", "value1");

        adapter.process(client, payload, headers);

        verify(repository).save(any(WebhookEventJPAEntity.class));
    }
}
