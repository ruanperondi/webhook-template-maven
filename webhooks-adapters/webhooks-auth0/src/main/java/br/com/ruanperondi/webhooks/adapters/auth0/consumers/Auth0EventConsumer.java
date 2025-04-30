package br.com.ruanperondi.webhooks.adapters.auth0.consumers;

import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0Event;

public interface Auth0EventConsumer {

    void consume(Auth0Event event);

}
