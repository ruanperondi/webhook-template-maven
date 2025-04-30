package br.com.ruanperondi.webhooks.adapters.persistence.mapper;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookClientJPAEntity;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

public interface WebhookClientMapper {

    public WebhookClientJPAEntity toEntity(WebhookClient webhookClient);

    public WebhookClient toDomain(WebhookClientJPAEntity webhookClientJPAEntity);

}
