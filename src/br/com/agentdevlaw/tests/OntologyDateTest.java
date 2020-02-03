package br.com.agentdevlaw.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.agentdevlaw.misc.OntologyDate;

public class OntologyDateTest {
	
	public OntologyDateTest() {
		
	}
	
	@Test
	public void createDateFormarTest() {
		assertEquals("2020-01-01T10:20:30", OntologyDate.createDateFormat(2020, 01, 01, 10, 20, 30));
		
	}

}
