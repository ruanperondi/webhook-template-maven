package br.com.ruanperondi.webhooks.adapters.auth0.consumers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

import br.com.ruanperondi.webhooks.adapters.auth0.domain.Auth0EventType;

/**
 * Annotation used to bind Auth0EventConsumer implementations to specific event
 * types. This annotation is used for dependency injection and routing of events
 * to the appropriate consumer. It extends Spring's @Qualifier to enable
 * type-safe event routing.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Auth0EventBind {

    Auth0EventType value();
}
