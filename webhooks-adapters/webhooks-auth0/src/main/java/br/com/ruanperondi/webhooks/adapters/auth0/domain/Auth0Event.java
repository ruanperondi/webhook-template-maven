package br.com.ruanperondi.webhooks.adapters.auth0.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Auth0 webhook event. This class models the structure of events
 * received from Auth0 webhooks, including the event type and associated data.
 * Uses snake_case naming strategy for JSON serialization/deserialization.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class Auth0Event {

    @NotNull(message = "Event type is required")
    private Auth0EventType eventType;

    @NotNull(message = "Data is required")
    private String data;
}
