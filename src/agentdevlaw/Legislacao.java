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


	/**
	 * @param debug um inteiro que se for maior que 0 (zero) define o nivel de apuracao de dados na tela
	 */
	public void setDebug(int debug) {
		this.debug = debug;
	}


	public Legislacao(OntologiaRemota ontologia) {
		this.ontologia = ontologia;
	}
	
	
	/** 
	 * Realiza pesquisa SPARQL na ontologia relatando todas as intancias de normas com a propriedade de comentario 
	 * (texto descritivo da norma)
	 * @return ArrayList<Resource> contendo conjunto de normas como recurso da ontologia
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
	
	
	/**
	 * Realiza pesquisa SPARQL na ontologia relatando todas as intancias de normas com a propriedade de comentario 
	 * (texto descritivo da norma). Neste metodo o retorno e uma lista com objetos Lei.
	 * @return ArrayList<Lei> lista de leis contendo informacoes completas de predicados e individuos de cada lei
	 */
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
	
	
	/**
	 * Realiza consultas SPARQL a fim de recuperar os predicados e individuos (instancias) relacionadas que regram 
	 * determinada norma, constituindo neste sentido a lei para o sistema.
	 * @return ArrayList<Lei> contendo todas as formalizacoes encontradas
	 */
	public ArrayList<Lei> processaLegislacao() {
		
		ArrayList<Lei> listaNormas = this.getNormasOntologia();
		ArrayList<Lei> listaLeis = new ArrayList<Lei>();
		
		for(int i = 0; i < listaNormas.size(); i++ ){
			
			String consultaIndividuosRestricao = "SELECT * WHERE " +
	        		"{ " +
	        		"law:"+listaNormas.get(i).getNorma()+" ?p ?o " +
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
			    	Lei lei = new Lei(listaNormas.get(i).getNorma(), listaNormas.get(i).getDescricao());
			    	lei.setPredicado(recurso.getLocalName());
			    	lei.setIndividuo(objeto.getLocalName());
			    	listaLeis.add(lei);
			    	
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
