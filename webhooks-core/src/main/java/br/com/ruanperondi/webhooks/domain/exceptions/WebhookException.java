package br.com.ruanperondi.webhooks.domain.exceptions;

public class WebhookException extends RuntimeException {
    public WebhookException(String error) {
        super(error);
    }

    public WebhookException(String error, Throwable cause) {
        super(error, cause);
    }
}
