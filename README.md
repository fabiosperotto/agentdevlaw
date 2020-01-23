# AgentDevLaw

Este projeto fornece um middleware entre uma ontologia legal e um sistema multiagente. A ontologia legal é oriunda do modelo criado na tese de doutorado de Fábio Aiub Sperotto sobre a legislação brasileira (ver [publicações](#publications)). Uma cópia desta ontologia é fornecida no diretório /ontologies. Utiliza como referência para testes as leis:

- Lei [7653 de 1988](http://www.planalto.gov.br/Ccivil_03/leis/L7653.htm) sobre pescaria na época de reprodução de peixes.
- Lei [5197 de 1967](http://www.planalto.gov.br/ccivil_03/leis/L5197compilado.htm) sobre a caça de animais.



This project provide an middleware between a legal ontology and an multiagent systems. The legal ontology is from a ontology model created in PhD thesis of Fábio Aiub Sperotto about the brazilian legislation (see [publications](#publications)). A copy of this ontology can be found in /ontologies folder. For testing purposes, use the following laws as a reference:
- Lei [7653 de 1988](http://www.planalto.gov.br/Ccivil_03/leis/L7653.htm) about fishing in reprodution period of fishes.
- Lei [5197 de 1967](http://www.planalto.gov.br/ccivil_03/leis/L5197compilado.htm) about animal hunting.


## Applications

Applications and other examples of this project can be found in:

- Multiagent Systems Simulation with JaCaMo and AgentDevLaw: [https://github.com/fabiosperotto/legislative-simulation](https://github.com/fabiosperotto/legislative-simulation).
- Multiagent Systems Simulation with JADE and AgentDevLaw: [https://github.com/fabiosperotto/legislative-simulation-jade](https://github.com/fabiosperotto/legislative-simulation-jade).



## Funcionalidades

- Conexão com um webservice SPARQL onde pode realizar consultas na ontologia a fim de buscar normas
- Fornece métodos para encontrar instâncias de normas, predicados e indivíduos, que explicam as restrições de uma legislação (detalhes futuros em desenvolvimento na tese, posteriormente serão fornecidos em uma Wiki).
- Considera alguma ação externa como filtro das consultas de normas a fim de filtrar normas relacionadas com a atividade.


### Instalação para desenvolvimento

1. Criar projeto com as classes de /src
2. No classpath do projeto, adicionar .jar externos referente ao framework Jena. Os componentes podem ser encontrados em apache-jena-X.X.X/lib (X -> número da versão). O download do framework pode ser [feito aqui](https://jena.apache.org/download/index.cgi).
3. É necessário uma ontologia de referência para ser utilizada como base de conhecimento, uma ontologia é fornecida no diretório /bases como exemplo. Pode ser levantado um serviço de consultas SPARQL com o arquivo OWL fornecido (ver Fuseki abaixo).


### Gerando a biblioteca
Para gerar uma lib a fim de ser utilizada em outros projetos ou em plataformas de simulação de agentes, execute os passos (para Eclipse):
1. Clique com o botão direito no projeto > Export;
2. Dentro das opções do wizard escolha Java > Runnable JAR file
3. Na tela seguinte escolha a opção Extract required libraries into generated JAR, para que as bibliotecas dependentes do Jena possam ser extraídas corretamente e reunidas junta com as demais classes deste projeto. Em seguida só escolher caminho e nome desejado para o .jar a ser gerado.


### Fuseki

[Fuseki](https://jena.apache.org/documentation/fuseki2/) é um servidor SPARQL onde ontologias podem ser fornecidas através de um webservice, servindo como endpoint para efetuar consultas. É utilizado neste projeto desde sua primeira versão.


### Dependences

- owlapi-distribution-5.1.2
- ontapi-2.0.0 https://search.maven.org/artifact/com.github.owlcs/ontapi/2.0.0/jar
- javax.inject-1 https://mvnrepository.com/artifact/javax.inject/javax.inject/1
- guice-4.2.2 https://mvnrepository.com/artifact/com.google.inject/guice/4.2.2
- guava-28.2-jre https://mvnrepository.com/artifact/com.google.guava/guava/28.2-jre
- eclipse-rdf4j-3.0.4-onejar https://rdf4j.org/download/
- commons-rdf-api-0.5.0 https://mvnrepository.com/artifact/org.apache.commons/commons-rdf-api/0.5.0
- caffeine-2.8.1 https://search.maven.org/artifact/com.github.ben-manes.caffeine/caffeine/2.8.1/jar


## Contributors
- Fábio Aiub Sperotto [fabio.aiub@gmail.com](mailto:fabio.aiub@gmail.com)


## Publications 

This is a product of PhD thesis of Fábio Aiub Sperotto

- SPEROTTO, Fábio Aiub; BELCHIOR, Mairon; DE AGUIAR, Marilton Sanchotene. [Ontology-Based Legal System in Multi-agents Systems](https://link.springer.com/chapter/10.1007/978-3-030-33749-0_41). In: Mexican International Conference on Artificial Intelligence. Springer, Cham, 2019. p. 507-521.

