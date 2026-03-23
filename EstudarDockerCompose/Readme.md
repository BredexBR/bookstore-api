# 🐳 Estudos de Orquestração com Docker Compose

Este repositório é um laboratório de estudos focado em **Docker
Compose**, abordando a criação de ambientes **multicontainer**, redes
isoladas e **persistência de dados**.

O projeto orquestra uma stack completa para execução do **WordPress**
integrado a um banco de dados **MySQL**.

------------------------------------------------------------------------

## 🏗️ Arquitetura do Ambiente

A solução é composta por dois serviços principais que se comunicam
através de uma rede virtual privada:

### 🗄️ Serviço: `db` (MySQL 5.7.22)

-   Banco de dados configurado para compatibilidade com o plugin de
    autenticação nativa\
-   Porta local: **3308** → mapeada para **3306** (interna do container)

### 🌐 Serviço: `wordpress` (latest)

-   Aplicação PHP com volume mapeado para o diretório `./wp-app`\
-   Configuração customizada do PHP via arquivo `uploads.ini`\
-   Comunicação direta com o serviço de banco (`db`)

------------------------------------------------------------------------

## ⚠️ Nota sobre Segurança (Lab Mode)

As credenciais de acesso (usuários e senhas) foram definidas diretamente
no arquivo `docker-compose.yml`.

### 🚨 Em ambientes reais:

Utilize boas práticas de segurança, como: - Arquivos `.env` (adicionados
ao `.gitignore`) - Docker Secrets - Variáveis de ambiente seguras

------------------------------------------------------------------------

## 🚀 Comandos de Terminal

> 💡 **Nota:** No Windows, certifique-se de que o **Docker Desktop**
> esteja em execução.

### ▶️ Subir o ambiente

``` bash
docker-compose up
```

### 🔄 Subir em background (detach)

``` bash
docker-compose up -d
```

### 🛑 Parar e remover containers, redes e volumes

``` bash
docker-compose down
```

------------------------------------------------------------------------

## 📊 Monitoramento

### Listar todos os containers ativos

``` bash
docker ps
```

### Listar apenas containers deste projeto

``` bash
docker-compose ps
```

------------------------------------------------------------------------

## 🚦 Entendendo os Status Codes

Os códigos de saída dos containers indicam o resultado da execução:

-   **0** → Execução finalizada com sucesso\
-   **Diferente de 0** → O container foi encerrado devido a erro na
    aplicação ou falha na inicialização

------------------------------------------------------------------------

## 📚 Objetivo do Projeto

Este repositório tem como objetivo praticar:

-   Orquestração com Docker Compose\
-   Comunicação entre containers\
-   Persistência de dados com volumes\
-   Configuração de serviços isolados
