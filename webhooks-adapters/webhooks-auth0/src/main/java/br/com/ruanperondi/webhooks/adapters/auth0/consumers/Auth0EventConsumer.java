package br.com.ruanperondi.webhooks.adapters.auth0.consumers;

import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0Event;

/**
 * Interface for Auth0 event consumers. Implementations of this interface handle
 * specific types of Auth0 events. Each consumer is responsible for processing a
 * particular event type and executing the appropriate business logic.
 */
public interface Auth0EventConsumer {

    void consume(Auth0Event event);

}
