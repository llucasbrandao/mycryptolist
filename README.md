# My Crypto List API

My Crypto List API é uma simples API implementada com Spring Boot 2.7.0 e o banco NoSQL Redis.

Sua função é buscar informações de criptomoedas no serviço [Coin Paprika](https://coinpaprica.com).

<br>

## Debaixo do capô

* Spring Boot 2.7.0;
* Redis;
* OpenAPI v3 (Swagger);

<br>

## Como executar

Para rodar a API, basta ter o [docker-compose](https://docs.docker.com/compose/) instalado em sua máquina. Se for o caso, vá para a pasta [docker](./docker): <br>

```bash
cd docker/ && docker-compose up -d
```

__Remova o argumento "-d" do comando acima, se quiser ver os logs do console.__

<br>

### Sem docker-compose

É só configurar as variáveis de ambiente no arquivo [configs.env](./env/configs.env). Após, você pode usar a imagem mais recente do repositório [llucasbrandao/mycryptolistapi](https://hub.docker.com/r/llucasbrandao/mycryptolistapi/tags) e executá-la com o Docker padrão.
<br>

_Lembre-se de que é necessário especificar um banco de dados Redis._

<br>

### Sem docker

Para a execução tradicional, é necessário configurar as várias de ambiente no arquivo [configs.env](./env/configs.env), ou no [application-prod.yml](./src/main/resources/application-prod.yml).
<br>

Com isso, você pode compilar a aplicação usando o Maven:

```bash
cd pasta_do_projeto/ && ./mvnw -Pprod clean install -Dmaven.test.skip=true
```
Depois que a compilação for concluída, inicie a API:

```bash
java -jar -Dspring.profiles.active=prod && target/mycriptolist-0.0.1-SNAPSHOT.jar
```
_Lembre-se de que é necessário especificar um banco de dados Redis._

<br>

## Endpoints
Todos os endpoints podem ser consultados através do OpenAPI v3 do projeto. <br> __Depois que ele estiver online__, vá para [Swagger](http://localhost:8080/docs/ui).

<br>
Os endpoints abertos são:

* [POST] - /login
* [POST - /api/users/newUser

<br>

### Cadastro
Ao cadastrar um usuário, é preciso preencher apenas o name, email, username e password.
```json
[POST - /api/users/newUser

{ 
  "name": "",
  "email": "",
  "loginDetails": {
    "username": "",
    "password": ""
  }
}
```
<br>

### Login
O login deve ser feito enviando-se o username e password para o endpoint de login:

```json
[POST] - /login

{
    "username": "",
    "password": ""
}
```

Se os dados forem válidos, um bearer token será devolvido no header da response. É com ele que você deverá se autenticar nos outros endpoints.