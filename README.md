# Tasks API (Spring Boot) + CI/CD

CRUD-приложение для управления задачами с поддержкой Docker и CI/CD pipeline для pull request.

## Стек

- Java 17
- Spring Boot 3
- Maven
- PostgreSQL
- Docker / Docker Compose
- GitHub Actions

## Локальный запуск

### 1) Запуск базы данных

Из корня репозитория:

```bash
docker compose up -d db
```

### 2) Сборка и запуск приложения

```bash
cd tasks
mvn clean package
java -jar target/tasks-0.0.1-SNAPSHOT.jar
```

Для Windows (PowerShell):

```powershell
cd tasks
mvn clean package
java -jar target\tasks-0.0.1-SNAPSHOT.jar
```

## Линтер

Конфигурация линтера находится в `tasks/checkstyle.xml`.

Проверка:

```bash
cd tasks
mvn checkstyle:check
```

Пайплайн падает, если Checkstyle находит ошибки.

## Тесты и coverage

Тесты находятся в `tasks/src/test/java`.

Запуск тестов и coverage:

```bash
cd tasks
mvn clean verify
```

Используется JaCoCo:

- HTML-отчёт: `tasks/target/site/jacoco/index.html`
- бинарный файл: `tasks/target/jacoco.exec`

Порог покрытия: **50%** (`jacoco:check` в `pom.xml`).
Если покрытие ниже, pipeline падает.

## Docker

Сборка образа:

```bash
docker build -t tasks-app:local ./tasks
```

Запуск контейнера:

```bash
docker run --rm -p 8080:8080 tasks-app:local
```

## CI/CD (Pull Request)

Workflow: `.github/workflows/ci-cd.yml`

Запускается на каждый `pull_request` и содержит job:

1. `build` — проверка сборки приложения
2. `lint` — запуск checkstyle
3. `test` — тесты + проверка coverage >= 50% + публикация артефактов JaCoCo
4. `docker_build` — сборка Docker-образа
5. `docker_push` — push образа в Docker Hub (только если предыдущие job успешны и PR не из fork)

Тег образа в Docker Hub:

- `pr-<номер_PR>-<короткий_sha>`

## Секреты для Docker Hub

Для job `docker_push` нужно добавить secrets в GitHub repository:

- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

Секреты не хардкодятся в репозитории.