package br.com.agentdevlaw.legislation;

public class Norm {

	private Law law;
	private String individual;
	private String consequence;
	private String consequenceType;
	private String role;

	public Norm() {

	}

	public Norm(String individual, String consequence, String consequenceType) {
		this.individual = individual;
		this.consequence = consequence;
		this.consequenceType = consequenceType;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIndividual() {
		return individual;
	}

	public void setIndividual(String individual) {
		this.individual = individual;
	}

	public Law getLaw() {
		return law;
	}

	public void setLaw(Law law) {
		this.law = law;
	}

	public String getConsequence() {
		return consequence;
	}

	public void setConsequence(String consequence) {
		this.consequence = consequence;
	}

	public String getConsequenceType() {
		return consequenceType;
	}

	public void setConsequenceType(String consequenceType) {
		this.consequenceType = consequenceType;
	}

}
