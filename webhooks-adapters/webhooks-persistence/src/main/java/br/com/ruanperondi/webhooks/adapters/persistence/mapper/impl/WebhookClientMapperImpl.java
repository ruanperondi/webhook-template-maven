package br.com.ruanperondi.webhooks.adapters.persistence.mapper.impl;

import org.springframework.stereotype.Component;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookClientJPAEntity;
import br.com.ruanperondi.webhooks.adapters.persistence.mapper.WebhookClientMapper;
import br.com.ruanperondi.webhooks.domain.entity.WebhookClient;

@Component
public class WebhookClientMapperImpl implements WebhookClientMapper {

    @Override
    public WebhookClientJPAEntity toEntity(WebhookClient webhookClient) {
        return WebhookClientJPAEntity.builder()
                .id(webhookClient.getId())
                .name(webhookClient.getName())
                .headerKey(webhookClient.getHeaderKey())
                .secretKey(webhookClient.getSecretKey())
                .maxAge(webhookClient.getMaxAge())
                .timestampHeaderKey(webhookClient.getTimestampHeaderKey())
                .signatureHeaderKey(webhookClient.getSignatureHeaderKey())
                .separator(webhookClient.getSeparator())
                .build();
    }

    @Override
    public WebhookClient toDomain(WebhookClientJPAEntity webhookClientJPAEntity) {
        return WebhookClient.builder()
                .id(webhookClientJPAEntity.getId())
                .name(webhookClientJPAEntity.getName())
                .headerKey(webhookClientJPAEntity.getHeaderKey())
                .secretKey(webhookClientJPAEntity.getSecretKey())
                .maxAge(webhookClientJPAEntity.getMaxAge())
                .timestampHeaderKey(webhookClientJPAEntity.getTimestampHeaderKey())
                .signatureHeaderKey(webhookClientJPAEntity.getSignatureHeaderKey())
                .separator(webhookClientJPAEntity.getSeparator())
                .build();
    }
}
