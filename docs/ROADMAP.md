# FleetCommand build roadmap

Phase-wise breakdown of the V2 build plan into GitHub milestones and issues. Each phase is a milestone; each milestone is a set of small, independently assignable issues. Phases are sequential — later phases depend on entities/APIs earlier phases create — but issues *within* a phase are mostly parallelizable across backend/mobile.

Full context: [`FleetCommand_V2_Critical_Build_Plan.docx`](FleetCommand_V2_Critical_Build_Plan.docx).

## Phase 0 — Foundation & Tooling
Repo scaffold, CI, local dev environment, DB, contribution workflow. Blocks everything else.

## Phase 1 — Auth & Roles
Phone auth, `User`/`OwnerProfile`/`DriverProfile`, RBAC (Hirer/Owner/Driver/Admin), route/role guards on both sides.

## Phase 2 — Equipment Listing
`Equipment` + `EquipmentAvailability` model, owner onboarding flow, equipment CRUD API, search/list UI (map optional, must not block booking per plan §7).

## Phase 3 — Booking Core Loop
`Booking` + `BookingEvent`, the state machine (`REQUESTED → ACCEPTED → DRIVER_ASSIGNED → EN_ROUTE → ARRIVED → STARTED → COMPLETED → SETTLED`), request/accept/reject/cancel APIs and UI.

## Phase 4 — Driver & Tracking
Driver assignment, `LocationPing`, active-job location sharing, arrival/start/complete confirmation, OTP fallback.

## Phase 5 — Payments & Invoicing
`Payment` + `Invoice`, idempotent order creation, webhook handling, manual settlement fallback. Payment state stays separate from booking state (plan §16, §17).

## Phase 6 — Admin & Ops Control Center
Admin search/override APIs, dispute record + workflow, audit log, manual OTP/notification resend.

## Phase 7 — Notifications & Hardening
FCM + SMS for OTP, `Notification` delivery/audit record, driver-side offline buffering for critical events, background-location testing on real Android devices.

## Phase 8 — Pilot Readiness
Monitoring (Sentry + structured logs), economics tracking from job #1 (plan §13), device testing, go/pivot review against the success gates in plan §23.

---

## Explicitly out of scope until evidence says otherwise
Custom IoT hardware, fuel sensors, edge AI/work-vs-idle classification, multi-vertical, multi-city, blockchain, AI pricing, automated escrow. See plan §9 and §25 — Phase 2 (IoT) work does not start until the marketplace produces repeat transactions.

## How to track progress
Use the [Milestones](https://github.com/harsh-pandhe/fleetcommand-platform/milestones) view for phase-level burn-down and the `phase:N-*` / `area:*` / `priority:*` labels to filter issues. See [`CONTRIBUTING.md`](../CONTRIBUTING.md) for how to pick up work.
