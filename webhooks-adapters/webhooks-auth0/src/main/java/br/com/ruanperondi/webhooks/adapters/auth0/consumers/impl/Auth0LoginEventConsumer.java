package br.com.ruanperondi.webhooks.adapters.auth0.consumers.impl;

import br.com.ruanperondi.webhooks.adapters.auth0.consumers.Auth0EventBind;
import br.com.ruanperondi.webhooks.adapters.auth0.consumers.Auth0EventConsumer;
import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0Event;
import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0EventType;
import lombok.extern.slf4j.Slf4j;

@Auth0EventBind(Auth0EventType.LOGIN)
@Slf4j
public class Auth0LoginEventConsumer implements Auth0EventConsumer {

    @Override
    public void consume(Auth0Event event) {
        //TODO: Implement the logic to consume the login event
    }
}
