# Exemplo de CI com Profiles do Maven

Este repositório é um exemplo de como integrar um projeto com **Travis CI** e **Heroku**.

## Heroku

- Crie uma conta no Heroku
- Adicione a dependência do webrunner do Heroku ao `pom.xml` no profile de testes/homologação

```xml
<!-- Plugin para o webrunner embarcado do Heroku -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>2.3</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals><goal>copy</goal></goals>
            <configuration>
                <artifactItems>
                    <artifactItem>
                        <groupId>com.github.jsimone</groupId>
                        <artifactId>webapp-runner</artifactId>
                        <version>8.0.30.2</version>
                        <destFileName>webapp-runner.jar</destFileName>
                    </artifactItem>
                </artifactItems>
            </configuration>
        </execution>
    </executions>
</plugin>
```

- Adicionar um arquivo `Procfile` (sem extensão)

```yaml
web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
```

- Configure o <activation> do profile com a seguinte configuração

```xml
<activation>
  <property>
    <name>env.DYNO</name>
  </property>
</activation>
```
Isso fará com que o profile seja ativado ao ser construído dentro do Heroku.

- Crie um novo app

[![Adicionando novo app](http://imgur.com/2ftNeeF.png)](#)

- Defina um nome se quiser, caso contrário pode clicar apenas em "**Create App**"
- Adicione um novo Add-on para criar uma instância do PostgreSQL

[![Adicionando add-on do PostgreSQL](http://i.imgur.com/VtYSCHU.png)](#)

- Configure os arquivos de propriedade e o filtro para definir o PostgreSQL do Heroku nos arquivos de propriedade ao construir com o profile de testes/homologação
- Faça `git push` das alterações

## Travis CI

- Criar conta no [Travis CI](https://travis-ci.org/)
- Adicionar o `.travis.yml`

```yaml
language: java
jdk:
  + oraclejdk8
branches:
  only:
    <BRANCH_TESTE>
install: mvn install -U -DskipTests=true
script:
  mvn integration-test verify -Ptest
deploy:
  provider: heroku
  api_key:
    secure: "<API_KEY>" # API Key, encontrado em Account Settings
  on: <BRANCH_TESTE> # Configuração de branch
  app: <SUA_APP> # Nome da app no Heroku
```

Algumas explicações sobre as propriedades:

- `branches`: Especifica quais branches deverão ser observadas ao identificar um pull request. As branches declaradas nessa propriedades serão as únicas a serem construídas

- `deploy`: Configuração do servidor para deploy, uma vez que o build tenha sido concluído com sucesso.
    + `api_key`: Essa chave é encontrada dentro da sua conta do Heroku, em **Account Settings**.
    + `on`: Branch para fazer deploy
    + `app`: Nome da sua aplicação

- Ao terminar essa configuração, faça commit e push do arquivo `.travis.yml` para o seu repositório, dentro da branch que você definiu para ser construída pelo Travis CI.
- Dentro do ambiente do Travis CI, clique em `Add New Repository`, um ícone de (+) ao lado de `My Repositories`
- Escolha o seu repositório
- Espere o build
