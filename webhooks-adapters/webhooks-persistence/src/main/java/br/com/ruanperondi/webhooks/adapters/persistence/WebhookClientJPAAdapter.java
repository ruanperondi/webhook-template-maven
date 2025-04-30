package br.com.ruanperondi.webhooks.adapters.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.ruanperondi.webhooks.adapters.persistence.mapper.WebhookClientMapper;
import br.com.ruanperondi.webhooks.adapters.persistence.repository.WebhookClientRepository;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;
import br.com.ruanperondi.webhooks.domain.ports.RetrieveClientPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebhookClientJPAAdapter implements RetrieveClientPort {

    private final WebhookClientRepository repository;
    private final WebhookClientMapper mapper;

    @Override
    public Optional<WebhookClient> retrieve(UUID uuid) {
        return repository.findById(uuid)
                .map(mapper::toDomain);
    }
}
