package br.com.agentdevlaw.ontology;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.util.FileManager;

public class OntologyConfigurator {
	
	private String uriBase;
	private String endpoint;
	private String endpoint_query;
	private String endpoint_update;
	private String queryPrefix = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
	private Model sourceModel = null;
	
	/**
	 * Set the origin of an ontology to file
	 */
	public static final int MODEL = 0;
	
	/**
	 * Set the origin of an ontology to server (from an endpoint, webservice)
	 */
	public static final int SERVER = 1;
	
	private int origin = SERVER;
	
	
	public OntologyConfigurator() {
		
		Properties prop = this.getProperties();
		this.endpoint_query = prop.getProperty("onto.endpoint");
		this.endpoint_update = prop.getProperty("onto.endpoint_update");
		this.endpoint = this.endpoint_query;
		this.uriBase = prop.getProperty("onto.base_uri");
		this.queryPrefix += "PREFIX law: <"+this.uriBase+"#>";
		this.setOrigin(MODEL);
		
	}
	
	/**
	 * Get properties from configuration file (need a config.properties)
	 * @return Properties object
	 */
	public Properties getProperties() {
		
		Properties prop = new Properties();
		try(InputStream input = new FileInputStream("config.properties")){
			
			prop.load(input);
			
		}catch(IOException ex) {
			System.out.println("config.properties file not found or without the correct onto.endpoint/onto.base_uri values");
		}
		
		return prop;
	}

	public String getEndpointQuery() {
		return endpoint_query;
	}

	public void setEndpointQuery(String endpoint_query) {
		this.endpoint_query = endpoint_query;
	}

	public String getEndpointUpdate() {
		return endpoint_update;
	}

	public void setEndpointUpdate(String endpoint_update) {
		this.endpoint_update = endpoint_update;
	}

	public Model getSourceModel() {
		return sourceModel;
	}

	public void setSourceModel(Model sourceModel) {
		this.sourceModel = sourceModel;
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
	 * @param origin the option constant described (MODEL/SERVER)
	 */
	public void setOrigin(int origin) {
		this.origin = origin;
		if(this.origin == MODEL) {
			this.sourceModel = FileManager.get().loadModel(this.endpoint);
		}
	}
	
	/**
	 * This will prepare the connection with a ontology origins (web or from file).
	 * @param queryString required string to be executed
	 * @return
	 */
	private  QueryExecution QueryExecutionFabricator(String queryString) {
		
		QueryExecution query = null;
		
		if(this.origin == SERVER) {
				
			try { 
				new URL(this.endpoint).toURI(); 
		        query = QueryExecutionFactory.sparqlService(this.endpoint, this.queryPrefix + queryString);
				
			}catch (Exception e) { 
		    	System.out.println("check your endpoint by a valid URL in config.properties");
		    	System.out.println(e.getMessage());
		    	return null;
		    } 
		}
			
		if(this.origin == MODEL) {

			try{
				
				query = QueryExecutionFactory.create(this.queryPrefix + queryString, this.sourceModel);
			
			}catch (Exception e) {
				System.out.println(e.getMessage() + ", check your config.properties");
				return null;
			}
			
		}
		
		return query;
	}
	
	/**
	 * Initialize the query execution in a model of an ontology.
	 * @param queryString the SPARQL query 
	 * @return ResultSet with results of a SPARQL query
	 */
	public ResultSet setup(String queryString) {

		this.endpoint = this.endpoint_query;
		QueryExecution query = this.QueryExecutionFabricator(queryString);
		ResultSet dataset = query.execSelect();
		
		return dataset;
	
	}
	
	/**
	 * Setup and execute insertions in a remote SPARQL service
	 * @param queryString the SPARQL query 
	 * @return boolean true if remote domain is updated
	 */
	public boolean setupUpdate(String queryString) {
		
		this.endpoint = this.endpoint_update;

		UpdateRequest request = UpdateFactory.create();
		request.add(this.queryPrefix + queryString);
		UpdateExecutionFactory.createRemote(
                request, this.endpoint).execute();
		
		return true;
		
	}
	
	/**
	 * Execute ASK SPARQL queries
	 * @param askQuery String with a well formed query
	 * @return boolean the return of a response ASK query, that always true or false if exists
	 */
	public boolean askQueries(String askQuery) {
		
		this.endpoint = this.endpoint_query;
		QueryExecution query = this.QueryExecutionFabricator(askQuery);
		return query.execAsk();
		
	}
	

}
