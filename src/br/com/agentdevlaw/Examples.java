package br.com.agentdevlaw;

import java.util.List;

import br.com.agentdevlaw.legislation.Law;
import br.com.agentdevlaw.legislation.Norm;
import br.com.agentdevlaw.middleware.QueryProcess;
import br.com.agentdevlaw.ontology.OntologyConfigurator;

public class Examples {

	public static void main(String[] args){
		
		OntologyConfigurator ontology = new OntologyConfigurator();
		ontology.setOrigin(OntologyConfigurator.SERVER);
		
		QueryProcess middleware = new QueryProcess(ontology);
		middleware.setDebug(1);
		
		String action = "fish";
		
		List<Law> laws =  middleware.searchAction(action, "fisherman");
		
		if(laws.isEmpty()) {
			System.out.println("Theres no results for agent action '" + action + "'");
			
		}else {
			for(int i = 0; i < laws.size(); i++) {
				System.out.println("Lei -> " + laws.get(i).getIndividual());
				List<Norm> norms = laws.get(i).getNorms();
				System.out.println("Norma -> " + norms.get(0).getIndividual());
				for(int j = 0; j < norms.size(); j++) {
					
					System.out.print("Consequencia da norma -> " + norms.get(j).getConsequence() + " (" + norms.get(j).getConsequenceType() + "), ");
				}
			}
		}
		
		
	

	}

}
