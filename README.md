# FleetCommand

Managed marketplace for construction equipment rental. 2-person team, Flutter + Spring Boot, one city pilot.

> V2 is not simply a longer V1. It changes the operating model: FleetCommand is a managed marketplace first, software product second, and IoT platform later.

Full plan: [`docs/FleetCommand_V2_Critical_Build_Plan.docx`](docs/FleetCommand_V2_Critical_Build_Plan.docx)

## Non-negotiable launch constraints

- **Vertical:** construction equipment only, one primary category first
- **Geography:** one city + adjacent industrial belt
- **Supply:** 10–20 verified machines before demand push
- **Demand:** 5–10 active pilot hirers
- **Pilot:** founder-assisted, not self-service
- **Timeline:** 14–16 weeks with buffer
- **Success:** repeat transactions + positive economics, not app completeness

## Architecture

| Layer | Choice |
|---|---|
| Frontend | Flutter mobile + responsive web where practical, one codebase |
| State | Riverpod or Bloc — pick one, freeze |
| Routing | go_router with role guards |
| Backend | Spring Boot 3 (Java 21), modular monolith |
| Database | PostgreSQL |
| Cache | Redis, only where justified |
| Realtime | Polling first, WebSockets are P1 |
| Notifications | FCM + SMS for critical OTP |
| Storage | S3-compatible |
| CI/CD | GitHub Actions |
| Monitoring | Sentry + structured logs |

## Repo layout

```
mobile/    Flutter app (lib/app, lib/core, lib/features)
backend/   Spring Boot modular monolith (Maven)
docs/      Build plan and product docs
```

## Booking state machine

```
REQUESTED → ACCEPTED → DRIVER_ASSIGNED → EN_ROUTE → ARRIVED → STARTED → COMPLETED → SETTLED
```

Payment state is tracked separately from booking state — a job can be completed while payment is pending or disputed.

## MVP scope (P0)

Phone auth, role-based access (Hirer/Owner/Driver/Admin), equipment onboarding + search, booking request/accept/cancel, driver assignment, active-job location sharing, arrival/start/complete confirmation, notifications, invoice record, payment status recording, admin control center + audit history.

Explicitly deferred to P1: rich maps/route preview, automated geofence arrival, WebSockets, ratings, automated settlement, owner earnings dashboard. If a P1 feature threatens the pilot date, it loses — founders do it manually instead.

Explicitly excluded from V2: custom IoT hardware, fuel sensors, edge AI, multi-vertical/multi-city, blockchain, AI pricing, full offline-first sync, escrow.

## Getting started

### Backend
```bash
cd backend
./mvnw spring-boot:run
```

### Mobile
```bash
cd mobile
flutter pub get
flutter run
```
