package agentdevlaw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.RDF;

public class Principal {

	public static void main(String[] args) throws IOException {
		
		QuerySolution qs = null;
		Resource recurso = null;
	
		
		OntologiaRemota ontologia = new OntologiaRemota("http://localhost:3030/ds/query", "http://www.semanticweb.org/fsp/ontologies/2018/11/agentdevlaw");
		
		String consultaNorma = 
				"SELECT ?x ?c WHERE {" + 
				"	?x rdf:type law:norma ." +
				"	?x rdfs:comment ?c" +
				"}";

		String queryString = 
				"SELECT * where {" + 		  
				"	law:artigo27_7653 ?p ?o"+ 
				"}";
		
//		consulta = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", consultaNorma);
//        ResultSet dataSetNormas = consulta.execSelect();
		ResultSet dataSetNormas = ontologia.consultar(consultaNorma);
        Resource norma = null;
        Literal comentario;
        while(dataSetNormas.hasNext()) {
		    qs = dataSetNormas.next();
		    norma = qs.getResource("x");
		    comentario = qs.getLiteral("c");
			System.out.println("A norma: "+norma.getLocalName());
			
			System.out.println("Descreve que: "+WordUtils.wrap(comentario.toString(), 130, "\n", true)+"\n");
		   
		}
        
//        //consulta de precidados da norma em questao
//        String consultaPredicadosNorma = "SELECT * WHERE " +
//        		"{" +
//        		"<"+norma+"> ?p ?o ." +
//        		"}";
//        
//        ResultSet dataSetPredicados = ontologia.consultar(consultaPredicadosNorma);
////        Resource recurso = null;
//        while(dataSetPredicados.hasNext()) {
//		    qs = dataSetPredicados.next();
//		    recurso = qs.getResource("p");
//		    if(!recurso.toString().contains("rdf")) {
//		    	System.out.println("predicado da norma: "+recurso);	
//		    }		   
//		}
        
        
        //consulta de individuos relacionados a norma que estao relacionados aos predicativos
        String consultaIndividuosRestricao = "SELECT * WHERE " +
        		"{" +
        		"<"+norma+"> ?p ?o ." +
        		"}";
        ResultSet dataSetIndividuos = ontologia.consultar(consultaIndividuosRestricao);
        Resource tipoClasse = null;
        while(dataSetIndividuos.hasNext()) {
		    qs = dataSetIndividuos.next();
		    recurso = qs.getResource("p");
		    if(!recurso.toString().contains("rdf")) {
		    	Resource objeto = qs.getResource("o");
		    	
		    	System.out.print("Predicado da norma é '"+recurso.getLocalName()+"'");
		    	String queryPredicadoConceito = 
		    			"SELECT * WHERE"+
		    			"{"+
		    				"<"+recurso+"> ?p ?o ." +
		    				"FILTER(?p = rdfs:range) }";
		    	QuerySolution solucaoDoPredicado  = ontologia.consultar(queryPredicadoConceito).next();
		    	tipoClasse = solucaoDoPredicado.getResource("o");
		    	System.out.print(" que é uma classe do tipo :"+tipoClasse.getLocalName());
		    	
		    	System.out.println(" apontando para a instancia: "+objeto.getLocalName());
		    	
		    	System.out.println("Portanto a norma a(ao) "+norma.getLocalName()+ " determina "+tipoClasse.getLocalName()+ " = "+objeto.getLocalName() + "\n");
		    }		   
		}
       
        System.exit(0);
	

	}

}
