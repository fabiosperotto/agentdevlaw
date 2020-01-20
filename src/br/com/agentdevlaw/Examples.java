package br.com.agentdevlaw;

import java.util.List;

import br.com.agentdevlaw.legislation.Consequence;
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
		
//		List<Law> laws =  middleware.searchAction(action, "fisherman");
//		
//		
//		if(laws.isEmpty()) {
//			System.out.println("Theres no results for agent action '" + action + "'");
//			
//		}else {
//			for(int i = 0; i < laws.size(); i++) {
//				System.out.println("Lei -> " + laws.get(i).getIndividual());
//				List<Norm> norms = laws.get(i).getNorms();
//				System.out.println("Norma -> " + norms.get(0).getIndividual());
//				for(int j = 0; j < norms.size(); j++) {
//					
//					System.out.print("Consequencia da norma -> " + norms.get(j).getConsequence() + " (" + norms.get(j).getConsequenceType() + "), ");
//					
//					List<Consequence> consequences = middleware.getConsequenceValues(norms.get(j));
//					if(consequences.isEmpty()) {
//						
//						System.out.println("A consequencia "+ norms.get(j).getConsequence() + "não possui valores de propriedade");
//						
//					}else {
//						System.out.println("A consequencia "+ norms.get(j).getConsequence() +" possui como valores: ");
//						for(int w = 0; w < consequences.size(); w++) {
//							System.out.print(
//									"Variavel = "+ consequences.get(w).getType() + 
//									", Valor = "+ consequences.get(w).getValue() +
//									", Tipo do Valor = "+ consequences.get(w).getValueType() + "\n"
//									);
//						}
//					}
//					
//				}
//			}
			if(middleware.insertNewLaw()) {
				System.out.println("Insert realizado com sucesso");
			}
//		}
		
		
	

	}

}
