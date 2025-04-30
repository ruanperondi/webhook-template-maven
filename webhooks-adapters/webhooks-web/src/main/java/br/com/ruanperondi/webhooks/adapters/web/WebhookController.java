package br.com.ruanperondi.webhooks.adapters.web;

import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ruanperondi.webhooks.domain.usecases.ProcessWebhookEvent;
import br.com.ruanperondi.webhooks.domain.usecases.ProcessWebhookEvent.ProcessWebhookEventParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final ProcessWebhookEvent processWebhookEvent;

    @PostMapping("/{clientId}")
    public void processWebhook(@PathVariable String clientId, @RequestBody String payload, @RequestHeader Map<String, Object> headers) {
        processWebhookEvent.process(new ProcessWebhookEventParam(UUID.fromString(clientId), payload, headers));
    }
}
