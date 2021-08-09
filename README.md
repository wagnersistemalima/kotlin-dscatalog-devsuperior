# kotlin-dscatalog-devsuperior

# 🛠️ Descrição

* Projeto full stack web e mobile de catálogo de produtos e área administrativa com autenticação e autorização

* Primeira parte Backend

* Crud completo, utilizado no mundo do desenvolvimento de sistemas para designar as quatro operações básicas do gerenciamento de registros em um banco de dados.
As operações referenciadas pelas letras C, R, U, D cujos respectivos termos em inglês são Create, Read, Update e Delete.

## Setup do Projeto 
* Linguagem de programação: Kotlin
* Tecnologia: Spring Boot 2.3.12
* Gerenciador de dependência: Gradlle
* Java 11
* IDE IntelJ

## Implementação utilizando as ferramentas do ecossistema Spring com Kotlin 

* Spring Web: Crie aplicativos da web, incluindo RESTful, usando Spring MVC. Usa Apache Tomcat como o contêiner integrado padrão.

* Bean Validation: é uma especificação que permite validar objetos com facilidade em diferentes camadas da aplicação. A vantagem de usar Bean Validation
é que as restrições ficam inseridas nas classes de modelo.

* H2: Banco de dados em memória, para testes

* Spring Data JPA: Especificação da biblioteca padrão de persistência de dados no java, baseado no mapeamento objeto relacional (javax.percistence)

* Hibernate: É uma das implementações da especificação JPA mais popular

* Especificação: (javax.persistence.Enty) É uma boa prática fazer o código com base na especificação, pois caso depois precise trocar a implementação Hibernate
por outra implementação, a aplicação continuará funcionando

* Spring Boot Actuator: Permitem monitorar e gerenciar seu aplicativo - como integridade, métricas, sessões, etc.

* Swagger: Documentar e consumir APIs REST, o Swagger tem ferramentas que conseguem automatizar a leitura dessa API e a geração de uma documentação.

* Ferramenta Postman: Para testar as requisições e criar um ambiente de produção

## Testes automatizados com JUnit 5 com 95% de linhas cobertas
* O JUnit foi repensado como uma plataforma para construção e execução de testes, de modo que o JUnit 5 é composto por diversos módulos com papéis diferentes (ao invés de “um único framework”)
* JUnit Jupiter: Este módulo contém os novos recursos para construção de testes usando o JUnit, e fornece uma implementação de TestEngine para execução dos testes escritos com o JUnit Jupiter.
* Testes unitarios ( @Mock , @InjectMock, @ExtendWith(SpringExtension::class))
* Testes de integraçao com um banco de testes (@SpringBootTest, @AutoConfigureDataMongo, @AutoConfigureMockMvc, @ActiveProfile("teste"))

![alter text](https://github.com/wagnersistemalima/kotlin-dscatalog-devsuperior/blob/main/backend/images/coverageDsCatalog.png)

## Porque IDE IntelJ
* Cada aspecto do IntelliJ IDEA foi projetado para maximizar a produtividade do desenvolvedor. Juntos, a assistência para codificação inteligente e o design ergonômico tornam o desenvolvimento não apenas produtivo, mas também agradável.

## Porque Kotlin
* Com o conhecimento da linguagem Kotlin, é possivel desenvolver aplicações mobile, backend e web
* Kotlin é multiplataforma
* Mesmo com seus recursos propios e sua sintaxe limpa, ele mantem uma performace equivalente ao java
* Menos sucetivel a erros, Kotlin é por padrão null safety, fazendo que exista menos erro no seu desenvolvimento
* Kotlin simplifica alguns recursos que a linguagem java precisa para funcionar com excelencia, como por exemplo gatters and setters, faça o mesmo escrevendo mesnos
* Possui integração com codigo Java

## Porque Spring
* Spring é um framework Java Kotlin para backend mais popular no mundo por pessoas e por empresas, por conta da sua velocidade, simplicidade, produtividade
* Spring tem o historico comprovado de lidar com problemas de segurança de forma rapida e responsavel. Além disso Spring Securiy facilita a integração com esquemas de segurança padrão da industria e oferece soluçoes confiaveis que são seguras por padrão
* Spring Boot transforma a maneira como se aborda as tarefas de programação, otimizando radicalmente sua experiência. Podemos combinar Spring Boot com um rico conjunto de bibliotecas de suporte
* O conjunto flexivel e abrangente de extenções e bibliotecas de terceiros, permite que os desenvolvedores criem quase todos os aplicativos imagináveis
* Com Spring, notamos inicialização rapida, desligamento rapido e execução otimizada por padrão

## Competencias backend

* Criar projeto Spring Boot
* Criar monorepo Git
* Organizar o projeto em camadas
* Controlador REST
* Serviço
* Acesso a dados (Repository)
* Criar entidades
* Configurar perfil de teste do projeto
* Seeding da base de dados
* Criar web services REST
* Parâmetros de rota @PathVariable
* Parâmetros de requisição @RequestParam
* Corpo de requisição @RequestBody
* Resposta da requisição ResponseEntity
* Padrão DTO
* CRUD completo
* Tratamento de exceções
* Postman (coleções, ambientes)
* Dados de auditoria
* Paginação de dados
* Associações entre entidades (N-N)
* Documentação API

## Desenvolvimento api e estruturas

* Estrutura camadas

![alter text](https://github.com/wagnersistemalima/kotlin-dscatalog-devsuperior/blob/main/backend/images/padraocamadas.png)

* Modelo conceitual

![alter text](https://github.com/wagnersistemalima/kotlin-dscatalog-devsuperior/blob/main/backend/images/modeloconceitual.png)
