package br.com.ruanperondi.webhooks.domain.usecases.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ruanperondi.webhooks.domain.exceptions.WebhookException;
import br.com.ruanperondi.webhooks.domain.ports.RetrieveClientPort;
import br.com.ruanperondi.webhooks.domain.ports.WebhookProcessorPort;
import br.com.ruanperondi.webhooks.domain.usecases.ProcessWebhookEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessWebhookEventImpl implements ProcessWebhookEvent {

    private final RetrieveClientPort retrieveClient;
    private final List<WebhookProcessorPort> webhookProcessors;

    @Override
    public void process(ProcessWebhookEventParam param) {
        if (param.clientId() == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }

        log.info("Processing webhook for client: {}. Retrieving client", param.clientId());
        var client = retrieveClient.retrieve(param.clientId()).orElseThrow(() -> new WebhookException("Client not found"));

        log.info("Validating signature for client: {}. Validating signature", param.clientId());
        client.ensureRequestIsAuthentic(param.headers(), param.payload());

        log.info("Processing webhook for client: {}. Processing webhook", param.clientId());
        webhookProcessors.forEach(
                port -> port.process(client, param.payload(), param.headers())
        );
    }
}
