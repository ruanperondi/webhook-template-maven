package br.com.ruanperondi.webhooks.domain.ports;

import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

import java.util.Optional;
import java.util.UUID;

public interface RetrieveClientPort {

    Optional<WebhookClient> retrieve(UUID uuid);

}
