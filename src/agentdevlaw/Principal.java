package agentdevlaw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

	public static void main(String[] args){
		
		OntologiaRemota ontologia = new OntologiaRemota("http://localhost:3030/ds/query", "http://www.semanticweb.org/fsp/ontologies/2018/11/agentdevlaw");
		
		Legislacao legislacao = new Legislacao(ontologia);
		legislacao.setDebug(1);
		ArrayList<Lei> leis = legislacao.processaLegislacao();
		
		for(int i = 0; i < leis.size(); i++) {
			System.out.println(leis.get(i).getNorma());
			System.out.println(leis.get(i).getPredicado());
			System.out.println(leis.get(i).getIndividuo());
			System.out.println("###");
		}
	

	}

}
