package br.com.agentdevlaw.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.agentdevlaw.ontology.OntologyConfigurator;

public class OntologyConfiguratorTest {
	
	private OntologyConfigurator ontology;

	public OntologyConfiguratorTest() {
		
	}
	
	@Before
	public void setupTestsMethod() {
		this.ontology = new OntologyConfigurator();
	}
	
	@Test
	public void setOriginModelTest() {
		this.ontology.setOrigin(OntologyConfigurator.MODEL);
		assertNotNull(this.ontology.getSourceModel());
		
	}
	
	@Test
	public void askQueryTest() {
		String askQuery = "ASK" + 
				"{" + 
				"  law:Norm rdfs:subClassOf law:Legislation" + 
				"}";
		assertTrue(this.ontology.askQueries(askQuery));
	}

}
