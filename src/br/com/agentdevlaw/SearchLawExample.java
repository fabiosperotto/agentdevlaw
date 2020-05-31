package br.com.agentdevlaw;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import br.com.agentdevlaw.legislation.Consequence;
import br.com.agentdevlaw.legislation.Law;
import br.com.agentdevlaw.legislation.Norm;
import br.com.agentdevlaw.middleware.QueryProcess;
import br.com.agentdevlaw.ontology.OntologyConfigurator;

public class SearchLawExample {

	public static void main(String[] args){
		
		OntologyConfigurator ontology = new OntologyConfigurator();
		ontology.setOrigin(OntologyConfigurator.MODEL);
		
		QueryProcess middleware = new QueryProcess(ontology);
		middleware.setDebug(0);
	
		
		String action = "hunting animals and exports skins";	
		List<Law> laws =  middleware.searchAction(action, "");
		
		
		if(laws.isEmpty()) {
			System.out.println("Theres no results for agent's action '" + action + "'");
			
		}else {
			for(int i = 0; i < laws.size(); i++) {
				System.out.println("\nLaw -> " + laws.get(i).getIndividual());
				System.out.println("Description -> " + WordUtils.wrap(laws.get(i).getDescription(), 70, "\n", true));
				
				System.out.println(laws.get(i).getTextSimilarity()+"%");
				
				List<Norm> norms = laws.get(i).getNorms();
				System.out.println("Norm -> " + norms.get(0).getIndividual());
				for(int j = 0; j < norms.size(); j++) {
					
					System.out.print("Norm consequence -> " + norms.get(j).getConsequence() + " (" + norms.get(j).getConsequenceType() + "), ");
					
					List<Consequence> consequences = middleware.getConsequenceValues(norms.get(j));
					if(consequences.isEmpty()) {
						
						System.out.println("The consequence "+ norms.get(j).getConsequence() + " don't have property values");
						
					}else {
						System.out.println("The consequence "+ norms.get(j).getConsequence() +" has properties values: ");
						for(int w = 0; w < consequences.size(); w++) {
							System.out.print(
									"Variable = "+ consequences.get(w).getType() + 
									", Data = "+ consequences.get(w).getValue() +
									", Data type = "+ consequences.get(w).getValueType() + "\n"
									);
						}
					}					
				}
			}
		}
	}

}
