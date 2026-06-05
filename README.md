# Fire Shield SMS MS

Microsservico responsavel por consumir ocorrencias de incendio enriquecidas com dados de endereco, persistir as informacoes e enviar SMS para o contato do orgao responsavel de acordo com o estado da ocorrencia.

Este modulo faz parte do projeto academico **Fire Shield**, composto por dois microsservicos Java:

- `fire-shield-localidades-ms`: consome ocorrencias brutas de satelite, enriquece as coordenadas com endereco e publica a ocorrencia enriquecida.
- `fire-shield-sms-ms`: consome ocorrencias enriquecidas, gerencia contatos de orgaos responsaveis e envia SMS.

## Fluxo Geral

1. O `fire-shield-localidades-ms` publica uma ocorrencia enriquecida em uma fila RabbitMQ.
2. O `fire-shield-sms-ms` consome essa mensagem pela fila configurada em `RABBITMQ_QUEUE_ORIGIN`.
3. A ocorrencia e persistida no banco.
4. O servico busca o primeiro contato cadastrado para o estado da ocorrencia.
5. Uma mensagem SMS e criada, persistida e enviada pelo adapter de SMS.
6. A API REST permite gerenciar os contatos dos orgaos responsaveis.
7. O servidor MCP expoe tools de consulta para ocorrencias, contatos e SMS.

## Arquitetura e Tecnologias

Tecnologias principais:

- Java 21
- Spring Boot 3.5.x
- Arquitetura Hexagonal
- Spring Web
- Spring Security com JWT
- Spring AMQP / RabbitMQ
- Spring Data JPA
- Flyway
- Oracle Database
- H2 para testes
- Twilio SDK
- Spring AI MCP Server WebMVC
- JJWT
- Lombok
- Maven Wrapper

O modulo segue arquitetura hexagonal:

- `application/core`: entidades de dominio, enums e excecoes.
- `application/ports/in`: casos de uso expostos para entrada.
- `application/ports/out`: contratos de saida, como repositorios, SMS, token e senha.
- `application/usecases`: implementacoes dos casos de uso.
- `adapter/in`: entrada via REST, RabbitMQ e MCP.
- `adapter/out`: saidas para banco, SMS e seguranca.
- `framework/config`: configuracoes Spring que montam controllers, adapters, use cases, listener e seguranca.

Essa organizacao evita que o dominio dependa diretamente de Spring, JPA, RabbitMQ, Twilio ou JWT.

## Funcionalidades

### API REST

Login publico:

```http
POST /api/v1/auth/login
```

CRUD de contatos protegido por JWT:

```http
POST   /api/v1/contatos
GET    /api/v1/contatos
GET    /api/v1/contatos/{uuid}
PUT    /api/v1/contatos/{uuid}
DELETE /api/v1/contatos/{uuid}
```

Exemplo de login:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Use o token retornado nos endpoints protegidos:

```http
Authorization: Bearer <access_token>
```

### MCP

O servico tambem expoe um servidor MCP via Spring AI, usando transporte Streamable HTTP.

Endpoint:

```text
http://localhost:8080/mcp
```

Tools registradas:

- `list-events`: lista ocorrencias de incendio.
- `list-contacts`: lista contatos cadastrados.
- `find-contact-by-state`: busca o primeiro contato pelo estado.
- `list-sms`: lista mensagens SMS persistidas.

Para usar com o MCP Inspector via Docker:

```powershell
docker compose up inspector
```

Abra a URL completa gerada no log do Inspector. No Inspector, configure:

```text
Transport Type: Streamable HTTP
URL: http://host.docker.internal:8080/mcp
```

Se usar o Inspector via `npx` fora do Docker:

```powershell
npx -y @modelcontextprotocol/inspector
```

URL do MCP:

```text
http://localhost:8080/mcp
```

## Variaveis de Ambiente

Configure as variaveis abaixo antes de executar localmente:

| Variavel | Descricao | Exemplo |
| --- | --- | --- |
| `DB_URL` | JDBC URL do banco Oracle | `jdbc:oracle:thin:@localhost:1521/FREEPDB1` |
| `DB_USER` | Usuario do banco | `fire_shield` |
| `DB_PASSWORD` | Senha do banco | `fire_shield` |
| `JWT_SECRET` | Chave usada para assinar tokens JWT | `fire-shield-sms-ms-local-secret-32-chars` |
| `RABBITMQ_HOST` | Host do RabbitMQ | `localhost` |
| `RABBITMQ_PORT` | Porta do RabbitMQ | `5671` ou `5672` |
| `RABBITMQ_USER` | Usuario do RabbitMQ | `guest` |
| `RABBITMQ_PASSWORD` | Senha do RabbitMQ | `guest` |
| `RABBITMQ_VHOST` | Virtual host do RabbitMQ | `/` |
| `RABBITMQ_QUEUE_ORIGIN` | Fila de entrada com ocorrencias enriquecidas | `loc.ocorrencia.queue` |
| `SMS_SID` | Account SID da Twilio | `AC...` |
| `SMS_TOKEN` | Auth token da Twilio | `...` |
| `SMS_NUMERO` | Numero remetente da Twilio | `+5511999999999` |

Propriedades MCP ja configuradas no `application.properties`:

```properties
spring.ai.mcp.server.name=mcp-fire-shield-sms
spring.ai.mcp.server.version=0.0.1
spring.ai.mcp.server.type=SYNC
spring.ai.mcp.server.protocol=STREAMABLE
```

Observacao: o `application.properties` atual usa SSL para RabbitMQ (`spring.rabbitmq.ssl.enabled=true`), pensando em CloudAMQP. Para RabbitMQ local sem SSL, ajuste a propriedade para `false` em um perfil local ou sobrescreva via configuracao.

## Executando Localmente

Entre no diretorio do microsservico:

```powershell
cd fire-shield-sms-ms
```

Configure as variaveis no PowerShell:

```powershell
$env:DB_URL="jdbc:oracle:thin:@localhost:1521/FREEPDB1"
$env:DB_USER="fire_shield"
$env:DB_PASSWORD="fire_shield"
$env:JWT_SECRET="fire-shield-sms-ms-local-secret-32-chars"
$env:RABBITMQ_HOST="localhost"
$env:RABBITMQ_PORT="5672"
$env:RABBITMQ_USER="guest"
$env:RABBITMQ_PASSWORD="guest"
$env:RABBITMQ_VHOST="/"
$env:RABBITMQ_QUEUE_ORIGIN="loc.ocorrencia.queue"
$env:SMS_SID="ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
$env:SMS_TOKEN="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
$env:SMS_NUMERO="+5511999999999"
```

Execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Por padrao, o servico sobe em:

```text
http://localhost:8080
```

## Banco e Usuario Base

As migrations criam as tabelas principais, contatos iniciais e um usuario base:

```text
username: admin
password: admin123
```

A senha e armazenada com hash BCrypt.

## Testes

Para rodar os testes:

```powershell
.\mvnw.cmd test
```

Os testes usam H2 em memoria, propriedades dummy para Twilio e listener RabbitMQ desativado.

## Integracao com o Localidades MS

O `fire-shield-sms-ms` deve consumir a fila onde o `fire-shield-localidades-ms` publica ocorrencias enriquecidas.

Exemplo:

```text
fire-shield-localidades-ms:
RABBITMQ_QUEUE_DESTINY=loc.ocorrencia.queue

fire-shield-sms-ms:
RABBITMQ_QUEUE_ORIGIN=loc.ocorrencia.queue
```

Com isso, o fluxo completo fica:

```text
Satelite/Fila bruta -> Localidades MS -> Fila enriquecida -> SMS MS -> SMS ao orgao responsavel
```

