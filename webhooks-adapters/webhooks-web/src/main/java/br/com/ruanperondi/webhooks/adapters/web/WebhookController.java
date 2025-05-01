package br.com.ruanperondi.webhooks.adapters.web;

import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ruanperondi.webhooks.domain.usecases.ProcessWebhookEvent;
import br.com.ruanperondi.webhooks.domain.usecases.ProcessWebhookEvent.ProcessWebhookEventParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Webhooks", description = "API for webhook processing")
@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final ProcessWebhookEvent processWebhookEvent;

    @Operation(
            summary = "Process a webhook",
            description = "Endpoint to receive and process webhook events from different providers. "
            + "The request must include a signature header in the format: 't={timestamp};v1={signature}' "
            + "where timestamp is the current Unix timestamp and signature is the HMAC-SHA256 of the "
            + "concatenation of timestamp and payload."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Webhook processed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{clientId}")
    public void processWebhook(
            @Parameter(description = "Client ID that will receive the webhook", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String clientId,
            @Parameter(description = "Webhook payload in JSON format")
            @RequestBody String payload,
            @Parameter(
                name = "x-webhook-signature",
                description = "Custom security header used by specific clients. Format: 't={timestamp};v1={signature}'",
                in = ParameterIn.HEADER,
                required = false,
                example = "t=1714521600;v1=def456"
            )
            @RequestHeader(name = "x-webhook-signature", required = false) String xSecurity,
            @RequestHeader Map<String, Object> headers
    ) {
        processWebhookEvent.process(new ProcessWebhookEventParam(UUID.fromString(clientId), payload, headers));
    }
}
