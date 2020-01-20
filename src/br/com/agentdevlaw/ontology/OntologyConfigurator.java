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
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
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
	public Model sourceModel = null;
	
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
		
		try(InputStream input = new FileInputStream("config.properties")){
			Properties prop = new Properties();
			prop.load(input);
			this.endpoint_query = prop.getProperty("onto.endpoint");
			this.endpoint_update = prop.getProperty("onto.endpoint_update");
			this.endpoint = this.endpoint_query;
			this.uriBase = prop.getProperty("onto.base_uri");
			this.queryPrefix += "PREFIX law: <"+this.uriBase+"#>";
			
		}catch(IOException ex) {
			System.out.println("config.properties file not found or without the correct onto.endpoint/onto.base_uri values");
		}
		
		
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
	}
	
	/**
	 * This will prepare the connection with a ontology origins (web or from file).
	 * @param queryString required string to be executed
	 * @return
	 */
	private  QueryExecution QueryExecutionFabricator(String queryString) {
		
		QueryExecution query = null;
		
		System.out.println("endpoint used: "+this.endpoint);
		System.out.println(this.queryPrefix + queryString);
		
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
				this.sourceModel = FileManager.get().loadModel(this.endpoint);
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
	

}
