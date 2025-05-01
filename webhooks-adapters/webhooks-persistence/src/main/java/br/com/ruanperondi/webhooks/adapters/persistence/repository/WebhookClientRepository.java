package br.com.ruanperondi.webhooks.adapters.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookClientJPAEntity;

/**
 * JPA repository for webhook clients.
 * Provides standard CRUD operations and custom queries for webhook client entities.
 * Extends JpaRepository to leverage Spring Data JPA functionality.
 */
@Repository
public interface WebhookClientRepository extends JpaRepository<WebhookClientJPAEntity, UUID> {

}
