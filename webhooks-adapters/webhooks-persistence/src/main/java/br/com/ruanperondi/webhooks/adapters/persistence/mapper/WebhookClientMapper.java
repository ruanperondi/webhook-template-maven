package br.com.ruanperondi.webhooks.adapters.persistence.mapper;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookClientJPAEntity;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

/**
 * Mapper interface for converting between WebhookClient domain entities and JPA entities.
 * Defines methods for bidirectional mapping between domain and persistence layers.
 */
public interface WebhookClientMapper {

    public WebhookClientJPAEntity toEntity(WebhookClient webhookClient);

    public WebhookClient toDomain(WebhookClientJPAEntity webhookClientJPAEntity);

}
