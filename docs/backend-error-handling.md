# FleetCommand Backend Error Handling

The backend implements a centralized error handling strategy utilizing `@RestControllerAdvice`. All API error responses return a consistent error envelope to ensure the Flutter/mobile client can handle failures uniformly.

## Standard Error Response Contract

All errors returned by the API conform to the following JSON structure:

```json
{
  "code": "ERROR_CODE",
  "message": "User-friendly description of the error",
  "details": {
    "field_name": "Specific error details (e.g., validation constraint description)"
  },
  "timestamp": "2026-08-28T10:30:00Z"
}
```

### Response Fields

* **`code`** (String): A machine-readable code categorizing the failure.
* **`message`** (String): A generic, human-readable summary of the error.
* **`details`** (Object/Null): A key-value map providing extra context. For validation errors, keys represent input fields and values represent constraint failure descriptions. For other errors, it is `null`.
* **`timestamp`** (String): Server-generated ISO-8601 string of the exact date and time the error payload was constructed.

---

## Mapped Error Codes & Statuses

| Exception Source | HTTP Status | Code | Message | Details Description |
| :--- | :--- | :--- | :--- | :--- |
| `MethodArgumentNotValidException`<br>`ConstraintViolationException` | **400 Bad Request** | `VALIDATION_ERROR` | `Request validation failed` | Map of fields to their specific violation messages. |
| `EntityNotFoundException`<br>`NoSuchElementException` | **404 Not Found** | `NOT_FOUND` | `Resource not found` | `null` |
| `AccessDeniedException` | **403 Forbidden** | `ACCESS_DENIED` | `Access denied` | `null` |
| Any unhandled `Exception` | **500 Internal Error** | `INTERNAL_ERROR` | `An unexpected error occurred` | `null` |

---

## Information Security Guardrails

To prevent the leakage of sensitive architecture and deployment properties, the global exception handler strictly filters responses before returning them to clients:
* **Hiding Internals**: The `INTERNAL_ERROR` envelope hides internal details (e.g., stack traces, SQL syntax/query exceptions, database structure details, Java class/package names, filesystem paths).
* **Logging**: Diagnostic details are still logged server-side at appropriate log levels (`ERROR` for 500s, `WARN`/`INFO` for client errors) to assist in server troubleshooting.
