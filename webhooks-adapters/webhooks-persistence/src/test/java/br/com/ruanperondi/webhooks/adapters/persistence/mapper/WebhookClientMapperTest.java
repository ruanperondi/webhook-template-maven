package br.com.ruanperondi.webhooks.adapters.persistence.mapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookClientJPAEntity;
import br.com.ruanperondi.webhooks.adapters.persistence.mapper.impl.WebhookClientMapperImpl;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

class WebhookClientMapperTest {

    private final WebhookClientMapper mapper = new WebhookClientMapperImpl();

    @Test
    void shouldMapToEntity() {
        // Given
        WebhookClient domain = WebhookClient.builder()
                .id(UUID.randomUUID())
                .name("Test Client")
                .headerKey("X-Test-Key")
                .secretKey("test-secret")
                .maxAge(3600L)
                .timestampHeaderKey("X-Timestamp")
                .signatureHeaderKey("X-Signature")
                .separator("|")
                .build();

        // When
        WebhookClientJPAEntity entity = mapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getHeaderKey(), entity.getHeaderKey());
        assertEquals(domain.getSecretKey(), entity.getSecretKey());
        assertEquals(domain.getMaxAge(), entity.getMaxAge());
        assertEquals(domain.getTimestampHeaderKey(), entity.getTimestampHeaderKey());
        assertEquals(domain.getSignatureHeaderKey(), entity.getSignatureHeaderKey());
        assertEquals(domain.getSeparator(), entity.getSeparator());
    }

    @Test
    void shouldMapToDomain() {
        // Given
        WebhookClientJPAEntity entity = WebhookClientJPAEntity.builder()
                .id(UUID.randomUUID())
                .name("Test Client")
                .headerKey("X-Test-Key")
                .secretKey("test-secret")
                .maxAge(3600L)
                .timestampHeaderKey("X-Timestamp")
                .signatureHeaderKey("X-Signature")
                .separator("|")
                .build();

        // When
        WebhookClient domain = mapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getHeaderKey(), domain.getHeaderKey());
        assertEquals(entity.getSecretKey(), domain.getSecretKey());
        assertEquals(entity.getMaxAge(), domain.getMaxAge());
        assertEquals(entity.getTimestampHeaderKey(), domain.getTimestampHeaderKey());
        assertEquals(entity.getSignatureHeaderKey(), domain.getSignatureHeaderKey());
        assertEquals(entity.getSeparator(), domain.getSeparator());
    }
}
