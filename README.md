# Sistema de Envio de Newsletter

Este projeto é um sistema backend desenvolvido para gerenciar o cadastro de clientes, o envio de e-mails de newsletters e o gerenciamento de notícias. Ele oferece uma API web para o cadastro de clientes e notícias, além de disparar e-mails diários com as notícias cadastradas que ainda não foram processadas.

## Funcionalidades

- Cadastro de Clientes: Permite o cadastro de novos clientes para o envio de newsletters.
- Cadastro de Notícias: Permite o cadastro de notícias que serão enviadas aos clientes.
- Envio de E-mails: Dispara e-mails diários para os clientes cadastrados, contendo as notícias não processadas.
- Marcação de Notícias Processadas: Após o envio da newsletter, as notícias são marcadas como processadas para que não sejam reenviadas.
- Mensagens de Aniversário: Inclui mensagens personalizadas de aniversário nos e-mails dos clientes quando aplicável.

## Requisitos
- Versão do Quarkus Utilizado: 3.16.2
- Código compilado para o Java versão: 17
- Versão do Maven Usada: 3.9.9
- JUnit: 5.11.3 (para testes)
    - Mockito: 5.14.2 (mocking)

## Estrutura do projeto
- /src/main: Código-fonte principal.
    - controller: Contém os controladores da API para gerenciar as requisições.
    - service: Lógica de negócios, incluindo o envio de e-mails.
    - repository: Acesso ao banco de dados para clientes e notícias.
    - model: Classes de domínio como Cliente e Noticia.
