# desafio-backend
Desafio backend Luiza Labs

## Requisitos
```sh
Java 11
Apache Maven 4.0.0
IntelliJ IDEA
Docker
```

## Swagger

```sh
http://localhost:8080/swagger-ui/
```

## Variaveis de Ambiente
| Nome | Descrição | Valor Padrão |
|------|:---------:|-------------:|
| APPLICATION_APP |  application name | `Desafio-Backend` |
| APPLICATION_VERSION |  application version | `1.0.0` |
| APPLICATION_CLIENT_PRODUCT_URL |  product api url | `http://challenge-api.luizalabs.com/api` |
| APPLICATION_CLIENT_PRODUCT_TIMEOUT |  product api timeot | `10000` |
| SPRING_DATASOURCE_HOST |  postgres database host | `localhost` |
| SPRING_DATASOURCE_PORT |  postgres database port | `5432` |
| SPRING_DATASOURCE_DBNAME |  postgres database name | `desafio` |
| SPRING_DATASOURCE_USERNAME |  postgres database username | `desafio` |
| SPRING_DATASOURCE_PASSWORD |  postgres database password | `desafio` |
| SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE |  max pool size | `10` |
| SPRING_FLYWAY_ENABLED |  flyway enabled | `true` |

## Execução

```sh
mvn spring-boot:run

Antes de executar a aplicação, execute o comando para subir o banco de dados:
docker-compose up -d
Se precisar baixar o banco, execute o comando abaixo:
docker-compose down
```

## Testes

```sh
mvn clean install test verify

Depois de executar o comando acima, acesse o caminho pasta-projeto/target/site/jacoco/index.html para verificar a 
cobertura dos testes.
```

