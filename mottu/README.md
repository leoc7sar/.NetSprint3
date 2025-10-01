# mottu (PT-BR)

Aplicação web (Spring Boot 3 + Thymeleaf + Flyway + Spring Security) para gestão de entregadores, motos, locações e pagamentos.

## Executar local
1. PostgreSQL (Docker):
```bash
docker run --name mottu-pg -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=mottu -p 5432:5432 -d postgres:16
```
2. Variáveis e execução:
```bash
export DB_URL=jdbc:postgresql://localhost:5432/mottu
export DB_USER=postgres
export DB_PASS=postgres
mvn spring-boot:run
```
3. Acesse http://localhost:8080  
   - admin: `admin` / `123`  
   - usuário: `usuario` / `123`
