# API RESTful para Sistema de Usuários de Carros 

Criar aplicação que exponha uma API RESTful de criação de usuários e carros com login. 

### Estórias de Usuário 

* **EST-01** - Criar e mapear entidades Usuário e Carro
* **EST-02** - Criar rotas para a entidade Usuário
* **EST-03** - Criar rotas para a entidade Carro
* **EST-04** - Configurar autenticação e geração de token com JWT
* **EST-05** - Implementar validações
* **EST-06** - Criar testes unitários
* **EST-07** - Fazer deploy da API no Render
* **EST-08** - Adicionar Swagger
* **EST-09** - Ajustes e correções

### Solução

* Optei por deixar o back e o front em repositórios separados, como boa prática, para facilitar a organização e versionamento dos mesmos.
* Para estruturar a aplicação dividi nas seguintes camadas:
  *  Controller: Classes que recebem as requisições
  *  Domain: Classes que definem os modelos e DTOs
  *  Repository: Classes responsáveis por acessar o banco de dados
  *  Security: Classes de configuração referente a segurança da aplicação
  *  Services: Classes que tratam a regra de negócio

#### O que foi entregue?
  * JWT como token :white_check_mark:
  * Servidor deve estar embutido na aplicação (utilizei o Tomcat) :white_check_mark:
  * Processo de build via Maven :white_check_mark:
  * Banco de dados em memória (utilizei o H2) :white_check_mark:
  * Framework Spring (utilizei a versão 3) :white_check_mark:
  * Utilizar no mínimo Java 8 (utilizei a versão 17) :white_check_mark:
  * Persistência com JPA/Hibernate :white_check_mark:
  * Disponibilizar a API rodando em algum host (utilizei o Render, através de uma imagem docker da aplicação) :white_check_mark:
  * Criar um repositório público em alguma ferramenta de git (utilizei o Github) :white_check_mark:
  * O desenvolvimento deve simular uma mini-sprint (Scrum), dividindo o desafio em estórias de usuário :white_check_mark:
  * O README.md do projeto deverá ter uma seção ESTÓRIAS DE USUÁRIO com a lista numerada de estórias de usuário que foram concebidas para a implementação do desafio. :white_check_mark:
  * A primeira linha de cada commit do repositório deve utilizar a descrição da estória de usuário associada. :white_check_mark:
  * O README.md do projeto deve ser claro e mostrar tudo que precisa ser feito para executar o build do projeto, deploy, testes, etc. :white_check_mark:

Desejáveis

  * Senha criptografada :white_check_mark:
  * Swagger :white_check_mark:
    
#### O que faltou?
  * Testes unitários :x:
  * Implementar o endpoint /api/me contendo as informações do usuário logado :x:

#### O que pode melhorar?
  * O front faltou ser mais completo, ficou pronta apenas a parte referente ao usuário, faltando login a tratativa para os carros 
  * Algumas validações de erros 

### Como executar o projeto? 

#### [BACKEND] Via arquivo .jar
  * Ter o maven instalado a partir da versão 3.8.6
  * Ter a versão 17 do Java instalada
  * Ir até a pasta /target e executar o comando a seguir:
    ```
    java -jar carros-api-1.0-SNAPSHOT.jar
    ```
  * A aplicação irá iniciar localmente na porta 8009. Acesse: **http://localhost:8009/swagger-ui/index.html** para obter a documentação Swagger e executar chamadas para as rotas.

#### [BACKEND] Via servidor do Render
  * Apenas acesse: [Cars API - Render](https://cars-api-latest.onrender.com/swagger-ui/index.html)
  * OBS: Pode demorar alguns minutos para o servidor iniciar

#### [FRONTEND]
  * Ter o NPM instalado a partir da versão 9.7.2
  * Ter o Node instalado a partir da versão 20.3.1
  * Instalar o Angular CLI
  ```
  npm install -g @angular/cli
  ```
  * Executar o projeto
  ```
  ng serve
  ``` 
