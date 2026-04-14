# 📚 Bookstore API

[![Continuous Integration and Delivery with Github Actions](https://github.com/BredexBR/bookstore-api/actions/workflows/continuous-deployment.yml/badge.svg?branch=main)](https://github.com/BredexBR/bookstore-api/actions/workflows/continuous-deployment.yml)

RESTful API para gerenciamento de uma livraria, desenvolvida com Spring Boot.
O projeto implementa boas práticas de desenvolvimento backend seguindo os princípios do **Glory of REST**, incluindo versionamento de API, testes automatizados e documentação com Swagger.

A API permite o gerenciamento completo de livros, incluindo operações de criação, consulta, atualização e remoção.

---

## Índice

- [Person](#person)
- [Book](#book)
- [Logs no Spring Boot](#logs-no-spring-boot)
- [Padrão DTO com Dozer Mapper](#padrão-dto-com-dozer-mapper)
- [Migrações com Flyway](#migrações-com-flyway)
- [Content Negotiation](#content-negotiation)
- [HATEOAS](#hateoas)
- [Testes unitários com Mockito e JUnit 5](#testes-unitários-com-mockito-e-junit-5)
- [Testes de Integração](#testes-de-integração)
- [Documentação com Swagger (SpringDoc OpenAPI)](#documentação-com-swagger-springdoc-openapi)
- [CORS](#cors)
- [Query Params e Busca Paginada](#query-params-e-busca-paginada)
- [Upload e Download de Arquivos](#upload-e-download-de-arquivos)
- [Importação e Exportação de Planilhas Excel e CSV](#importação-e-exportação-de-planilhas-excel-e-csv)
- [O que é um Mock?](#o-que-é-um-mock)
- [Spring Boot Initializr](#spring-boot-initializr)
- [Geração de Mocks com Mockaroo](#geração-de-mocks-com-mockaroo)
- [Dependências utilizadas](#dependências-utilizadas)
- [Pré-requisitos](#pré-requisitos)

## Person

Este projeto implementa serviços HTTP (`POST`, `GET`, `DELETE` e `PUT`) utilizando Java com Spring Boot. Inicialmente, os dados eram simulados com [mocks](#o-que-é-um-mock), mas o projeto foi evoluído para realizar operações reais de CRUD utilizando um banco de dados MySQL com JPA (Java Persistence API).

### FindByID

Retorna uma pessoa de acordo com o seu ID:

<br>

![Exemplo FindByID](imgs/06-findById.png)

### FindAll

Retorna todas as pessoas presentes:

<br>

![Exemplo FindAll](imgs/06-findAll.png)

### Create

Cria uma pessoa:

<br>

![Exemplo Create](imgs/06-create.png)

### Update

Atualiza os dados de uma pessoa:

<br>

![Exemplo Update](imgs/06-update.png)

### Delete

Deleta os dados de uma pessoa:

<br>

![Exemplo Delete](imgs/06-Delete.png)

## Book

Este projeto também implementa serviços HTTP (`POST`, `GET`, `DELETE` e `PUT`) para a entidade **Book**, utilizando Java com Spring Boot. Assim como na entidade [Person](#person), realiza CRUD com um banco de dados MySQL utilizando JPA (Java Persistence API).

## Logs no Spring Boot

O projeto também utiliza logs para registrar o comportamento da aplicação durante a execução, facilitando o processo de depuração e monitoramento.

Para isso, foi utilizada a biblioteca **SLF4J** (Simple Logging Facade for Java), que fornece uma interface para diversas implementações de logging.

### Níveis de log disponíveis:

- `TRACE` – nível mais detalhado, geralmente usado para rastreamento fino
- `DEBUG` – informações úteis para desenvolvedores durante a depuração
- `INFO` – mensagens informativas sobre o fluxo normal da aplicação
- `WARN` – indica situações de alerta que não interrompem o sistema
- `ERROR` – indica falhas ou erros que exigem atenção

> Os logs ajudam a manter o controle do que está acontecendo na aplicação sem a necessidade de usar `System.out.println()`.

## Padrão DTO com Dozer Mapper

O projeto também adota o padrão **DTO (Data Transfer Object)** para separar a camada de apresentação dos modelos de entidade. Isso promove maior segurança, controle e clareza na exposição de dados via API.

Para realizar a conversão entre entidades e DTOs, foi utilizada a biblioteca **Dozer Mapper**, que simplifica o mapeamento automático entre objetos com estruturas semelhantes.

### Vantagens do uso de DTOs:

- Evita exposição direta de entidades JPA

- Facilita a criação de respostas customizadas

- Permite maior controle sobre os dados trafegados na API

- Ajuda na validação de entrada e saída de dados

> 📌 À medida que novos endpoints forem adicionados, novos DTOs serão criados para representar os dados da forma mais adequada para cada caso de uso.

## Migrações com Flyway

Este projeto utiliza a ferramenta **Flyway** para controle de versionamento e execução automática de migrações no banco de dados. Assim, garantimos que o esquema do banco esteja sempre sincronizado com o estado esperado da aplicação, independentemente do ambiente em que estiver rodando.

### O que é o Flyway?

O Flyway é uma biblioteca de versionamento de banco de dados que executa scripts SQL em sequência, com base em um esquema de versionamento (`V1__`, `V2__`, etc.). Ele mantém um histórico das migrações já aplicadas, evitando que scripts sejam executados mais de uma vez e facilitando o gerenciamento das mudanças no banco.

### Estrutura dos arquivos de migração

Os scripts SQL ficam localizados em:  
`src/main/resources/db/migration`

Cada arquivo de migração deve seguir a seguinte convenção de nomenclatura:  
`V<versão>__<descrição>.sql`

### Exemplo de arquivo de migração

```sql
V1__create_table_person.sql
```

### Benefícios de usar Flyway:

- Evita conflitos e inconsistências no banco entre ambientes
- Controla a ordem das alterações no esquema
- Permite reverter o estado do banco em ambientes de teste
- Mantém um histórico confiável das alterações aplicadas

> 📌 À medida que o projeto evolui, novas migrações serão adicionadas para refletir alterações nas entidades ou regras de negócio.

<br>

![Exemplo SQL](imgs/11-bd.png)

## Content Negotiation

Este projeto implementa **Content Negotiation**, permitindo que as requisições HTTP aceitem e retornem dados em múltiplos formatos, como:

- `application/json`
- `application/xml`
- `application/x-yaml`

Com isso, o cliente pode escolher o formato de resposta desejado utilizando o cabeçalho `Accept`.

### Vantagens

- Flexibilidade para integrar com diferentes tipos de clientes

- Melhora a interoperabilidade da API

- Facilita testes e desenvolvimento com ferramentas como Postman, Insomnia e cURL

> 📌 A configuração de Content Negotiation foi feita com base no uso do WebMvcConfigurer no Spring Boot, definindo os MediaType suportados e suas extensões correspondentes.

<br>

![Exemplo Content Negotiation](imgs/13-bd.png)

## HATEOAS

Este projeto implementa **HATEOAS (Hypermedia as the Engine of Application State)** para enriquecer as respostas da API com links de navegação relacionados às ações possíveis no recurso.

Cada resposta dos endpoints inclui referências para outras operações relevantes, como:

- **Create** – Criação de novos recursos
- **FindById** – Consulta de recurso específico
- **FindAll** – Listagem de todos os recursos
- **Update** – Atualização de dados
- **Delete** – Remoção de recurso

Isso facilita a navegação e a compreensão da API por parte do consumidor, promovendo um design mais RESTful.

<br>

![HATEOS](imgs/14-HATEOS.png)

## Testes unitários

Os testes unitários deste projeto são escritos utilizando **JUnit 5** em conjunto com o **Mockito**, garantindo a qualidade e a confiabilidade das funcionalidades da API.

As principais operações da API (Create, FindById, FindAll, Update e Delete) são testadas de forma isolada, com simulação de dependências externas através do Mockito.

### Benefícios:

- Validação automática do comportamento esperado da aplicação
- Redução de bugs e regressões em alterações futuras
- Facilidade de manutenção do código
- Maior confiança em deploys e integrações contínuas

Os testes estão localizados no diretório:  
`src/test/java/br/com/erudio/services/PersonServicesTest`

> 📌 À medida que novas funcionalidades forem implementadas, novos testes unitários serão adicionados para cobrir os cenários correspondentes.

<br>

![Testes Unitários](imgs/14-TstMock.png)

## Testes de Integração

Para garantir que os endpoints da API estejam funcionando corretamente em conjunto com o ambiente real de execução, o projeto utiliza testes de integração com as bibliotecas **[Testcontainers](https://www.testcontainers.org/)** e **[Rest Assured](https://rest-assured.io/)**.

### Testcontainers

Com o Testcontainers, é possível criar e gerenciar containers Docker diretamente nos testes automatizados, garantindo um ambiente isolado e consistente. Neste projeto, é utilizado um container MySQL, configurado dinamicamente durante os testes.

#### Vantagens do uso do Testcontainers:

- Evita dependência de banco de dados local ou ambiente externo
- Garante um ambiente de testes consistente para todos os desenvolvedores
- Possibilita integração contínua com testes reais de banco de dados
- Os dados são descartados ao final dos testes, mantendo o ambiente limpo

> 📌 A configuração do Testcontainers está localizada nos arquivos de teste da entidade `Person`.

### Rest Assured

A biblioteca **Rest Assured** é utilizada para simular e validar chamadas HTTP diretamente nos testes, permitindo verificar o comportamento dos endpoints da API em nível de integração.

#### Com o Rest Assured é possível:

- Enviar requisições HTTP `GET`, `POST`, `PUT`, `DELETE` de forma fluente
- Validar os códigos de resposta (200, 201, 404, etc.)
- Testar os payloads de entrada e saída com facilidade
- Verificar headers, corpo da resposta e tempo de resposta

#### Exemplo de fluxo testado:

1. **Criar** uma pessoa com `POST /api/person/v1`
2. **Consultar** com `GET /api/person/v1/{id}`
3. **Atualizar** com `PUT /api/person/v1`
4. **Excluir** com `DELETE /api/person/v1/{id}`

Esses testes verificam desde a persistência no banco até o retorno correto dos dados via HTTP.

### Benefícios dos testes de integração:

- Validação de ponta a ponta da aplicação (API + Banco)
- Redução de erros em produção
- Simulação de ambiente real
- Confiança na cobertura dos principais fluxos de negócio

## Documentação com Swagger (SpringDoc OpenAPI)

Este projeto utiliza a biblioteca [SpringDoc OpenAPI](https://springdoc.org/#google_vignette) para gerar automaticamente a documentação interativa da API no padrão Swagger.
<br>

Para se aprofundar mais acessar o [link](https://lankydan.dev/documenting-a-spring-rest-api-following-the-openapi-specification).
<br>

![Swagger](imgs/15-swagger.png)

## CORS

Este projeto implementa a configuração de **CORS (Compartilhamento de Recursos entre Origens Diferentes)** para permitir que aplicações frontend (como React, Angular, etc.) possam se comunicar com a API mesmo estando hospedadas em domínios diferentes.

### O que é CORS?

CORS(Cross-Origin Resource Sharing) é um mecanismo de segurança dos navegadores que impede requisições entre diferentes origens (domínios) caso não estejam explicitamente autorizadas. Sem essa configuração, ao tentar consumir a API de um frontend hospedado em outra origem, o navegador bloquearia a requisição.

### Exemplo de problema comum:

Uma aplicação frontend hospedada em `http://localhost:3000` tenta acessar a API em `http://localhost:8080`, resultando em erro de CORS caso não esteja permitido.

### Solução adotada no projeto

A configuração de CORS foi feita para liberar origens específicas ou de forma global, permitindo que aplicações externas possam consumir os endpoints da API sem bloqueios.

### Vantagens de configurar o CORS corretamente:

- Permite integração com aplicações frontend modernas

- Evita erros de bloqueio em navegadores

- Garante segurança controlando quem pode acessar a API

> 📌 Em ambientes de produção, é recomendado limitar as origens permitidas apenas aos domínios necessários.

## Query Params e Busca Paginada

Este projeto também implementa **busca paginada** utilizando query parameters, o que permite controlar a forma como os dados são retornados pela API, especialmente em grandes volumes.

A busca paginada é útil para:

- Melhorar a performance das requisições
- Evitar sobrecarregar o cliente com muitos dados de uma vez
- Facilitar a navegação por grandes listas de recursos

#### Parâmetros aceitos:

- `page`: número da página (começando em 0)
- `size`: quantidade de registros por página
- `direction`: direção da ordenação (`asc` para crescente, `desc` para decrescente)

### HAL (Hypertext Application Language)

As respostas da API seguem o padrão **HAL (Hypertext Application Language)**, um formato que adiciona hiperlinks aos dados retornados, facilitando a navegação entre recursos relacionados.

Isso significa que cada item retornado na busca paginada inclui _links_ como:

- `self`: link para o próprio recurso
- `first`, `prev`, `next`, `last`: links de paginação
- Ações adicionais, como `update` ou `delete`

### Exemplo visual da resposta:

<br>

![Busca Paginada](imgs/20_BuscaPaginada.png)

> 📌 O uso de HAL facilita a criação de clientes mais inteligentes e alinhados com os princípios RESTful, promovendo a auto-descoberta dos recursos da API.

## Upload e Download de Arquivos

O projeto também implementa funcionalidades de **upload e download de arquivos**, permitindo que clientes da API enviem e recuperem arquivos diretamente do servidor.

### Upload de Arquivo

A API aceita arquivos enviados por meio de uma requisição `POST` com `multipart/form-data`.

![Upload de arquivo](imgs/21_UploadDeArquivo.png)

### Download de Arquivo

Para baixar um arquivo já armazenado no servidor, basta fazer uma requisição `GET` ao endpoint correspondente, informando o nome do arquivo.

![Download de arquivo](imgs/21_DownloadDeArquivo.png)

### Vantagens

- Facilita o envio de imagens, documentos e outros arquivos como parte de um fluxo de negócio
- Permite integrar com sistemas externos ou frontends que trabalham com arquivos
- Simples de consumir com ferramentas como Postman ou aplicações frontend

> 📌 Os arquivos são armazenados em uma pasta específica do servidor e podem ser protegidos conforme as necessidades do projeto.

## Importação e Exportação de Planilhas Excel e CSV

O projeto também implementa funcionalidades de **importação e exportação
de dados** nos formatos **Excel (.xlsx)** e **CSV**, permitindo uma
integração eficiente com sistemas externos, ferramentas de análise e
planilhas manipuladas por usuários finais.

Esses recursos são essenciais em aplicações que precisam lidar com
grandes volumes de dados, realizar migrações, importar cadastros ou
fornecer relatórios.

### 📥 Importação (Upload)

A API permite enviar arquivos Excel ou CSV para realizar a leitura e
processamento dos dados.
Durante a importação, o sistema:

- Valida o formato do arquivo enviado
- Converte os dados para objetos da aplicação
- Realiza o processamento necessário (cadastro, atualização ou
  análise)
- Retorna feedback sobre possíveis erros ou inconsistências

Esse processo é realizado através de um endpoint `POST`.

> 📌 A importação é útil para cadastrar listas de pessoas.

![Importação de arquivo](imgs/22_importacao.png)

---

### 📤 Exportação (Download)

A API também oferece endpoints para **exportar dados em formato Excel ou
CSV**.
Ao realizar a exportação, o sistema:

- Consulta os dados diretamente no banco
- Gera dinamicamente a planilha ou arquivo CSV
- Retorna o arquivo pronto para download

Essa funcionalidade é ideal para relatórios, backups ou migração de
dados para outros sistemas.

![Exportação de arquivo](imgs/22_Exportacao1.png)

## ![Exportação de arquivo](imgs/22_Exportacao2.png)

### Vantagens

- Facilita integração com sistemas externos
- Permite criação de relatórios personalizados
- Agiliza cadastros em massa
- Possibilita backup dos dados da aplicação
- Simples de consumir usando Postman, Insomnia ou frontends como React
  e Angular

## O que é um Mock?

Um _mock_ é uma simulação de um objeto real. Em contextos de desenvolvimento e testes, mocks são usados para representar dados ou comportamentos esperados de componentes que ainda não foram implementados, ou que não se deseja acessar diretamente (como chamadas a APIs externas ou bancos de dados).

No contexto deste projeto, o _mock_ de pessoa é uma classe ou objeto com atributos pré-definidos (como nome, idade, CPF, etc.) que representa uma instância de `Person`. Ele é utilizado para simular uma requisição HTTP de criação de pessoa, facilitando o desenvolvimento e os testes do serviço sem depender de entradas externas.

## Spring Boot Initializr

Para gerar um novo projeto Spring Boot, acesse o [Spring Initializr](https://start.spring.io/).

## Geração de Mocks com Mockaroo

Durante o desenvolvimento do projeto, foi utilizado o site [Mockaroo](https://mockaroo.com/) para gerar dados falsos (mocks) de forma rápida, prática e personalizável.

### O que é o Mockaroo?

O **Mockaroo** é uma ferramenta online que permite gerar conjuntos de dados fictícios com campos customizados, tipos variados e formatos exportáveis (como CSV, JSON, SQL etc.).

### Vantagens de usar Mockaroo:

- Economia de tempo na criação de dados para testes
- Customização de nomes de campos, formatos e quantidades
- Simulação realista de dados como nomes, endereços, datas, números, etc.
- Suporte a exportação em diversos formatos compatíveis com APIs e bancos de dados

> 📌 O uso do Mockaroo acelerou significativamente a geração de massa de dados para testes de endpoints e visualização de resultados, substituindo a criação manual de objetos mock.

## Dependências utilizadas

[pom.xml](https://github.com/BredexBR/bookstore-api/blob/main/bookstore-api/pom.xml)

## Pré-requisitos

1. **Java 21**: Certifique-se de que o Java 21 está instalado na sua máquina.
2. **Docker**: Necessário para a execução de containers. Versão 27.4.0.
3. **MySQL**: Configuração do banco de dados. Versão 8.0.40.
4. **Postman**: Para testar a API.
5. **Java Spring Tool**: Framework na versão 3.3.7.
6. **Mvn**: Gerenciar as dependências e automatizar as builds. Versão 3.3.9.
