package br.com.agentdevlaw.middleware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.util.FileManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.github.owlcs.ontapi.OntManagers;
import com.github.owlcs.ontapi.Ontology;
import com.github.owlcs.ontapi.OntologyManager;

import br.com.agentdevlaw.legislation.Consequence;
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
	 * Define the debug message work level of system. When enabled, messages are printed with 
	 * information about the middleware internal process.
	 * @param debug int 0 to disabled, 1 to enable
	 */
	public void setDebug(int debug) {
		this.debug = debug;
	}


	public QueryProcess(OntologyConfigurator ontologia) {
		this.ontology = ontologia;
	}
	
	/**
	 * It's necessary use only dates accepted in xsd schema (supported by OWL/RDF ontologies), 
	 * this function returns the present datetime in xsd format.
	 * @return
	 */
	protected String dateSchemaNow() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String now = date.format(formatter);
		now = now.replace(" ", "T");
		return now;
	}
	
	
	/**
	 * Builds the intended query related to the base ontology model (has particularities from model)
	 * @param actionFilter String with a expression to filter among many laws]
	 * @param agentRole string with the agent type (provide empty string if this doesn't matter
	 * @return String with query structured
	 */
	private String modelLegislationQuery(String actionFilter, String agentRole) {
		
		String now = this.dateSchemaNow();
		String agentRoleFilter = "FILTER(?roles = law:allRoles) .";
		if(!agentRole.isEmpty()) {
			agentRoleFilter = "FILTER(?roles = law:" + agentRole + " || ?roles = law:allRoles) .";
		}
		 
		return "SELECT * " + 
			"WHERE {  " + 
			"	{   " + 
			"    	?law rdf:type law:Legislation . " + 
			"    	?law rdfs:comment ?description . " + 
			"    	?law law:starts_at ?starts_at. " + 
			"    	OPTIONAL { ?law law:ends_at ?ends_at } . " + 
			"   		FILTER(\""+ now +"\"^^xsd:dateTime >= ?starts_at ) . " + 
			"    	?law ?p ?condition . " + 
			" 		OPTIONAL { ?condition rdfs:comment ?cond_desc } . " +
			"    	?condition law:apply ?consequences . " + 
			"    	?condition law:relates ?roles . " + 
			"    	?consequences rdf:type ?type .  " + 
			"	} " + 
			"		UNION { " + 
			"			?law law:ends_at ?ends_at . " + 
			"			FILTER(\"" + now + "\"^^xsd:dateTime >= ?starts_at && \"" + now + "\"^^xsd:dateTime < ?ends_at ) . " + 
			" 		}  " + 
			"	FILTER regex(?description, \""+actionFilter+"\", \"i\") . " + 
				agentRoleFilter +
			"	FILTER(?p = law:specifiedBy) . " + 
			"	FILTER(?type != owl:NamedIndividual) . " + 
			" } ";
	}
	
	
	/**
	 * This method build a list of laws related to an action agent
	 * @param action string with agent action to filter in ontology inside description laws
	 * @param role string with the agent type (provide empty string if this doesn't matter
	 * @return List list of laws found in ontology
	 */
	public List<Law> searchAction(String action, String role) {
		
		if(this.debug > 0) System.out.println("### \nSearching in all laws for action '" + action + " for the agent type " + role + "'\n###\n");
		String query = this.modelLegislationQuery(action, role);

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
				law.setDescription(this.qs.getLiteral("description").toString());
				law.setNorms(new ArrayList<Norm>());
				laws.add(law);
				
				if(this.debug > 0) {
					System.out.println("Legislation: " + legislation.getLocalName());
					System.out.println("Description of law: " + WordUtils.wrap(this.qs.getLiteral("description").toString(), 115, "\n", true));
					System.out.print("Starts at: " + initialDate.getDays() + "/" + initialDate.getMonths() + "/" + initialDate.getYears() + " "+ initialDate.timeLexicalForm());
					if(ends != null) System.out.print(" ends at: " + finalDate.getDays() + "/" + finalDate.getMonths() + "/" + finalDate.getYears() + " " + finalDate.timeLexicalForm());
					System.out.println();
				}
					
			}
			
			norms = laws.get(laws.size()-1).getNorms();
			Norm norm = new Norm(this.qs.getResource("condition").getLocalName(), this.qs.getResource("consequences").getLocalName(), this.qs.getResource("type").getLocalName()); 
			norms.add(norm);
			laws.get(laws.size()-1).setNorms(norms);
			
			if(this.debug > 0) {
				System.out.print("Specified by " + norm.getIndividual());
				System.out.println(" and has the consequence of '" + norm.getConsequence() + "' type of " + norm.getConsequenceType());
			}
		}
		
		return laws;
	}
	
	
	/**
	 * Get values from norms consequences individual. Check about properties inside sanctions and 
	 * returns something like values to pay a fine and other types of sanctions. Can return a 
	 * empty list if the individual of sanction don't have properties values.  
	 * @param norm Object of Norm with result values.
	 * @return List Consequence
	 */
	public List<Consequence> getConsequenceValues(Norm norm) {
		
		
		if(this.debug > 0) System.out.println("\n###\n Finding values from norm consequence "+ norm.getConsequence() +" \n###\n");
		
		String query = "SELECT ?type ?plain_value ?value_type \n" + 
				"	WHERE { \n" + 
				"	law:"+ norm.getConsequence() +" ?type ?value \n" + 
				"	BIND(STR(?value) AS ?plain_value)\n" + 
				"	BIND(datatype(?value) as ?value_type)\n" + 
				"	FILTER(?type != rdf:type)\n" + 
				"	}";
		
		List<Consequence> consequences = new ArrayList<Consequence>();
	
		ResultSet dataSet = this.ontology.setup(query);
		while(dataSet.hasNext()) {
			this.qs = dataSet.next();
			Consequence consequence = new Consequence(norm.getConsequence(), 
					this.qs.getResource("type").getLocalName(), 
					this.qs.getLiteral("plain_value").getString(), 
					this.qs.getResource("value_type").getLocalName()
					);
			consequences.add(consequence);			
		}
		
		return consequences;
	}
	
	/**
	 * Insert a new law inside to the ontology
	 * @param newLaw object with all data about the law and it norms
	 * @return true if law was inserted, false otherwise
	 */
	public boolean insertNewLaw(Law newLaw) {
		
		String query = this.prepareQueryNewLaw(newLaw);
		if(this.debug > 0) System.out.println("Query about a new law is ready to dispatch");
		return this.dispatchUpdate(query);
       
	}
	
	/**
	 * This function mount a SPARQL query related to create one new law with the specifications 
	 * like dates, norms, consequences.
	 * @param newLaw object of Law
	 * @return String with a ready to go SPARQL query
	 */
	protected String prepareQueryNewLaw(Law newLaw) {
		
		String query = "INSERT DATA\n" + 
				"{" + 
				"	law:"+ newLaw.getIndividual() +" rdf:type law:Legislation ." + 
				"	law:"+ newLaw.getIndividual() +" rdfs:comment '"+ newLaw.getDescription() +"' . " + 
				"  	law:"+ newLaw.getIndividual() +" law:starts_at '"+ newLaw.getStartDate() +"'^^xsd:dateTime .";
		
		if(newLaw.getEndDate() != null) {
			query += "  law:"+ newLaw.getIndividual() +" law:ends_at '"+ newLaw.getEndDate() +"' .";
		}
		
		List<Norm> norms = newLaw.getNorms();
		
		if(norms.size() > 0) {
			
			for(int i = 0; i < norms.size(); i++) {
				
				String ask = "ASK" + 
						"{" + 
						"  law:"+ norms.get(i).getRole() +" rdf:type law:Role .\n" + 
						"}";
				if(!this.ontology.askQueries(ask)) {
					this.createIndividual(norms.get(i).getRole(), "Role");
					if(this.debug > 0) System.out.println("Creating new role "+norms.get(i).getRole());
				}
				
				ask = "ASK" + 
					"{" + 
					"  law:"+ norms.get(i).getConsequence() +" rdf:type law:"+ norms.get(i).getConsequenceType() +" .\n" + 
					"}";
				if(!this.ontology.askQueries(ask)) {
					this.createIndividual(norms.get(i).getConsequence(), norms.get(i).getConsequenceType());
					if(this.debug > 0) System.out.println("Creating new individual "+ norms.get(i).getConsequence() +" of type "+norms.get(i).getConsequenceType());
				}
				
				query += "  law:"+norms.get(i).getIndividual()+" rdf:type law:Norm ."
					
						+ "  law:"+norms.get(i).getIndividual()+" law:relates law:"+ norms.get(i).getRole() +" ."
						+ "  law:"+ newLaw.getIndividual() +" law:specifiedBy law:"+ norms.get(i).getIndividual() +" ."
						+ "	 law:"+ norms.get(i).getIndividual() +" law:apply law:"+ norms.get(i).getConsequence() +" .";
			}
		}
		
		query += "}";
		
		return query;
	}
	
	/**
	 * Create a new individual inside de ontology. Check previously the ontology structure for valid concepts
	 * @param individualName string with the name of a new individual
	 * @param individualConcept string with the concept name of that same individual.
	 * @return true if ontology was updated, false otherwise
	 */
	public boolean createIndividual(String individualName, String individualConcept) {
		
		String query = "INSERT DATA\n" + 
				"{" + 
				"	law:"+ individualName +" rdf:type law:"+ individualConcept +" ." + 
				"}";
		
		return this.dispatchUpdate(query);
		
	}
	
	/**
	 * Dispatch the update query type to the origin ontology (sparql service or file) 
	 * @param query String with the update query
	 * @return true if the query was successful dispatch, false otherwise 
	 */
	private boolean dispatchUpdate(String query) {
		
		if(this.ontology.getOrigin() == OntologyConfigurator.MODEL) {
			
			FileManager.get().open(this.ontology.getEndpoint());
			
			OntologyManager m = OntManagers.createONT();
			File fileout = new File(this.ontology.getEndpoint());
			try {
				Ontology o = m.loadOntologyFromOntologyDocument(fileout);
				
				UpdateAction.parseExecute(this.ontology.getQueryPrefix() + query,
	            o.asGraphModel());
				
				o.saveOntology(new FileOutputStream(fileout));
				
				return true;
			} catch (OWLOntologyCreationException | OWLOntologyStorageException | FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}
		
		if(this.ontology.getOrigin() == OntologyConfigurator.SERVER) {
			if(this.debug > 0) System.out.println("Updating on SPARQL webservice");
			return this.ontology.setupUpdate(query);
			
		}
		
		return false;
		
	}
	

}
