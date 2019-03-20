## AgendDevLaw

Projeto da tese de doutorado. Usa como modelagem e testes a Lei [7653 de 1988](http://www.planalto.gov.br/Ccivil_03/leis/L7653.htm) sobre pescaria na época de reprodução de peixes.

### Funcionalidades

- Conexão com um webservice SPARQL onde pode realizar consultas na ontologia a fim de buscar normas
- Fornece métodos para encontrar instâncias de normas, predicados e indivíduos, que explicam as restrições de uma legislação (detalhes futuros em desenvolvimento na tese, posteriormente serão fornecidos em uma Wiki).
- Considera alguma ação externa como filtro das consultas de normas a fim de filtrar normas relacionadas com a atividade.

### Instalação para desenvolvimento

1. Criar projeto com as classes de /src
2. No classpath do projeto, adicionar .jar externos referente ao framework Jena. Os componentes podem ser encontrados em apache-jena-X.X.X/lib (X -> número da versão). O download do framework pode ser [feito aqui](https://jena.apache.org/download/index.cgi).
3. É necessário uma ontologia de referência para ser utilizada como base de conhecimento, uma ontologia é fornecida no diretório /bases como exemplo. Pode ser levantado um serviço de consultas SPARQL com o arquivo OWL fornecido (ver Fuseki abaixo).

### Fuseki

[Fuseki](https://jena.apache.org/documentation/fuseki2/) é um servidor SPARQL onde ontologias podem ser fornecidas através de um webservice, servindo como endpoint para efetuar consultas. É utilizado neste projeto desde sua primeira versão.