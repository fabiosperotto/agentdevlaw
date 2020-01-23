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
		
		String action = "pesticides";
		
		List<Law> laws =  middleware.searchAction(action, "");
		
		
		if(laws.isEmpty()) {
			System.out.println("Theres no results for agent action '" + action + "'");
			
		}else {
			for(int i = 0; i < laws.size(); i++) {
				System.out.println("Lei -> " + laws.get(i).getIndividual());
				List<Norm> norms = laws.get(i).getNorms();
				System.out.println("Norma -> " + norms.get(0).getIndividual());
				for(int j = 0; j < norms.size(); j++) {
					
					System.out.print("Consequencia da norma -> " + norms.get(j).getConsequence() + " (" + norms.get(j).getConsequenceType() + "), ");
					
					List<Consequence> consequences = middleware.getConsequenceValues(norms.get(j));
					if(consequences.isEmpty()) {
						
						System.out.println("A consequencia "+ norms.get(j).getConsequence() + "não possui valores de propriedade");
						
					}else {
						System.out.println("A consequencia "+ norms.get(j).getConsequence() +" possui como valores: ");
						for(int w = 0; w < consequences.size(); w++) {
							System.out.print(
									"Variavel = "+ consequences.get(w).getType() + 
									", Valor = "+ consequences.get(w).getValue() +
									", Tipo do Valor = "+ consequences.get(w).getValueType() + "\n"
									);
						}
					}
					
				}
			}
		}
		
		//Para criar uma nova lei, preencha os objetos Law e sua lista de normas
		//para que os dados possam ser compreendidos na ontologia legal fornecida
		Law novaLei = new Law("law-88", "description about law");
		//data inicial ou de publicacao da lei:
		novaLei.setStartDate(OntologyDate.createDateFormat(1988, 02, 12, 00, 00, 00));
		//se a lei possuir um prazo de validade/expiracao ou periodo final, informar conforme abaixo
		//novaLei.setEndDate(OntologyDate.createDateFormat(2021, 02, 12, 00, 00, 00));
		List<Norm> normas = new ArrayList<Norm>();
		//o artigo 2 da lei 88 determina o pagamento de multa entre 200 e 300 unidades
		//atencao sobre o PayAFine, verifique todos os conceitos de consequencias de normas 
		//na ontologia antes da programacao, corre o risco de nao registrar suas novas
		//definicoes na ontologia
		Norm novaNorma = new Norm("88-article-2", "pay-a-fine_200-300", "PayAFine");
		novaNorma.setRole("testRole"); //isto e opcional, allRoles e o papel padrao relacionado
		
		normas.add(novaNorma); //mais de uma norma pode ser criada
		
		novaLei.setNorms(normas); //inserir a lista de normas para a  nova lei
		
		if(middleware.insertNewLaw(novaLei)) {
			System.out.println("New law inserted");
		}

	}

}
