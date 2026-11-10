# Project Management integration contracts

Project Management owns `Project` and `MonitoringPoint`. Its REST API exposes project and monitoring point IDs. References to other bounded contexts remain scalar IDs in the domain and database; no JPA entity relationship crosses a bounded context boundary.

## Current behavior

- Creating a project does not check subscription quotas. The create request and `Project` model do not contain an account or organization ID.
- Assigning a responsible user checks that `responsibleUserId` is positive, but does not check that the user exists or belongs to the project account.
- A monitoring point can store an optional positive `normativeThresholdId`, but does not check the referenced threshold in Environmental Monitoring.
- Project locations are stored as text and monitoring point coordinates are stored as latitude and longitude. The backend does not call a geolocation service.
- Project search matches names. The model has no project code, and cross-context search is outside this module.

## Agreements needed before further integration

### Subscription Management: project quota

Agree on the source of `accountId` for project creation, whether archived projects count toward the quota, and the operation Project Management can call to check the active limit. The contract should return an explicit allowed or denied result and a reason that can be mapped to a documented HTTP response. Quota validation belongs in the project command flow before saving a new project. Project Management must not read Subscription Management tables or JPA entities directly.

### Identity & Access Management: responsible user

Agree whether assignment must verify that the user exists, is active, and belongs to the same account as the project. If required, define a lookup by `userId` that returns only the facts needed for that decision. The project stores only `responsibleUserId`.

### Environmental Monitoring: normative threshold

Agree whether a monitoring point may omit a threshold, whether a selected threshold must be active, and whether it must match the point's monitored variable. If validation is required, define a lookup by `normativeThresholdId`. The monitoring point stores only that ID.

### Maps and global search

Agree whether geolocation is a frontend concern or requires a backend adapter and which map service supplies it. For global search, agree on the owner of aggregation across bounded contexts and whether projects need a separate code. Project Management can supply its own project query without reading other contexts' repositories.

## Verification still needed

Run the application against the team's MySQL database and verify project creation, monitoring point creation, the `monitoring_points.project_id` foreign key, paging, search, and `/swagger-ui/index.html`. The automated REST tests use mocked application services and do not exercise MySQL.
