# UGTSIC - Sistema de Cadastramento de Currículos

Este é um sistema de cadastramento de currículos desenvolvido em Java com Spring Boot. A aplicação permite o registro de candidatos e envia automaticamente um e-mail de confirmação para o endereço cadastrado.

O ambiente de desenvolvimento é totalmente baseado em contêineres, dispensando a instalação local de dependências como Java, Maven ou MySQL.

## Pré-requisitos

* [Docker Desktop](https://www.docker.com/products/docker-desktop) instalado e em execução.
* [Git](https://git-scm.com/) instalado na sua máquina.

## Como rodar o projeto localmente

### 1. Clonando o Repositório

Abra o seu terminal, faça o clone do projeto e entre na pasta raiz:

```bash
git clone https://github.com/opedro500/UGTSIC-SESAP-RN-Challenge
cd UGTSIC-SESAP-RN-Challenge
```

### 2. Configuração do Ambiente

Antes de iniciar a aplicação, é necessário configurar as variáveis de ambiente referentes ao banco de dados e ao servidor de e-mail.

1. Na raiz do projeto, faça uma cópia do arquivo `.env.example` e renomeie-a para `.env`.
2. Abra o arquivo `.env` e preencha as variáveis com os seus dados:

```env
# Configurações da Base de Dados
MYSQL_ROOT_PASSWORD=sua_senha_aqui
MYSQL_DATABASE=projeto_sesap

# Configurações de E-mail
SPRING_MAIL_USERNAME=ugtsicsesaprnprojeto@gmail.com
SPRING_MAIL_PASSWORD=insira_a_senha_de_app_aqui
```

> **Nota:** Para que o envio de e-mails funcione corretamente, utilize uma Senha de Aplicativo do Google correspondente à conta informada, ou solicite as credenciais ao administrador do repositório.

### 3. Iniciando a Aplicação

Ainda no terminal, na pasta raiz do projeto, execute:

```bash
docker-compose up --build
```

O Docker fará o download das imagens, compilará o código, inicializará o banco de dados e subirá a aplicação Spring Boot. Quando os logs no terminal indicarem que o Spring iniciou, acesse a aplicação no navegador através do endereço:
**http://localhost:8080**

---

## Acesso ao Banco de Dados

O banco de dados e as tabelas são criados automaticamente na inicialização da aplicação. Para visualizar ou manipular os dados salvos, escolha uma das opções abaixo:

### Opção A: Linha de Comando do Docker
Como o banco já está rodando no Docker, você pode acessá-lo diretamente pelo terminal sem instalar nada:

1. Abra uma nova janela de terminal.
2. Liste os contêineres ativos para encontrar o nome exato do serviço de banco de dados:
   ```bash
   docker ps
   ```
3. Acesse o MySQL dentro do contêiner (substitua `<nome_do_container>` pelo nome listado no passo anterior):
   ```bash
   docker exec -it <nome_do_container> mysql -u root -p
   ```
4. Insira a senha definida no `.env` e execute suas consultas SQL:
   ```sql
   USE projeto_sesap;
   SELECT * FROM candidates;
   ```

### Opção B: Interface Gráfica (DBeaver, MySQL Workbench)
Se preferir usar um software de gerenciamento de banco de dados, crie uma nova conexão MySQL com os seguintes parâmetros:
* **Host:** `localhost`
* **Port:** `3309` *(Porta externa mapeada pelo Docker)*
* **Username:** `root`
* **Default Schema:** `projeto_sesap`
* **Password:** *(A senha definida no seu arquivo .env)*

## Como encerrar a aplicação

Para parar a execução dos contêineres, pressione `Ctrl + C` no terminal em execução.

Para remover os contêineres e a rede criada de forma limpa (os dados do banco continuarão salvos no volume local), execute:
```bash
docker-compose down
```

---
**Desenvolvido por Pedro Cavalcanti** 📧 Contato: pedropjc500@gmail.com
