# ADR 0001: Use Riverpod for mobile state management

**Status:** Accepted (2026-08-23)

## Decision

FleetCommand mobile uses **Riverpod** (`flutter_riverpod`) as its single state-management and dependency-injection approach. The application root is wrapped with `ProviderScope` in `mobile/lib/app/app.dart`.

`go_router` is the single routing mechanism. The initial router includes independent placeholder role roots for Hirer, Owner, Driver, and Admin. Authentication and role-based redirects are intentionally not part of this decision and will be added by the auth work.

## Consequences

- New mobile features expose state through Riverpod providers/notifiers; do not introduce Bloc, Provider, Redux, or another app-wide state framework.
- Route guards will be implemented through `go_router` once authentication state exists.
- The current role navigation is a development-only placeholder, not an authorization mechanism.
