package br.com.agentdevlaw;

import java.util.ArrayList;
import java.util.List;

import br.com.agentdevlaw.legislation.Law;
import br.com.agentdevlaw.legislation.Norm;
import br.com.agentdevlaw.middleware.QueryProcess;
import br.com.agentdevlaw.misc.OntologyDate;
import br.com.agentdevlaw.ontology.OntologyConfigurator;

public class NewLawExample {

	public static void main(String[] args) {
		
		OntologyConfigurator ontology = new OntologyConfigurator();
		ontology.setOrigin(OntologyConfigurator.MODEL);
		QueryProcess middleware = new QueryProcess(ontology);
		middleware.setDebug(1);
		
		//To create new law, fill the law object and its norms list in 
		//order to data can be understood by the ontology model provided
		Law newLaw = new Law("law-8899", "Mining is prohibited throughout the national territory");
		//initial date or the publish date of the law:
		newLaw.setStartDate(OntologyDate.createDateFormat(1988, 02, 12, 00, 00, 00));
		//it law has an expiration or date limit, determine like below
		//newLaw.setEndDate(OntologyDate.createDateFormat(2021, 02, 12, 00, 00, 00));
		
		List<String> actions = new ArrayList<String>(); //you can add multiples actions to be regulated
		actions.add("mining");
		actions.add("extraction");
		actions.add("extracts");
		newLaw.setActions(actions);
		
		List<Norm> norms = new ArrayList<Norm>();
		//the article 2 of 8899 law determine the fine payment between 200 e 300 units
		//attention about PayAFine, check about all consequences  and norms concepts 
		//before the programming, there is a risk of not registering your new 
		//definitions in the ontology
		Norm newNorm = new Norm("8899_article-2", "pay-a-fine_200-300", "PayAFine");
		newNorm.setRole("allRoles"); //optional, if empty allRoles is the default

		norms.add(newNorm); //more than one can be created:
		
		newNorm = new Norm("8899_article-2", "equipment-loss", "Seizure");
		newNorm.setRole("allRoles");
		norms.add(newNorm);
		
		newLaw.setNorms(norms); //insert the norms list inside the new law
			
		if(middleware.insertNewLaw(newLaw)) {
			System.out.println("New law inserted");
		}
	}
}