package agentdevlaw;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;

public class OntologiaRemota {
	
	private String uriBase;
	private String endpoint;
	private String queryPrefix = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	
	public OntologiaRemota(String end, String url) {
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
	
	public ResultSet consultar(String consulta) {
		QueryExecution query = QueryExecutionFactory.sparqlService(this.endpoint, this.queryPrefix + consulta);
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
