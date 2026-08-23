# Backend

## API error responses

All REST API failures use the following JSON envelope:

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Validation failed",
  "details": {
    "phone": "must not be blank"
  },
  "timestamp": "2026-08-23T12:00:00Z"
}
```

`details` contains field-to-message pairs for validation failures and is an empty object for other errors. The public messages are safe to show to clients; internal exception details are never included in the response.

| Status | Code | Meaning |
| --- | --- | --- |
| 400 | `VALIDATION_ERROR` | Request validation failed |
| 403 | `ACCESS_DENIED` | The caller is not allowed to perform the action |
| 404 | `NOT_FOUND` | The requested resource does not exist |
| 500 | `INTERNAL_SERVER_ERROR` | An unexpected server error occurred |
