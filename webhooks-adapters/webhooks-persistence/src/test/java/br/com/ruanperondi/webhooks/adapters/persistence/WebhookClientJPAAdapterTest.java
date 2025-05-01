package br.com.ruanperondi.webhooks.adapters.persistence;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookClientJPAEntity;
import br.com.ruanperondi.webhooks.adapters.persistence.mapper.WebhookClientMapper;
import br.com.ruanperondi.webhooks.adapters.persistence.repository.WebhookClientRepository;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

class WebhookClientJPAAdapterTest {

    private WebhookClientRepository repository;
    private WebhookClientMapper mapper;
    private WebhookClientJPAAdapter adapter;

    @BeforeEach
    void setup() {
        repository = mock(WebhookClientRepository.class);
        mapper = mock(WebhookClientMapper.class);
        adapter = new WebhookClientJPAAdapter(repository, mapper);
    }

    @Test
    void shouldRetrieveClient() {
        // Given
        UUID id = UUID.randomUUID();
        WebhookClientJPAEntity entity = mock(WebhookClientJPAEntity.class);
        WebhookClient domain = mock(WebhookClient.class);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // When
        adapter.retrieve(id);

        // Then
        verify(repository).findById(id);
        verify(mapper).toDomain(entity);
    }
} 