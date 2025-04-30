package br.com.ruanperondi.webhooks.domain.ports;

import java.util.Map;

import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

public interface WebhookProcessorPort {

    void process(WebhookClient client, String payload, Map<String, Object> headers);

}
