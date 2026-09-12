# FrontEnd

Esse é o front end do sistema, a parte com a qual o usuário interage. Ele possui dois segmentos: os formulários e a área das notas.

## Formulários

Existem dois formulários que podem ser alternados na tela pelo botão de toggle: cadastro e login.

### Cadastro

Aqui são coletados:

* Nome
* E-mail
* Senha
* Data de nascimento
* Gênero
* Função (Aluno ou Professor)
* Opção de receber ou não receber e-mails

Caso os inputs passem pelas verificações, será realizada uma requisição POST para `/users`, e o usuário será registrado.

### Login

Aqui são recebidos:

* E-mail
* Senha

É então realizada uma chamada POST para `/users/login` e, caso os dados sejam encontrados, o login é realizado. Após isso, o botão de adicionar notas surge.

## Área das notas

Aqui é onde o usuário interage com a principal funcionalidade do sistema. Ao clicar no botão de gerar nota, uma nota vazia surge nessa área. A nota começa com um espaço vazio para escrita e um botão de deletar.

Ao escrever, um botão de atualização aparecerá ao lado do botão de deletar. Ao clicar nele, as alterações na nota serão realizadas. Da mesma forma, ao clicar no botão de deletar, o usuário poderá excluir a nota.

Tudo isso é integrado ao backend e utiliza as rotas disponibilizadas pela API.
