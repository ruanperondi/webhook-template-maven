package br.com.ruanperondi.webhooks.adapters.auth0.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class Auth0Event {

    @NotNull(message = "Event type is required")
    private Auth0EventType eventType;

    @NotNull(message = "Data is required")
    private String data;
}
