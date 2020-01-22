package br.com.agentdevlaw.legislation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The Law class has the descriptions of each norm. Having previously stored, 
 * for each instance of the norm, the predicates and related individuals, without 
 * the need to make new queries to the ontology.
 * 
 */
public class Law {

	private String description;
	private String individual;
	protected List<Norm> norms;
	private String startDate;
	private String endDate = null; //sometimes a law is always valid or not expire ;)

	public Law() {

	}

	public Law(String individual, String description) {
		this.individual = individual;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIndividual() {
		return individual;
	}

	public void setIndividual(String individual) {
		this.individual = individual;
	}

	public List<Norm> getNorms() {
		return norms;
	}

	public void setNorms(List<Norm> norms) {
		this.norms = norms;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	

}
