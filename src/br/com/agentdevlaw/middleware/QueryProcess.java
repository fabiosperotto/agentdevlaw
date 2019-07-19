package br.com.agentdevlaw.middleware;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;

import br.com.agentdevlaw.legislation.Law;
import br.com.agentdevlaw.legislation.Norm;
import br.com.agentdevlaw.ontology.OntologyConfigurator;

public class QueryProcess {
	
	private OntologyConfigurator ontology;
	private QuerySolution qs = null;
	private int debug = 0;
	
	public OntologyConfigurator getOntology() {
		return ontology;
	}


	public void setOntologia(OntologyConfigurator ontology) {
		this.ontology = ontology;
	}


	public QuerySolution getQs() {
		return qs;
	}


	public void setQs(QuerySolution qs) {
		this.qs = qs;
	}


	public int getDebug() {
		return debug;
	}


	/**
	 * @param debug um inteiro que se for maior que 0 (zero) define o nivel de apuracao de dados na tela
	 */
	public void setDebug(int debug) {
		this.debug = debug;
	}


	public QueryProcess(OntologyConfigurator ontologia) {
		this.ontology = ontologia;
	}
	
	
	/**
	 * Builds the intended query related to the base ontology model (has particularities from model)
	 * @param actionFilter String with a expression to filter among many laws
	 * @return String with query structured
	 */
	private String modelLegislationQuery(String actionFilter) {
		return "SELECT * " + 
			"WHERE {  " + 
			"	{   " + 
			"    	?law rdf:type law:Legislation . " + 
			"    	?law rdfs:comment ?c . " + 
			"    	?law law:starts_at ?starts_at. " + 
			"    	OPTIONAL { ?law law:ends_at ?ends_at } . " + 
			"   		FILTER(\"2019-11-18T16:50:00\"^^xsd:dateTime >= ?starts_at ) . " + 
			"    	?law ?p ?line . " + 
			"    	?line law:apply ?consequences . " + 
			"    	?line law:relates ?roles . " + 
			"    	?consequences rdf:type ?type .  " + 
			"	} " + 
			"		UNION { " + 
			"			?law law:ends_at ?ends_at . " + 
			"			FILTER(\"2019-11-18T16:50:00\"^^xsd:dateTime >= ?starts_at && \"2019-11-18T16:50:00\"^^xsd:dateTime < ?ends_at ) . " + 
			" 		}  " + 
			"	FILTER regex(?c, \""+actionFilter+"\", \"i\") . " + 
			"	FILTER(?roles = law:fisherman) . " + 
			"	FILTER(?p = law:specifiedBy) . " + 
			"	FILTER(?type != owl:NamedIndividual) . " + 
			" } ";
	}
	
	
	/**
	 * This method build a list of laws related to an action agent
	 * @param action string with agent action to filter in ontology inside description laws
	 * @return List<Law>
	 */
	public List<Law> searchAction(String action) {
		
		if(this.debug > 0) System.out.println("### \nSearching in all laws for action '" + action + "'\n###\n");
		String query = this.modelLegislationQuery(action);
		
		ResultSet dataSet = this.ontology.setup(query);
		Resource legislation = null;
		Literal ends = null;
		XSDDateTime initialDate, finalDate = null;
		String lastLaw = "";
		List<Law> laws = new ArrayList<Law>();
		
		
		while(dataSet.hasNext()) {
			this.qs = dataSet.next();
			legislation = this.qs.getResource("law");
			Law law = new Law();
			List<Norm> norms = new ArrayList<Norm>();
			
			if(!legislation.getLocalName().equals(lastLaw)) {
				
				lastLaw = legislation.getLocalName();
				
				ends = this.qs.getLiteral("ends_at");
				initialDate = (XSDDateTime) this.qs.getLiteral("starts_at").getValue();
				
				if(ends != null) finalDate = (XSDDateTime) ends.getValue();
				
				law.setIndividual(legislation.getLocalName());
				law.setDescription(this.qs.getLiteral("c").toString());
				law.setNorms(new ArrayList<Norm>());
				laws.add(law);
				
				if(this.debug > 0) {
					System.out.println("Legislation: " + legislation.getLocalName());
					System.out.println("Description of law: " + WordUtils.wrap(this.qs.getLiteral("c").toString(), 115, "\n", true));
					System.out.print("Starts at: " + initialDate.getDays() + "/" + initialDate.getMonths() + "/" + initialDate.getYears() + " "+ initialDate.timeLexicalForm());
					if(ends != null) System.out.print(" ends at: " + finalDate.getDays() + "/" + finalDate.getMonths() + "/" + finalDate.getYears() + " " + finalDate.timeLexicalForm());
					System.out.println();
				}
					
			}
			
			norms = laws.get(laws.size()-1).getNorms();
			Norm norm = new Norm(this.qs.getResource("line").getLocalName(), this.qs.getResource("consequences").getLocalName(), this.qs.getResource("type").getLocalName()); 
			norms.add(norm);
			laws.get(laws.size()-1).setNorms(norms);
			
			if(this.debug > 0) {
				System.out.print("Specified by " + norm.getIndividual());
				System.out.println(" and has the consequence of '" + norm.getConsequence() + "' type of " + norm.getConsequenceType());
			}
		}
		
		return laws;
	}

}
