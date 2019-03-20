package agentdevlaw;


import java.util.ArrayList;

public class Principal {

	public static void main(String[] args){
		
		OntologiaRemota ontologia = new OntologiaRemota("http://localhost:3030/ds/query", "http://www.semanticweb.org/fsp/ontologies/2018/11/agentdevlaw");
		
		Legislacao legislacao = new Legislacao(ontologia);
		legislacao.setDebug(1);
		ArrayList<Lei> leis = legislacao.carregaLegislacao("pescar");
		
		for(int i = 0; i < leis.size(); i++) {
			System.out.println(leis.get(i).getNorma());
			System.out.println(leis.get(i).getPredicado());
			System.out.println(leis.get(i).getIndividuo());
			System.out.println("###");
		}
	

	}

}
