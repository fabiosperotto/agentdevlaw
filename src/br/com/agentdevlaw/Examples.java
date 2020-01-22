package br.com.agentdevlaw;

import java.util.ArrayList;
import java.util.List;

import br.com.agentdevlaw.legislation.Consequence;
import br.com.agentdevlaw.legislation.Law;
import br.com.agentdevlaw.legislation.Norm;
import br.com.agentdevlaw.middleware.QueryProcess;
import br.com.agentdevlaw.misc.OntologyDate;
import br.com.agentdevlaw.ontology.OntologyConfigurator;

public class Examples {

	public static void main(String[] args){
		
		OntologyConfigurator ontology = new OntologyConfigurator();
		ontology.setOrigin(OntologyConfigurator.MODEL);
		
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
//		}
		
		
//		law:law-88 rdf:type law:Legislation .
//		law:law-88 rdfs:comment 'teste' . 
//	  	law:law-88 law:starts_at '1988-02-12T00:00:00' .
//	  	law:88-article-2 rdf:type law:Norm .
//	  	law:88-article-2 law:relates law:allRoles .
//	  	law:law-88 law:specificiedBy law:88-article-2 .
//	  	law:88-article-2 law:apply law:pay-a-fine-5-20 .
		Law novaLei = new Law("law-88", "teste");
		novaLei.setStartDate(OntologyDate.createDateFormat(1988, 02, 12, 00, 00, 00));
//		novaLei.setEndDate(OntologyDate.createDateFormat(2021, 02, 12, 00, 00, 00));
//		private Law law;
//		private String individual;
//		private String consequence;
//		private String consequenceType;
		List<Norm> normas = new ArrayList<Norm>();
		Norm novaNorma = new Norm("88-article-2", "pay-a-fine_200-300", "PayAFine");
		novaNorma.setRole("allRoles");
		normas.add(novaNorma);
		
		novaLei.setNorms(normas);
		
		if(middleware.insertNewLaw(novaLei)) {
			System.out.println("Insert realizado com sucesso");
		}

	}

}
