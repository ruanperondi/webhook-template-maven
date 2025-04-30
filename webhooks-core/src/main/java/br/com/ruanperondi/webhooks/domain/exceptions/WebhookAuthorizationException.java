package br.com.ruanperondi.webhooks.domain.exceptions;

public class WebhookAuthorizationException extends WebhookException {
    public WebhookAuthorizationException(String error) {
        super(error);
    }

    public WebhookAuthorizationException(String error, Throwable cause) {
        super(error, cause);
    }
} 