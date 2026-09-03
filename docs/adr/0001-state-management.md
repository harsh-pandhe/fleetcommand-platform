# ADR 0001: State Management

## Status

Accepted

## Decision

Use Riverpod as the project's state-management solution.

## Context

The mobile application will eventually manage:
- authentication/session state
- role information
- API state
- equipment availability
- bookings
- driver/job state
- notifications

The project needs one consistent state-management approach before downstream features are implemented.

## Decision rationale

Riverpod was selected over Bloc due to the following reasons:
- **Provider-based dependency management**: Riverpod acts as a compile-safe dependency injection framework, which fits the modular architecture.
- **Asynchronous state support**: Features like FutureProvider and AsyncValue make managing backend API communication states (loading, error, data) extremely intuitive without custom state classes.
- **Low Boilerplate**: Unlike Bloc, which requires creating Event, State, and Bloc classes for each piece of state, Riverpod can define providers in single concise files.
- **Testability**: Riverpod providers can override their behavior within a ProviderContainer or ProviderScope during testing, allowing easy mock injection.
- **Modular and scalable**: Extremely suited for a modular Flutter application.

## Alternatives considered

- **Bloc**: While Bloc is robust and structures event-driven state transitions well, it was not selected because it introduces higher boilerplate (Event/State/Bloc pattern) which is over-engineered for the current scale of the pilot.

## Consequences

### Positive
- One standardized state-management pattern across all features.
- Clean and testable dependency injection mechanism out of the box.
- Future feature modules can easily consume and expose application state.

### Negative
- Contributors must learn Riverpod concepts (ConsumerWidget, ref, providers).
- The project is now committed to Riverpod as the primary state-management pattern.

## Scope

This ADR only decides the state-management approach. It does NOT implement authentication, role guards, API state, or domain state.
