# content-service

Reference data for the Verdant garden site: **plants**, **journal entries**, **gallery images**, and **services** (cooking, delivery, workshops, consulting). Read-heavy, edited rarely, cached aggressively.

Bilingual (English + Vietnamese) content is stored as side-by-side columns and returned in both languages on every response — the frontend's `loc` pipe picks the right one at render time.

---

## Run

```bash
./gradlew bootRun
```

Server listens on **`http://localhost:8082`**. On first startup the `DataSeeder` populates the H2 database with the same 9 plants, 4 journal entries, 9 gallery images, and 5 services that the frontend ships with.

The H2 console is at **`http://localhost:8082/h2`** (JDBC URL `jdbc:h2:file:./data/content-dev`, user `sa`, no password).

---

## Endpoints

All `GET` endpoints are public. All write endpoints require a Bearer JWT with role `ADMIN`.

| Method | Path                       | Notes |
|--------|----------------------------|-------|
| GET    | `/api/plants`              | `?category=`, `?search=`, `?tag=` |
| GET    | `/api/plants/{id}`         |       |
| POST   | `/api/plants`              | admin |
| PUT    | `/api/plants/{id}`         | admin |
| DELETE | `/api/plants/{id}`         | admin |
| GET    | `/api/journal`             | `?season=` |
| GET    | `/api/journal/{id}`        |       |
| POST/PUT/DELETE | `/api/journal[/{id}]` | admin |
| GET    | `/api/gallery`             | `?season=` |
| GET    | `/api/gallery/{id}`        |       |
| POST/PUT/DELETE | `/api/gallery[/{id}]` | admin |
| GET    | `/api/services`            | `?category=` |
| GET    | `/api/services/{id}`       |       |
| POST/PUT/DELETE | `/api/services[/{id}]` | admin |
| GET    | `/api/stats`               | aggregate counts for the dashboard |
| GET    | `/actuator/health`         |       |

---

## Auth

content-service does **not** issue tokens — it just verifies them. In production they come from `identity-service`.

For local development, `DevTokenPrinter` logs a freshly-signed admin JWT (valid 7 days) at startup. Look for the banner in the console output and copy the token. Disable in prod by setting `app.jwt.dev-print-token=false` (already off in `application-prod.yml`).

```bash
# After grabbing the token from the console:
curl -X POST http://localhost:8082/api/plants \
  -H "Authorization: Bearer eyJhbGc..." \
  -H "Content-Type: application/json" \
  -d '{"commonName":"Rosemary","latinName":"Salvia rosmarinus", ...}'
```

JWTs are validated against `app.jwt.secret` (HS256, ≥32 bytes) and the `app.jwt.issuer` claim. Roles come from the `roles` claim — either an array or space-separated string. `ADMIN` is automatically prefixed with `ROLE_`.

---

## Profiles

| Profile | Database                       | When |
|---------|--------------------------------|------|
| `dev`   | H2 file (`./data/content-dev`) | default — `./gradlew bootRun` |
| `test`  | H2 in-memory                   | `./gradlew test` |
| `prod`  | PostgreSQL                     | `SPRING_PROFILES_ACTIVE=prod` |

Production env vars: `DATABASE_URL`, `DATABASE_USER`, `DATABASE_PASSWORD`, `JWT_SECRET`.

---

## Caching

Caffeine, in-process. Read endpoints are cached per query-string; writes evict the affected entity + the corresponding list cache + (for plants and journal) the `stats` cache. Inspect at `/actuator/caches`.

When you migrate to multiple instances, swap `spring.cache.type=caffeine` for `redis` and add `spring-boot-starter-data-redis` — the `@Cacheable` annotations don't change.

---

## What's intentionally **not** here

- Comments / likes — those live in `engagement-service`.
- Shop items (today's harvest stand) — `storefront-service`.
- Auth / users — `identity-service`.
- Contact form & newsletter — `inbox-service`.

This service is the boring CRUD core. The interesting stuff lives elsewhere.
