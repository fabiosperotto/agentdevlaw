# AgentDevLaw

Este projeto fornece um middleware entre uma ontologia legal e um sistema multiagente. A ontologia legal é oriunda do modelo criado na tese de doutorado de Fábio Aiub Sperotto sobre a legislação brasileira (ver [publicações](#publications)). Uma cópia desta ontologia é fornecida no diretório /ontologies. Utiliza como referência para testes as leis:

- Lei [7653 de 1988](http://www.planalto.gov.br/Ccivil_03/leis/L7653.htm) sobre pescaria na época de reprodução de peixes.
- Lei [5197 de 1967](http://www.planalto.gov.br/ccivil_03/leis/L5197compilado.htm) sobre a caça de animais.



This project provides a middleware between a legal ontology and multiagent systems. The legal ontology is from an ontology model created in a PhD thesis of Fábio Aiub Sperotto about the Brazilian legislation (see [publications](#publications)). A copy of this ontology can be found in /ontologies folder. For testing purposes, use the following laws as a reference:
- Lei [7653 de 1988](http://www.planalto.gov.br/Ccivil_03/leis/L7653.htm) about fishing in reprodution period of fishes.
- Lei [5197 de 1967](http://www.planalto.gov.br/ccivil_03/leis/L5197compilado.htm) about animal hunting.



## How to Use

This repository is for development purposes and the [download of the last version](https://github.com/fabiosperotto/agentdevlaw/releases) of the middleware. Applications and other examples of this project can be found in:

- Multiagent Systems Simulation with JaCaMo and AgentDevLaw: [https://github.com/fabiosperotto/legislative-simulation](https://github.com/fabiosperotto/legislative-simulation).
- Multiagent Systems Simulation with JADE and AgentDevLaw: [https://github.com/fabiosperotto/legislative-simulation-jade](https://github.com/fabiosperotto/legislative-simulation-jade).



## Funcionalidades

- Funciona com ontologias em arquivos OWL ou através de ontologias online (servidores SPARQL).
- Com o modelo de ontologia legal (/ontologies), fornece métodos para encontrar normas e consequências. Procura por instâncias de leis, normas, predicados e indivíduos que expliquem as regulamentações de uma ação.
- Fornece métodos para compreender dados e tipos de dados da ontologia

## Features

- Works with ontology in OWL file or through online ontologies (SPARQL services).
- With the legal ontology model (/ontologies), provide methods to find norms, consequences. Search for laws instances, norms, predicate and individuals that explain the regulations of any agent's action.
- Provide methods to understand ontology data and data types.


### Development Installation

1. Criar projeto com as classes de /src
2. No classpath do projeto, adicionar .jar externos referente ao framework Jena. Os componentes podem ser encontrados em apache-jena-X.X.X/lib (X -> número da versão). O download do framework pode ser [feito aqui](https://jena.apache.org/download/index.cgi).
3. Coloque também os pacotes do diretório /vendor no classpath que são itens extras para funcionamento das manipulações de ontologias pelo middleware (ver [Dependencies](#Dependencies)).
4. É necessário uma ontologia de referência para ser utilizada como base de conhecimento, uma ontologia é fornecida no diretório /bases como exemplo. Pode ser levantado um serviço de consultas SPARQL com o arquivo OWL fornecido (ver Fuseki abaixo).

#### Tests
Unit tests are available in tests package (jUnit 4 is in used).


### Generate the software component
Para gerar uma lib a fim de ser utilizada em outros projetos ou em plataformas de simulação de agentes, execute os passos (para Eclipse):
1. Clique com o botão direito no projeto > Export;
2. Dentro das opções do wizard escolha Java > Runnable JAR file
3. Na tela seguinte escolha a opção Extract required libraries into generated JAR, para que as bibliotecas dependentes do Jena possam ser extraídas corretamente e reunidas junta com as demais classes deste projeto. Em seguida só escolher caminho e nome desejado para o .jar a ser gerado.


### Fuseki

[Fuseki](https://jena.apache.org/documentation/fuseki2/) é um servidor SPARQL onde ontologias podem ser fornecidas através de um webservice, servindo como endpoint para efetuar consultas. É utilizado neste projeto desde sua primeira versão.


### Dependencies

All dependencies package are in vendor folder:

- [OWL API](http://owlcs.github.io/owlapi/), [owlapi-distribution-5.1.2](https://github.com/owlcs/owlapi/wiki/All-Releases)
- [ONT-API](https://github.com/owlcs/ont-api), [ontapi-2.0.0](https://search.maven.org/artifact/com.github.owlcs/ontapi/2.0.0/jar)
- [javax.inject-1](https://mvnrepository.com/artifact/javax.inject/javax.inject/1)
- [guice-4.2.2](https://mvnrepository.com/artifact/com.google.inject/guice/4.2.2)
- [guava-28.2-jre](https://mvnrepository.com/artifact/com.google.guava/guava/28.2-jre)
- [eclipse-rdf4j-3.0.4-onejar](https://rdf4j.org/download)
- [commons-rdf-api-0.5.0](https://mvnrepository.com/artifact/org.apache.commons/commons-rdf-api/0.5.0)
- [caffeine-2.8.1](https://search.maven.org/artifact/com.github.ben-manes.caffeine/caffeine/2.8.1/jar)


## Contributors
- Fábio Aiub Sperotto [fabio.aiub@gmail.com](mailto:fabio.aiub@gmail.com)


## Publications 

This is a product of PhD thesis of Fábio Aiub Sperotto

- SPEROTTO, Fábio Aiub; BELCHIOR, Mairon; DE AGUIAR, Marilton Sanchotene. [Ontology-Based Legal System in Multi-agents Systems](https://link.springer.com/chapter/10.1007/978-3-030-33749-0_41). In: Mexican International Conference on Artificial Intelligence. Springer, Cham, 2019. p. 507-521.

