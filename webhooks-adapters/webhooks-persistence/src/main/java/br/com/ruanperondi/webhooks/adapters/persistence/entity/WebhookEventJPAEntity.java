package br.com.ruanperondi.webhooks.adapters.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "webhook_event")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class WebhookEventJPAEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 10000)
    private String payload;

    @Column(length = 10000)
    private String headers;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private UUID clientId;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
