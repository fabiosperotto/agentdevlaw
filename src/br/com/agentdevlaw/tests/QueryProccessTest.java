package br.com.agentdevlaw.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.agentdevlaw.middleware.QueryProcess;
import br.com.agentdevlaw.misc.OntologyDate;
import br.com.agentdevlaw.ontology.OntologyConfigurator;
import br.com.agentdevlaw.legislation.Consequence;
import br.com.agentdevlaw.legislation.Law;
import br.com.agentdevlaw.legislation.Norm;

public class QueryProccessTest {
	
	private OntologyConfigurator ontology;
	private QueryProcess middleware;
	private static HelperFile helperFile;
	

	public QueryProccessTest() {
		
	}
	
	@BeforeClass
	public static void setupOntologyBase() {
		
		QueryProccessTest.helperFile = new HelperFile("ontologies/agentdevlaw_english_base.owl", "ontologies/agentdevlaw_english_tests.owl");
		QueryProccessTest.helperFile.ontologyFilesToTest();
	}
	
	@AfterClass
	public static void finishTests() {
		
		QueryProccessTest.helperFile.deleteOntologyTestsFile();
	}
	
	@Before
	public void instanceSetup() {
		ontology = new OntologyConfigurator();
		ontology.setOrigin(OntologyConfigurator.MODEL);
		ontology.setEndpoint("ontologies/agentdevlaw_english_tests.owl");
		ontology.setEndpointQuery(ontology.getEndpoint());
		middleware = new QueryProcess(ontology);
	}
	
	
	@Test
	public void insertNewLawTest() {
		
		Law newLaw = new Law("law-1234", "Example law with activity regulations");
		newLaw.setStartDate(OntologyDate.createDateFormat(1988, 02, 12, 00, 00, 00));
		List<Norm> norms = new ArrayList<Norm>();
		Norm newNorm = new Norm("1234-_article-X", "pay-a-fine_200-300", "PayAFine");
		newNorm.setRole("someone");
		norms.add(newNorm); 
		newLaw.setNorms(norms);
		
		assertTrue(this.middleware.insertNewLaw(newLaw));
	}
	

	@Test
	public void searchActionTest(){
		 
		List<Law> laws = new ArrayList<Law>();
		assertEquals(laws, this.middleware.searchAction("activity", "someone"));
		 
	}
	 
	@Test
	public void getConsequenceValuesTest() {
		
		Norm norm = new Norm("individual", "consequencue", "consequenceType");
		List<Consequence> consequences = new ArrayList<Consequence>();
		assertEquals(consequences, this.middleware.getConsequenceValues(norm));
	}
	
	@Test
	public void createNewIndivdualTest() {
		assertTrue(this.middleware.createIndividual("newindividual", "Role"));
	}
	

}
