# Sistema de Envio de Newsletter

Este projeto é um sistema backend desenvolvido para gerenciar o cadastro de clientes, o envio de e-mails de newsletters e o gerenciamento de notícias. Ele oferece uma API web para o cadastro de clientes e notícias, além de disparar e-mails diários com as notícias cadastradas que ainda não foram processadas.

## Funcionalidades

- Cadastro de Clientes: Permite o cadastro de novos clientes para o envio de newsletters.
- Cadastro de Notícias: Permite o cadastro de notícias que serão enviadas aos clientes.
- Envio de E-mails: Dispara e-mails diários para os clientes cadastrados, contendo as notícias não processadas.
- Marcação de Notícias Processadas: Após o envio da newsletter, as notícias são marcadas como processadas para que não sejam reenviadas.

## Requisitos
- Versão do Quarkus Utilizado: 3.16.2
- Código compilado para o Java versão: 17
- Versão do Maven Usada: 3.9.9
