package br.com.agentdevlaw.ontology;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

public class OntologyConfigurator {
	
	private String uriBase;
	private String endpoint;
	private String queryPrefix = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
	/**
	 * Set the origin of an ontology to file
	 */
	public static final int MODEL = 0;
	
	/**
	 * Set the origin of an ontology to server (from an endpoint, webservice)
	 */
	public static final int SERVER = 1;
	
	private int origin = SERVER;
	
	
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

	public int getOrigin() {
		return origin;
	}

	/**
	 * Set the origin of an ontology, the options are: MODEL (file origin) or SERVER (url origin)
	 * @param origin
	 */
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	
	
	private  QueryExecution QueryExecutionFabricator(String queryString) {
		
		QueryExecution query = null;
		
		if(this.origin == SERVER) {
			query = QueryExecutionFactory.sparqlService(this.endpoint, this.queryPrefix + queryString);
		}
		
		if(this.origin == MODEL) {
			
			Model model = FileManager.get().loadModel(this.endpoint);
			query = QueryExecutionFactory.create(this.queryPrefix + queryString, model);
		}
		
		return query;
	}
	
	/**
	 * Initialize the query execution in a model of an ontology.
	 * @param queryString the SPARQL query 
	 * @return ResultSet with results of a SPARQL query
	 */
	public ResultSet setup(String queryString) {

		QueryExecution query = this.QueryExecutionFabricator(queryString);
		ResultSet dataset = query.execSelect();
		return dataset;
	
        
	}
	

}
