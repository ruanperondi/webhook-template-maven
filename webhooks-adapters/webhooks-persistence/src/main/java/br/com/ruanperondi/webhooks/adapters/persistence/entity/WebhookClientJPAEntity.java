package br.com.ruanperondi.webhooks.adapters.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a webhook client in the database.
 * This entity stores:
 * - Client identification and configuration
 * - Webhook endpoints and security settings
 * - Audit information (creation and update timestamps)
 * - Client status and metadata
 */
@Entity
@Table(name = "webhook_client")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebhookClientJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column(nullable = false, length = 30, name = "header_key")
    @NotEmpty(message = "Header Key cannot be empty")
    private String headerKey;

    @Column(nullable = false, unique = true, length = 100, name = "secret_key")
    @NotEmpty(message = "Secret Key cannot be empty")
    private String secretKey;

    @NotNull(message = "Max Age cannot be null")
    @Column(name = "max_age")
    @Positive(message = "Max Age must be a positive number")
    private Long maxAge;

    @NotEmpty(message = "Timestamp Header Key cannot be empty")
    @Column(length = 10, name = "timestamp_header_key")
    private String timestampHeaderKey;

    @NotEmpty(message = "Signature Header Key cannot be empty")
    @Column(length = 10, name = "signature_header_key")
    private String signatureHeaderKey;

    @NotEmpty(message = "Signature Separator cannot be empty")
    @Column(length = 10, name = "separator")
    private String separator;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
