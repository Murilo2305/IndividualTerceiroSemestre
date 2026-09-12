# LeaveANote

Este projeto consiste em um sistema para guardar notas voltado para faculdades. Após se registrar e fazer login, o usuário pode criar, editar e deletar notas à vontade.

## Execução

O LeaveANote funciona com duas partes trabalhando juntas: uma aplicação em JavaScript e uma API em Java. Para fazer o programa funcionar, basta realizar duas coisas: iniciar a API na porta 8080 (não importa se você faz isso por meio de um arquivo `.jar` ou simplesmente executando-a dentro da sua IDE) e iniciar o servidor da página em JavaScript (recomendo usar a extensão Live Server do VS Code).

Com isso, você será capaz de executar o programa. Todos os arquivos de configuração já vêm prontos.

## Tecnologias

Neste projeto foram utilizadas:

1. Spring Web
2. JDBC Template
3. Banco de dados H2
4. MySQL
5. JavaScript
6. CSS

## Rotas da API

A API possui dois artefatos: `users` e `notes`.

### Rotas de Users

1. **GET /users**: retorna todos os usuários.
2. **POST /users/login**: realiza as verificações da requisição para retornar os dados de login.
3. **POST /users/**: permite realizar a inserção direta de um usuário.
4. **PUT /users/id**: permite alterar os dados de um usuário.
5. **DELETE /users**: permite deletar um usuário.

### Rotas de Notes

1. **GET /notes**: retorna todas as notas.
2. **GET /notes/users/id**: retorna todas as notas de um usuário específico.
3. **POST /notes/**: permite realizar a inserção direta de uma nota.
4. **PUT /notes/id**: permite alterar os dados de uma nota.
5. **DELETE /notes**: permite deletar uma nota.

É necessário destacar que muitas dessas rotas não são utilizadas ativamente no sistema e servem principalmente para testes.

## BD

O banco de dados H2 é salvo localmente e possui as tabelas:

# Database Structure

## User Table

```sql
CREATE TABLE IF NOT EXISTS `user` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL,
    senha VARCHAR(50) NOT NULL,
    birth DATE,
    `function` VARCHAR(50),
    genre VARCHAR(50),
    recieveEmails BOOLEAN
);

CREATE TABLE IF NOT EXISTS note (
    id INT PRIMARY KEY AUTO_INCREMENT,
    msg VARCHAR(1000) NOT NULL,
    fkUser INT,
    CONSTRAINT fkUserNote
        FOREIGN KEY (fkUser)
        REFERENCES `user` (id)
)
```

Esse banco é definido em `schema.sql`, e as propriedades do banco H2 são definidas em `application.properties`.
