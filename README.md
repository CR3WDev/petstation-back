# PetStation

Sistema Simples de Gerenciamento de Pets.

## Índice

- [Requisitos do Sistema](#requisitos-do-sistema)
- [Tecnologias Back-end](#tecnologias-back-end)
- [Como Rodar o Back-end](#como-rodar-o-back-end)
- [Links](#links)

## Requisitos do Sistema

- Sistema de CRUD Paginados e com Filtragem (Categoria e Animais)
- Documentação via Swagger:
  - Local: [Swagger Local](http://localhost:8080/swagger-ui/index.html)
  - Produção: [Swagger Produção](http://ec2-34-210-253-95.us-west-2.compute.amazonaws.com:8080/swagger-ui/index.html)
- Requisição de Edição de Status de Animal
- Deploy feito na AWS usando EC2 no back-end e S3 no front-end

## Tecnologias Back-end

- Java 17+
- Maven
- PostgreSQL
- JPA
- Spring Boot

## Como Rodar o Back-end

1. Clone o repositório:
    ```bash
    git clone https://github.com/CR3WDev/petstation-back.git
    ```
2. Configure o banco de dados local no arquivo `application-local.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/petstation
    spring.datasource.username=seu-usuario
    spring.datasource.password=sua-senha
    ```
    Além do Banco configure o path que você deseja salvar as imagens da aplicação ou apenas crie um arquivo petstation-files no seu disco c:
    ```properties
     ## Image Storage Properties
    file.upload-dir=c:/petstation-files/
    ```
3. Execute o Maven para baixar as dependências e compilar o projeto:
    ```bash
    mvn clean install
    ```
4. Escolha o profile que você deseja executar. Caso não tenha profile no seu editor, basta configurar a variável `mode` com o profile que você desejar, por exemplo, `mode=local` ou `mode=prod`. Caso nenhum profile seja selecionado, ele executa o de produção.
   - **local**: Caso você escolha o profile local, deve ser criado o banco `petstation`.
   - **prod**: Caso você escolha o profile prod, basta dar run no projeto.
5. Inicie a aplicação:
    ```bash
    mvn spring-boot:run
    ```

## Links

- Link do front-end: [PetStation Front-end](http://petstation.s3-website-us-west-2.amazonaws.com)
- Link do back-end: [PetStation Back-end](http://ec2-34-210-253-95.us-west-2.compute.amazonaws.com:8080)
