## AgendDevLaw

Projeto da tese de doutorado.

### Funcionalidades

- COnexão com um webservice SPARQL onde pode realizar consultas na ontologia a fim de buscar normas
- Fornece métodos para encontrar instâncias de normas, predicados e indivíduos, que explicam as restrições de uma legislação (detalhes futuros em desenvolvimento na tese, posteriormente serão fornecidos em uma Wiki).

### Instalação para desenvolvimento

1. Criar projeto com as classes de /src
2. No classpath do projeto, adicionar .jar externos referente ao framework Jena. Os componentes podem ser encontrados em apache-jena-X.X.X/lib (X -> número da versão). O download do framework pode ser [feito aqui](https://jena.apache.org/download/index.cgi).

### Fuseki

[Fuseki](https://jena.apache.org/documentation/fuseki2/) é um servidor SPARQL onde ontologias podem ser fornecidas através de um webservice, servindo como endpoint para efetuar consultas. É utilizado neste projeto desde sua primeira versão.