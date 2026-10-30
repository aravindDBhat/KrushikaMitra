# Krushika Mitra

Spring Boot REST API for Krushika Mitra.

## Requirements

- Java 21
- MySQL with a `krushikaMitra` database

## Run locally

Set the database password and a private JWT signing key before starting the application. The JWT key must be at least 32 bytes; keep it private and do not commit it. The username defaults to `root`; set `DB_USERNAME` if your MySQL account uses another username.

In PowerShell:

```powershell
$env:DB_PASSWORD = "your-database-password"
$env:DB_USERNAME = "root"
$env:JWT_SECRET = "replace-with-a-private-random-string-of-at-least-32-characters"
.\mvnw.cmd spring-boot:run
```

Database credentials and the JWT signing key are read from environment variables so secrets do not need to be committed to the repository.
