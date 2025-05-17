
## Deployment
```bash
mvn clean package -DskipTests
docker-compose build
docker-compose up
```

## Endpoints
```
    POST /users — create user
    GET /users/{id} — get user via id
    PUT /users/{id} — update user
    DELETE /users/{id} — delete user
    POST /users/{id}/subscriptions — create subscription by user id
    GET /users/{id}/subscriptions — get subscriptions by user id
    DELETE /users/{id}/subscriptions/{sub_id} — nullify subscription
    GET /subscriptions/top — get popular subscriptions
```

## Examples
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "pass123", "name": "John", "surname": "Doe"}'
```
