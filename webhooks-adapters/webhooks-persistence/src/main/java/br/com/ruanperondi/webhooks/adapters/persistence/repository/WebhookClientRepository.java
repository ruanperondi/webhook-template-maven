package br.com.ruanperondi.webhooks.adapters.persistence.repository;

import br.com.ruanperondi.webhooks.adapters.persistence.entity.WebhookClientJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WebhookClientRepository extends JpaRepository<WebhookClientJPAEntity, UUID> {

}
