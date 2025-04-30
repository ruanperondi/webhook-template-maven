package br.com.ruanperondi.webhooks.domain.usecases;

import java.util.Map;
import java.util.UUID;

public interface ProcessWebhookEvent {

    public void process(ProcessWebhookEventParam param);

    public record ProcessWebhookEventParam(UUID clientId, String payload, Map<String, Object> headers) {
    }
}
