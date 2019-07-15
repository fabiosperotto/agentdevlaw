package br.com.agentdevlaw.ontology;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;

public class OntologyConfigurator {
	
	private String uriBase;
	private String endpoint;
	private String queryPrefix = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
	
	/**
	 * Construtor da classe OntologiaRemota
	 * @param end String com a url do endpoint, ou webservice onde fornece a ontologia OWL/RDF
	 * @param url String url base registrada para a ontologia, cada conceito possui uma URL base, necessaria para as consultas SPARQL
	 */
	public OntologyConfigurator(String end, String url) {
		this.endpoint = end;
		this.uriBase = url;
		this.queryPrefix += "PREFIX law: <"+this.uriBase+"#>";
	}

	public String getUriBase() {
		return uriBase;
	}

	public void setUriBase(String uri) {
		this.uriBase = uri;
	}
	
	public ResultSet run(String queryString) {
		QueryExecution query = QueryExecutionFactory.sparqlService(this.endpoint, this.queryPrefix + queryString);
        ResultSet dataset = query.execSelect();
        return dataset;
	}

	public String getQueryPrefix() {
		return queryPrefix;
	}

	public void setQueryPrefix(String queryPrefix) {
		this.queryPrefix = queryPrefix;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	
	

}
