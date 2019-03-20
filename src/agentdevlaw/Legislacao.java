package agentdevlaw;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;

public class Legislacao {
	
	private OntologiaRemota ontologia;
	private QuerySolution qs = null;
	private int debug = 0;
	
	public OntologiaRemota getOntologia() {
		return ontologia;
	}


	public void setOntologia(OntologiaRemota ontologia) {
		this.ontologia = ontologia;
	}


	public QuerySolution getQs() {
		return qs;
	}


	public void setQs(QuerySolution qs) {
		this.qs = qs;
	}


	public int getDebug() {
		return debug;
	}


	public void setDebug(int debug) {
		this.debug = debug;
	}


	public Legislacao(OntologiaRemota ontologia) {
		this.ontologia = ontologia;
	}
	
	
	/**
	 * @return
	 */
	public ArrayList<Resource> getNormasOntologiaResource(){
		ArrayList<Resource> listaNormas = new ArrayList<Resource>();
		
		String consultaNorma = 
				"SELECT ?x ?c WHERE {" + 
				"	?x rdf:type law:norma ." +
				"	?x rdfs:comment ?c" +
				"}";
		
		ResultSet dataSetNormas = this.ontologia.consultar(consultaNorma);
        Resource norma = null;
        Literal comentario;
        while(dataSetNormas.hasNext()) {
		    this.qs = dataSetNormas.next();
		    norma = this.qs.getResource("x");
		    comentario = this.qs.getLiteral("c");
		    listaNormas.add(norma);   
		}
		
		return listaNormas;
	}
	
	public ArrayList<Lei> getNormasOntologia(){
	
		ArrayList<Lei> listaLeis = new ArrayList<Lei>();
		
		String consultaNorma = 
				"SELECT ?x ?c WHERE {" + 
				"	?x rdf:type law:norma ." +
				"	?x rdfs:comment ?c" +
				"}";
		
		ResultSet dataSetNormas = this.ontologia.consultar(consultaNorma);
        Resource norma = null;
        Literal comentario;
        while(dataSetNormas.hasNext()) {
		    this.qs = dataSetNormas.next();
		    norma = this.qs.getResource("x");
		    comentario = this.qs.getLiteral("c");
		    listaLeis.add(new Lei(norma.getLocalName(), comentario.toString()));
		    
		    if(this.debug > 0) {
		    	System.out.println("A norma: "+norma.getLocalName());
				System.out.println("Descreve que: "+WordUtils.wrap(comentario.toString(), 130, "\n", true)+"\n");
		    }
			
		}
        
        return listaLeis;
		
	}
	
	public ArrayList<Lei> processaLegislacao() {
		
		ArrayList<Lei> listaLeis = this.getNormasOntologia();
		
		for(int i = 0; i < listaLeis.size(); i++ ){
			System.out.println(listaLeis.get(i).getNorma()+"222");
			
			String consultaIndividuosRestricao = "SELECT * WHERE " +
	        		"{ " +
	        		"law:"+listaLeis.get(i).getNorma()+" ?p ?o " +
	        		"}";
	
	        ResultSet dataSetIndividuos = this.ontologia.consultar(consultaIndividuosRestricao);
	        Resource tipoClasse = null;
	        Resource recurso = null;
	        while(dataSetIndividuos.hasNext()) {
	        
			    this.qs = dataSetIndividuos.next();
			    recurso = this.qs.getResource("p");
			    if(!recurso.toString().contains("rdf")) { //truque para considerar somente as propriedades relacionadas ao estudo
			    	Resource objeto = this.qs.getResource("o");
			    	
			    	if(this.debug > 0) System.out.print("Predicado da norma é '"+recurso.getLocalName()+"'");
			    	String queryPredicadoConceito = 
			    			"SELECT * WHERE "+
			    			"{"+
			    				"<"+recurso+"> ?p ?o ." +
			    				"FILTER(?p = rdfs:range) }";
			    	QuerySolution solucaoDoPredicado  = this.ontologia.consultar(queryPredicadoConceito).next();
			    	tipoClasse = solucaoDoPredicado.getResource("o");
			    	listaLeis.get(i).setPredicado(recurso.getLocalName());
			    	listaLeis.get(i).setIndividuo(objeto.getLocalName());
			    	
			    	if(this.debug > 0) {
			    		System.out.print(" que é uma classe do tipo :"+tipoClasse.getLocalName());
				    	System.out.println(" apontando para a instancia: "+objeto.getLocalName());
				    	System.out.println("Portanto a norma a(ao) "+listaLeis.get(i).getNorma()+ " determina "+tipoClasse.getLocalName()+ " = "+objeto.getLocalName() + "\n");
			    	}
			    }		   
			}
		}

		return listaLeis;
	}

}
