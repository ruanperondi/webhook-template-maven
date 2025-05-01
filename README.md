# Webhooks System

A modular webhook processing system built with Hexagonal Architecture principles.

## Architecture Overview

This project demonstrates the power of Hexagonal Architecture (also known as Ports and Adapters) through a webhook processing system. The architecture is designed to be:

- **Modular**: Each component is isolated and can be developed independently
- **Extensible**: New webhook providers can be easily added
- **Maintainable**: Clear separation of concerns and well-defined boundaries
- **Testable**: Each component can be tested in isolation

### Core Components

1. **webhooks-core**
   - Contains the domain logic and business rules
   - Defines the core entities and interfaces (ports)
   - Independent of any external dependencies
   - Key components:
     - `WebhookClient`: Entity representing a webhook client with authentication
     - `WebhookProcessorPort`: Interface for processing webhook events

2. **webhooks-adapters**
   - Contains implementations of the ports defined in core
   - Each adapter is a separate module:
     - `webhooks-auth0`: Auth0 webhook processor
     - `webhooks-stripe`: Stripe webhook processor
     - `webhooks-persistence`: Database adapter
     - `webhooks-web`: Web interface adapter

3. **webhooks-app**
   - Application layer that orchestrates the components
   - Handles dependency injection and configuration
   - Provides the runtime environment

## Security Features

The system includes robust security features:

- HMAC-based signature verification
- Timestamp validation to prevent replay attacks
- Configurable header keys and separators
- Customizable maximum age for webhook events

## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+
- Spring Boot 3.4.5

### Building the Project

```bash
mvn clean install
```

### Running the Application

```bash
cd webhooks-app
mvn spring-boot:run
```

## Testing

The project includes comprehensive test coverage:

```bash
# Run all tests
mvn test

# Run tests for a specific module
cd webhooks-core
mvn test
```

## Adding a New Webhook Provider

To add support for a new webhook provider:

1. Create a new module in `webhooks-adapters`
2. Implement the `WebhookProcessorPort` interface
3. Create domain models specific to the provider
4. Implement event consumers for different event types
5. Register the adapter in the application configuration

Example structure for a new provider:
```
webhooks-adapters/
  └── webhooks-newprovider/
      ├── src/
      │   └── main/
      │       └── java/
      │           └── br/com/ruanperondi/webhooks/adapters/newprovider/
      │               ├── WebhookProcessorNewProviderAdapter.java
      │               ├── domain/
      │               │   ├── NewProviderEvent.java
      │               │   └── NewProviderEventType.java
      │               └── consumers/
      │                   └── NewProviderEventConsumer.java
      └── pom.xml
```

## Best Practices

1. **Domain Isolation**
   - Keep domain logic in the core module
   - Avoid external dependencies in the core
   - Use interfaces (ports) to define boundaries

2. **Adapter Implementation**
   - Each adapter should be focused on a single responsibility
   - Handle provider-specific logic in the adapter
   - Implement proper error handling and logging

3. **Security**
   - Always validate webhook signatures
   - Implement timestamp validation
   - Use secure configuration management

4. **Testing**
   - Write unit tests for core logic
   - Test adapters in isolation
   - Include integration tests for end-to-end flows

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 