package br.com.ruanperondi.webhooks.adapters.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.com.ruanperondi.webhooks.domain.exceptions.WebhookAuthorizationException;
import br.com.ruanperondi.webhooks.domain.exceptions.WebhookException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class WebhookExceptionHandler {

    @ExceptionHandler(WebhookException.class)
    public ResponseEntity<ErrorResponse> handleWebhookException(WebhookException ex, WebRequest request) {
        log.error("Webhook error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                    "WEBHOOK_ERROR",
                    ex.getMessage(),
                    request.getDescription(false)
                ));
    }

    @ExceptionHandler(WebhookAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleWebhookAuthorizationException(WebhookAuthorizationException ex, WebRequest request) {
        log.error("Webhook authorization error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                    "WEBHOOK_AUTHORIZATION_ERROR",
                    ex.getMessage(),
                    request.getDescription(false)
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                    "INTERNAL_SERVER_ERROR",
                    "An unexpected error occurred",
                    request.getDescription(false)
                ));
    }

    public record ErrorResponse(
        String code,
        String message,
        String path
    ) {}
} 