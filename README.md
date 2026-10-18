# FakeStoreAPI Test Automation

API test automation for [fakestoreapi.com](https://fakestoreapi.com) — REST Assured + TestNG using a **service-object** design, covering Cart CRUD, authentication, response **schema** validation, a **data-driven** test, and a **contract/snapshot** test.

## Framework choice & why

| Choice | Why |
|---|---|
| **Java 11 + Maven** | Consistent with the team's other frameworks; easy CI. |
| **REST Assured 5.4** | Fluent request/response API, Jackson (de)serialization, and a JSON-schema matcher in one library. |
| **TestNG 7** | Groups, data providers, listeners, and built-in parallelism. |
| **Service-object pattern** | `BaseService` holds the request spec + helpers; `CartService` / `AuthService` / `ProductService` expose intent-revealing methods, keeping endpoints out of tests. |
| **Builder POJOs** | Readable request construction (`CartRequest`, `CartProduct`, `AuthRequest`); response POJOs via `response.as(...)`. |
| **json-schema-validator** | `matchesJsonSchemaInClasspath(...)` validates responses against committed schemas. |
| **Log4j2 + LoggingFilter** | Every request/response is logged via a REST Assured `Filter`. |
| **ExtentReports 5** | Self-contained HTML report (`report.html`) with per-test steps. |

## What is covered

- **Cart CRUD (positive):** `POST` (201, new id), `GET /carts/{id}` & `GET /carts`, `PUT` (200), `DELETE` (200).
- **Negative / edge:** non-numeric id → **400**; missing cart → **200 + null**; payload not validated by the mock (documented).
- **Authentication:** valid login → **201 + token**; invalid → **401**.
- **Schema validation:** cart, product and auth responses validated against `schemas/*.json`.
- **Data-driven:** the create-cart scenario runs over 5 product IDs (`@DataProvider`).
- **Contract / snapshot (senior bonus):** `schemas/cart-schema.json` is a committed snapshot of the cart shape; tests assert live responses still conform, so field drift fails the build.

## How to run

```bash
mvn test                      # runs suite.xml (parallel)
mvn test -Dsuite=suite        # explicit suite selection
mvn -o test                   # fully offline build (deps from local ~/.m2 cache)
```


## Reporting & artifacts

- `report.html` — ExtentReports (open in a browser).
- `logs/api-automation.log` — Log4j2 run log (every request/response via `LoggingFilter`).
- `target/surefire-reports/` — TestNG/Surefire results.

## Parallel execution

`suite.xml` runs `parallel="methods"` with `thread-count="4"`. REST Assured calls are stateless and each service is instantiated per test method, so parallelism is safe.

## Extension plan

**Parallelisation**
- Raise `thread-count`; split suites (smoke vs regression) selected via `-Dsuite`.
- Run the suite against multiple environments with a CI matrix (base URI per env).

**Reporting**
- Publish `report.html` to GitHub Pages per run; add Allure for history/trends.
- Notify Slack/Teams on failure from CI.

## Project structure

```
fakestoreapi-test-automation/
├── suite.xml                         # TestNG suite (parallel)
└── src/test/
    ├── java/com/api/
    │   ├── base/        BaseService + CartService / AuthService / ProductService
    │   ├── models/      request (builders) + response POJOs
    │   ├── filters/     LoggingFilter (Log4j2 req/resp)
    │   ├── listeners/   TestListener (Log4j2 + Extent)
    │   ├── utility/     ConfigReader, LoggerUtility, ExtentReporterUtility
    │   ├── constants/   Endpoints
    │   ├── dataproviders/ ProductDataProvider
    │   └── tests/       CRUD / Negative / Auth / Schema / DataDriven / ContractSnapshot
    └── resources/
        ├── config.properties
        ├── log4j2.xml
        └── schemas/     cart-schema.json, product-schema.json, auth-schema.json
```
