package br.com.ruanperondi.webhooks.adapters.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookEventJPAEntity;

public interface WebhookEventRepository extends JpaRepository<WebhookEventJPAEntity, UUID> {

}
