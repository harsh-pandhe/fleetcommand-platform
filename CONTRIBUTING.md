# Contributing to FleetCommand

FleetCommand is built in small, independently mergeable pieces so multiple people can work in parallel without blocking each other. Read this before opening a PR.

## How work is organized

- **Milestones** = phases (Phase 0 Foundation → Phase 8 Pilot Readiness). See the [roadmap](docs/ROADMAP.md) for what's in each phase and why the order is fixed.
- **Issues** = the actual units of work. Every issue is scoped to be doable in one PR, roughly half a day to two days of work.
- **Labels** tell you what an issue touches:
  - `area:backend` / `area:mobile` / `area:infra` / `area:docs` — which part of the codebase
  - `phase:N-name` — which milestone it belongs to
  - `priority:p0` / `priority:p1` — P0 is required for pilot launch, P1 is deferrable (see the build plan's P0/P1 split)
  - `good first issue` — self-contained, minimal context needed

## Picking up work

1. Find an open, unassigned issue that matches your area. Prefer issues in the earliest open phase — later phases depend on earlier ones (e.g. you can't build Booking before Auth exists).
2. Comment "taking this" and assign yourself. If it's unclear or you want to change the approach, ask in the issue first — don't silently reinterpret scope.
3. Branch from `main`: `git checkout -b <type>/<issue-number>-<short-slug>` (e.g. `feat/12-equipment-crud-api`, `fix/18-booking-state-guard`).
4. Keep the PR to the scope of the issue. If you find unrelated problems while working, file a new issue instead of expanding the PR.

## PR requirements

- Link the issue (`Closes #12`).
- CI must pass (`backend` and/or `mobile` job depending on what you touched).
- Keep diffs small — if a PR is touching both `backend/` and `mobile/` for unrelated reasons, split it.
- Follow existing package/feature structure — new backend logic goes in the relevant module under `backend/src/main/java/com/fleetcommand/backend/`, new mobile logic goes under the matching `mobile/lib/features/` folder. Don't introduce new top-level structure without discussion.
- At least one review required before merge (see `CODEOWNERS`). Founders own final merge decisions.
- No secrets, API keys, or `.env` files in commits.

## Commit style

Conventional Commits (`feat:`, `fix:`, `refactor:`, `docs:`, `test:`, `chore:`), imperative mood, one logical change per commit. Body explains *why* when it isn't obvious from the diff.

## Architecture guardrails (don't relitigate these in a PR)

These were decided in the [build plan](docs/FleetCommand_V2_Critical_Build_Plan.docx) — raise a discussion issue if you think one is wrong, don't just change it in a feature PR:

- Backend is **one Spring Boot modular monolith** — no new services/microservices.
- Mobile is **one Flutter codebase** — no separate web app.
- **Booking state and payment state are separate** — never conflate "completed" with "paid."
- Realtime is **polling first**; WebSockets are P1.
- No IoT/telemetry work until Phase 8 evidence says the marketplace has repeat transactions — Phase 2 (IoT) issues do not exist yet on purpose.

## Local setup

### Backend
```bash
cd backend
./mvnw spring-boot:run
```
Requires PostgreSQL running locally (see `docs/ROADMAP.md` Phase 0 for docker-compose setup once it lands).

### Mobile
```bash
cd mobile
flutter pub get
flutter run
```

## Questions

Open a `question`-labeled issue rather than asking in a PR — keeps discussion discoverable for the next person hitting the same thing.
