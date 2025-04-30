package br.com.ruanperondi.webhooks.domain.usecases.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;
import br.com.ruanperondi.webhooks.domain.exceptions.WebhookException;
import br.com.ruanperondi.webhooks.domain.ports.RetrieveClientPort;
import br.com.ruanperondi.webhooks.domain.ports.WebhookProcessorPort;
import br.com.ruanperondi.webhooks.domain.usecases.ProcessWebhookEvent.ProcessWebhookEventParam;

class ProcessWebhookEventImplTest {

    private final RetrieveClientPort retrieveClient = mock(RetrieveClientPort.class);
    private final WebhookProcessorPort processor1 = mock(WebhookProcessorPort.class);
    private final WebhookProcessorPort processor2 = mock(WebhookProcessorPort.class);
    private final WebhookClient client = mock(WebhookClient.class);

    private ProcessWebhookEventImpl service;
    private UUID clientId;
    private Map<String, Object> headers;
    private String payload;
    private ProcessWebhookEventParam validParam;

    @BeforeEach
    void setUp() {
        service = new ProcessWebhookEventImpl(retrieveClient, List.of(processor1, processor2));

        clientId = UUID.randomUUID();
        headers = Map.of("X-Signature", "t=123,v1=abc");
        payload = "{\"event\":\"test\"}";
        validParam = new ProcessWebhookEventParam(clientId, payload, headers);

        when(retrieveClient.retrieve(clientId)).thenReturn(Optional.of(client));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when clientId is null")
    void shouldThrowWhenClientIdIsNull() {
        var param = new ProcessWebhookEventParam(null, payload, headers);

        assertThatThrownBy(() -> service.process(param))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Client ID cannot be null");
    }

    @Test
    @DisplayName("Should throw WebhookException when client is not found")
    void shouldThrowWhenClientNotFound() {
        when(retrieveClient.retrieve(clientId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.process(validParam))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Client not found");
    }

    @Test
    @DisplayName("Should throw WebhookException when signature is invalid")
    void shouldThrowWhenSignatureIsInvalid() {
        doThrow(new WebhookException("Signature verification failed"))
                .when(client).ensureRequestIsAuthentic(headers, payload);

        assertThatThrownBy(() -> service.process(validParam))
                .isInstanceOf(WebhookException.class)
                .hasMessageContaining("Signature verification failed");
    }

    @Test
    @DisplayName("Should process webhook successfully")
    void shouldProcessWebhookSuccessfully() {
        assertThatCode(() -> service.process(validParam))
                .doesNotThrowAnyException();

        verify(client).ensureRequestIsAuthentic(headers, payload);
        verify(processor1).process(client, payload, headers);
        verify(processor2).process(client, payload, headers);
    }
}
