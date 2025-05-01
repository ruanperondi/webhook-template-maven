package br.com.ruanperondi.webhooks.adapters.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookEventJPAEntity;

/**
 * JPA repository for webhook events.
 * Provides standard CRUD operations and custom queries for webhook event entities.
 * Extends JpaRepository to leverage Spring Data JPA functionality.
 */
@Repository
public interface WebhookEventRepository extends JpaRepository<WebhookEventJPAEntity, UUID> {

}
