package br.com.ruanperondi.webhooks.adapters.auth0;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ruanperondi.webhooks.adapters.auth0.consumers.Auth0EventConsumer;
import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0Event;
import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0EventType;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

class WebhookProcessorAuth0AdapterTest {

    private ObjectMapper mapper;
    private Map<Auth0EventType, Auth0EventConsumer> consumers;
    private Auth0EventConsumer consumer;
    private WebhookProcessorAuth0Adapter adapter;
    private WebhookClient client;
    private String payload;
    private Map<String, Object> headers;
    private Auth0Event event;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mapper = mock(ObjectMapper.class);
        consumers = new HashMap<>();
        consumer = mock(Auth0EventConsumer.class);
        adapter = new WebhookProcessorAuth0Adapter(mapper, consumers);

        client = new WebhookClient(
                UUID.randomUUID(),
                "Test Client",
                "http://test.com",
                "secret",
                1L,
                "http://test.com",
                "http://test.com",
                "http://test.com"
        );
        payload = "{\"type\":\"user.created\"}";
        headers = Map.of();
        event = new Auth0Event();
        event.setEventType(Auth0EventType.USER_CREATION);
        event.setData("{\"user_id\":\"123\"}");

        when(mapper.readValue(payload, Auth0Event.class)).thenReturn(event);
        consumers.put(Auth0EventType.USER_CREATION, consumer);
    }

    @Test
    @DisplayName("Should process event successfully when consumer exists")
    void shouldProcessEventSuccessfully() throws JsonProcessingException {
        adapter.process(client, payload, headers);
        verify(consumer).consume(event);
    }

    @Test
    @DisplayName("Should not process event when consumer does not exist")
    void shouldNotProcessEventWhenConsumerDoesNotExist() throws JsonProcessingException {
        consumers.remove(Auth0EventType.USER_CREATION);
        adapter.process(client, payload, headers);
        verify(consumer, never()).consume(any());
    }

    @Test
    @DisplayName("Should handle JsonProcessingException gracefully")
    void shouldHandleJsonProcessingException() throws JsonProcessingException {
        when(mapper.readValue(payload, Auth0Event.class)).thenThrow(new JsonProcessingException("Invalid JSON") {
        });
        adapter.process(client, payload, headers);
        verify(consumer, never()).consume(any());
    }
}
