# Desafio UGTSIC SESAP/RN

Este documento fornece instruções detalhadas para configurar e rodar o projeto.

---

## Requisitos

Antes de iniciar, certifique-se de ter os seguintes itens instalados:

- **Java JDK 17** ou superior
- **Apache Maven**
- **MySQL** (Servidor de banco de dados)
- **Git** (para clonar o repositório)

---

## Passo a Passo para Executar o Projeto

### 1. Clonar o Repositório

Baixe o código-fonte do projeto para a sua máquina:

```bash
# Clonando o repositório
git clone https://github.com/opedro500/UGTSIC-SESAP-RN-Challenge

# Navegue até o diretório do projeto
cd UGTSIC-SESAP-RN-Challenge
```

### 2. Configurar o Banco de Dados MySQL

1. Acesse o MySQL utilizando seu cliente preferido (CLI ou ferramenta gráfica).

2. Crie um schema chamado `projeto_sesap`:

```sql
CREATE SCHEMA projeto_sesap;
```

3. Certifique-se de que tem um usuário do MySQL configurado com permissões adequadas para este schema.

---

### 3. Configurar o Arquivo `application.properties`

Abra o arquivo de configuração do Spring localizado em `src/main/resources/application.properties` e ajuste os seguintes parâmetros para o seu ambiente:

```application.properties

# Configurações do banco de dados

spring.datasource.username=(Substitua pelo seu usuário do MySQL)
spring.datasource.password=(Substitua pela sua senha do MySQL)

```

### 4. Compilar e Rodar o Projeto

Use o Maven para compilar e executar o projeto:

```bash
# Limpar e construir o projeto
mvn clean install

# Executar o projeto
mvn spring-boot:run
```

Se tudo estiver configurado corretamente, a aplicação estará disponível em:

```
http://localhost:8080
```

---

### 5. Conexão com o Banco de Dados

- Certifique-se de que o projeto conseguiu se conectar ao banco de dados `projeto_sesap`.
- Certifique-se de que a porta 3306 (padrão do MySQL) esteja acessível no seu ambiente.

---

**Autor:** Pedro José Cavalcanti Cabral
