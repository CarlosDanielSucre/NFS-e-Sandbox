# NFS-e Sandbox

API educacional para simular a emissão de uma Nota Fiscal de Serviço Eletrônica, do cadastro de um RPS até sua autorização ou rejeição por uma prefeitura fictícia.

> Projeto em desenvolvimento, na etapa de domínio em Java. O progresso abaixo se baseia nos trechos implementados e revisados durante o desenvolvimento; não representa verificação do repositório completo. API, banco, XML e transmissão continuam planejados. Os documentos emitidos serão fictícios e sem validade fiscal.

## Objetivo

Praticar desenvolvimento backend com Java e Spring Boot, modelagem de domínio, regras de negócio, geração e validação de XML, persistência e integração simulada.

O MVP estará concluído quando permitir criar um RPS, consultá-lo e transmiti-lo ao simulador, preservando o resultado e impedindo a emissão duplicada.

## Escopo

| Incluído no MVP | Fora do MVP |
| --- | --- |
| Prestador, tomador e serviço informados no RPS | Cadastros independentes com CRUD completo |
| Cálculo simplificado de ISS | Motor tributário e regras municipais reais |
| Validação de dados e XML contra XSD | Certificado e assinatura digital reais |
| Um RPS por transmissão síncrona | Lotes com vários RPS e processamento assíncrono |
| Prefeitura simulada com resposta determinística | Integração com prefeitura real |
| Autorização, rejeição e consulta persistidas | Cancelamento e substituição |
| Proteção contra duplicidade | Interface Angular, autenticação e múltiplos usuários |

## Decisões do projeto

- Monólito Spring Boot: API e simulador no mesmo processo inicialmente.
- ABRASF 2.04 como versão alvo do XML. A operação e o conjunto exato de schemas devem ser confirmados e registrados antes da implementação do gerador.
- Não misturar ABRASF com o padrão nacional da NFS-e neste MVP.
- O simulador representa apenas o contrato educacional definido aqui; não promete reproduzir uma prefeitura real.
- O JSON da API é um contrato próprio. Sua conversão para XML fica sob responsabilidade da aplicação.
- Regras de assinatura exigidas pelos schemas escolhidos devem ser avaliadas nessa etapa. Não remover exigências silenciosamente nem declarar conformidade sem validar o documento completo.

## Tecnologias e estágio atual

| Tecnologia | Responsabilidade |
| --- | --- |
| Java e Spring Boot | Aplicação backend |
| Spring Web | API REST |
| Bean Validation | Validação dos dados de entrada |
| Spring Data JPA | Persistência |
| PostgreSQL | Banco de dados |
| Flyway | Evolução do schema do banco |
| Gradle | Build escolhido para o projeto; configuração e versão a documentar |
| Biblioteca de XML e validador XSD do Java | Serialização e validação estrutural |
| JUnit e testes de integração | Verificação das regras e do fluxo |

O domínio atual utiliza Java, `BigDecimal`, `LocalDateTime`, `ResourceBundle` e `MessageFormat`. As versões exatas do JDK, Gradle e Spring Boot devem ser confirmadas no repositório antes de documentar comandos de execução.

## Progresso atual

### Domínio desenvolvido

| Classe | Responsabilidade e comportamento atual |
| --- | --- |
| `Address` | Agrupa logradouro, número, complemento, bairro, cidade, UF, CEP e código do município. Trata espaços externos e permite complemento nulo. |
| `PersonType` | Enum com `INDIVIDUAL` e `LEGAL_ENTITY`. |
| `ServiceParty` | Classe abstrata com documento, tipo de pessoa, nome e endereço; concentra as validações comuns. |
| `ServiceProvider` | Estende `ServiceParty` e acrescenta inscrição municipal obrigatória no sandbox. |
| `ServiceCustomer` | Estende `ServiceParty` para representar o tomador. |
| `ServiceDetails` | Mantém código, descrição, valor e alíquota; calcula ISS com `calculateIss()`. |
| `ValidationSupport` | Compartilha `requireNonBlank` e o método genérico `requireNonNull`, retornando os valores validados. |
| `Rps` | Reúne número, série, data, prestador, tomador e serviço; valida obrigatoriedade e inicia em `CREATED`. |
| `RpsStatus` | Estados previstos no modelo: `CREATED`, `AUTHORIZED` e `REJECTED`. |

### Validações e mensagens

- Textos obrigatórios são rejeitados quando nulos ou em branco e armazenados sem espaços nas extremidades.
- UF: duas letras maiúsculas, por meio de `[A-Z]{2}`; ainda sem conferência da lista de UFs.
- CEP: oito dígitos, por meio de `[0-9]{8}`.
- Código do município: sete dígitos, por meio de `[0-9]{7}`; ainda sem consulta à existência ou correspondência com a cidade.
- Documento: checagem inicial de formato conforme `PersonType`. Dígitos verificadores e regras completas ainda estão pendentes.
- As regras alvo para valores são `amount > 0` e `0 < issRate <= 5`. Os cenários de nulo, zero e limites devem ser consolidados em testes automatizados.
- As mensagens ficam em `src/main/resources/messages.properties`, carregadas com `ResourceBundle`.
- Mensagens parametrizadas, como `validation.required={0} is required`, são formatadas com `MessageFormat`.

O cálculo implementado multiplica `amount` por `issRate` e divide por 100, com escala 2 e `RoundingMode.HALF_UP`. Exemplos de referência: 1000,00 a 5% resulta em 50,00; 10,10 a 5% resulta em 0,51.

### Próxima entrega

Foi definido e fornecido um primeiro método `authorize()`, que só permite a transição de `CREATED` para `AUTHORIZED` e lança `IllegalStateException` em outros estados. A incorporação e os testes desse método ainda precisam ser confirmados.

Os próximos passos são concluir as transições de autorização e rejeição, associar os dados do resultado e automatizar os testes do domínio. Alterar o status em memória ainda não representa emissão completa, persistência ou transmissão.

## Organização atual do código

Pacote de domínio: `com.nfsesandbox.rps.domain`.

| Caminho | Conteúdo |
| --- | --- |
| `src/main/java/com/nfsesandbox/rps/domain/` | Classes de domínio, enumerações e validações compartilhadas |
| `src/main/resources/messages.properties` | Catálogo de mensagens |
| `src/test/java/com/nfsesandbox/rps/domain/` | Local previsto para testes automatizados do domínio |

A evolução prevê os pacotes `rps.api`, `rps.application`, `rps.persistence`, `rps.xml` e `prefeitura`. Eles serão introduzidos conforme suas responsabilidades forem implementadas.

## Fluxo de emissão planejado

1. O cliente informa identificação do RPS, prestador, tomador, serviço e valores.
2. A aplicação valida a entrada, calcula o ISS e salva o RPS como `CREATED`.
3. O cliente solicita a transmissão.
4. A aplicação gera o XML e valida sua estrutura contra os XSD escolhidos.
5. O simulador aplica suas regras de aceitação.
6. A aplicação persiste a autorização fictícia ou a rejeição e devolve o resultado.

Entrada inválida na criação retorna erro e não cria um RPS. Uma rejeição municipal acontece após a criação e fica registrada para consulta. Falhas técnicas de geração, persistência ou comunicação não devem ser apresentadas como rejeições fiscais.

## Modelo de domínio

| Conceito | Dados principais |
| --- | --- |
| RPS | Atualmente: número, série, data de emissão, prestador, tomador, serviço e status. Identificador interno e tipo ainda serão definidos para persistência/layout. |
| Prestador | Documento, tipo de pessoa, nome, inscrição municipal e endereço |
| Tomador | CPF ou CNPJ, nome e dados de endereço exigidos pelo layout |
| Serviço | Descrição, código do serviço, município, valor e alíquota |
| Valores calculados | Base de cálculo e valor do ISS |
| Resultado da transmissão | Data, XML enviado, status e dados da resposta |
| NFS-e simulada | Número fictício e protocolo fictício associados ao RPS autorizado |
| Rejeição | Código e mensagem da regra violada |

A tabela distingue o modelo atual dos resultados ainda planejados. Os campos obrigatórios adicionais do XML serão derivados dos schemas selecionados.

### Estados

| Estado | Significado | Próxima ação |
| --- | --- | --- |
| `CREATED` | RPS criado; persistência e transmissão ainda serão implementadas | Transmitir quando o fluxo estiver disponível |
| `AUTHORIZED` | NFS-e fictícia emitida | Consultar ou recuperar o mesmo resultado |
| `REJECTED` | Simulador recusou o RPS | Consultar ou recuperar a mesma rejeição |

O MVP não permite editar ou retransmitir com dados alterados um RPS já processado. Falhas técnicas sem resultado definitivo preservam o estado `CREATED`, permitindo nova tentativa com os mesmos dados.

## Regras de negócio

### Validação local

- Exigir os campos definidos no contrato da API.
- Validar formato e dígitos verificadores de CPF/CNPJ, sem consulta cadastral externa.
- Exigir valor do serviço maior que zero.
- Usar alíquota percentual maior que zero e menor ou igual a 5 como convenção do sandbox, sem representar uma regra fiscal universal.
- Rejeitar campos e valores incompatíveis antes de salvar.
- Calcular o ISS no servidor; não aceitar do cliente um imposto calculado como valor definitivo.

### Cálculo simplificado do ISS

Neste MVP não existem deduções, retenções, isenções nem regimes especiais.

`base de cálculo = valor do serviço`

`ISS = base de cálculo × alíquota percentual / 100`

Exemplo: serviço de R$ 1.000,00 e alíquota de 5% resulta em ISS de R$ 50,00.

Usar `BigDecimal`, com arredondamento `HALF_UP` para duas casas decimais no valor final do imposto. A API representa 5% como `5.00`; a conversão para a representação exigida no XML pertence ao gerador.

### Rejeição simulada

O resultado deve depender de regras explícitas, sem sorteio ou comportamento aleatório.

| Código interno | Regra do simulador |
| --- | --- |
| `SIM001` | Código de serviço ausente da lista permitida pelo simulador |
| `SIM002` | Município do prestador diferente do município configurado no simulador |

Esses códigos pertencem ao projeto e não são códigos oficiais da ABRASF. Um código de serviço bem formado pode passar na validação local e ser rejeitado pelo simulador por não estar habilitado.

### Duplicidade e idempotência

- Planejar a identificação única pela combinação de documento e tipo de pessoa do prestador, inscrição municipal, município, número, série e tipo do RPS. Confirmar essa chave ao implementar persistência e selecionar o layout.
- Impedir duas criações com a mesma chave por meio de uma restrição única no banco.
- Uma criação duplicada retorna conflito e informa o identificador existente.
- Transmitir novamente um RPS processado retorna o mesmo resultado persistido, sem gerar outro número ou protocolo.
- Requisições concorrentes também devem gerar no máximo uma NFS-e por RPS. Uma consulta prévia isolada não garante essa regra: é necessário controle transacional e unicidade no banco.

## API planejada

| Método | Rota | Resultado esperado |
| --- | --- | --- |
| `POST` | `/api/rps` | Validar, calcular ISS e criar o RPS |
| `POST` | `/api/rps/{id}/transmit` | Processar ou devolver o resultado existente |
| `GET` | `/api/rps/{id}` | Consultar dados, valores, status e resultado |

### Semântica HTTP

| Status HTTP | Uso |
| --- | --- |
| `201 Created` | RPS criado, com cabeçalho `Location` para consulta |
| `200 OK` | Consulta ou transmissão concluída, incluindo rejeição simulada |
| `400 Bad Request` | JSON malformado ou entrada inválida |
| `404 Not Found` | Identificador de RPS inexistente |
| `409 Conflict` | Tentativa de criar a mesma identificação de RPS |
| `500 Internal Server Error` | Falha interna, inclusive XML gerado incompatível com o XSD |

Uma resposta HTTP `200` na transmissão indica que a operação foi processada. O campo de status do documento distingue `AUTHORIZED` de `REJECTED`.

Erros devem ter código, mensagem compreensível e, quando aplicável, campo inválido. Não retornar stack traces ao cliente.

## Organização da aplicação

Manter responsabilidades separadas, sem criar vários serviços ou módulos de implantação no MVP.

| Parte | Responsabilidade |
| --- | --- |
| API | Receber requisições, validar contratos e mapear respostas HTTP |
| Aplicação | Coordenar criação, consulta e transmissão |
| Domínio | Cálculo do ISS, identificação do RPS e transições de estado |
| Persistência | Salvar e consultar dados com garantias de unicidade |
| XML | Gerar documentos e validar contra schemas versionados |
| Integração simulada | Aplicar regras municipais e devolver resultado fictício |

O controlador não calcula imposto nem monta XML. O simulador não decide a resposta HTTP. O banco mantém os dados necessários para consultar uma autorização após reiniciar a aplicação.

## Plano de implementação

O domínio está em desenvolvimento; as demais camadas ainda não foram demonstradas.

- [x] Modelar endereço, participantes e serviço.
- [x] Centralizar validações compartilhadas e mensagens.
- [x] Implementar cálculo simplificado do ISS.
- [x] Modelar criação do RPS com campos obrigatórios e estado inicial.
- [ ] Concluir e testar transições de estado e dados de autorização/rejeição.
- [ ] Implementar validação completa dos documentos.
- [ ] Consolidar testes automatizados das regras do domínio.

- [ ] **1. Fundação:** iniciar o projeto, fixar versões, configurar PostgreSQL e primeira migration.
- [ ] **2. Domínio (em andamento):** concluir identificação, validação de documentos, resultados e testes das transições. Cálculo do ISS e estrutura inicial já desenvolvidos.
- [ ] **3. Criação e consulta:** implementar os endpoints, persistência e erros de validação.
- [ ] **4. XML:** selecionar a operação ABRASF, registrar origem e versão dos XSD e validar um documento de referência.
- [ ] **5. Simulador:** definir município e serviços permitidos, autorização e rejeições determinísticas.
- [ ] **6. Transmissão:** integrar XML, simulador e persistência do resultado.
- [ ] **7. Duplicidade:** garantir unicidade, repetição idempotente e comportamento concorrente.
- [ ] **8. Demonstração:** documentar execução, exemplos de requisições e evidências dos cenários de aceite.

## Critérios de aceite

| Cenário | Resultado necessário |
| --- | --- |
| Emissão válida | XML válido contra os XSD selecionados e autorização com número e protocolo fictícios |
| Entrada inválida | Erro com campo e motivo; nenhum RPS criado |
| Rejeição municipal | RPS válido localmente, rejeitado por regra do simulador, com código e mensagem persistidos |
| Criação duplicada | Conflito sem criar outro RPS |
| Transmissão repetida | Mesmo resultado, número e protocolo, sem nova NFS-e |
| Transmissões concorrentes | No máximo uma NFS-e para o mesmo RPS |
| Reinício da aplicação | RPS e resultado continuam disponíveis para consulta |
| Falha técnica antes do resultado | Nenhuma autorização fictícia inventada; nova tentativa permanece possível |

Os testes devem cobrir o cálculo e arredondamento do ISS, as transições de estado, os documentos XML e o fluxo com banco real de testes, especialmente duplicidade e concorrência.

## Execução local

As instruções serão preenchidas quando existir uma implementação executável. Ainda não há comandos de instalação ou inicialização validados.

Antes de concluir o MVP, registrar nesta seção:

- Versões de Java e Gradle e configuração do Gradle Wrapper.
- Como iniciar o PostgreSQL e configurar as credenciais por variáveis de ambiente.
- Como aplicar migrations, iniciar a aplicação e executar testes.
- Porta e URL local da API.
- Exemplos reproduzíveis para autorização, rejeição e duplicidade, usando apenas dados fictícios.

## Limite de conclusão

O MVP termina quando todos os critérios de aceite forem demonstrados. Interface web, integração real, padrão nacional, cancelamento e processamento assíncrono ficam para uma próxima versão, após o fechamento desta entrega.