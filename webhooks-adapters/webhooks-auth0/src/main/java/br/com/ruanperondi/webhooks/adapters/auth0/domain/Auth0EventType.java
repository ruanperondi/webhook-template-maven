package br.com.ruanperondi.webhooks.adapters.auth0.domain;

/**
 * Enumeration of all possible Auth0 event types. Each value represents a
 * specific event that can be received from Auth0 webhooks. Used for routing
 * events to appropriate consumers and validating incoming events.
 */
public enum Auth0EventType {
    SIGNUP,
    LOGIN,
    LOGOUT,
    USER_CREATION,
    USER_DELETION,
    USER_UPDATE,
    USER_BLOCK,
    USER_UNBLOCK;
}
