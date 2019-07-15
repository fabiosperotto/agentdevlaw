package br.com.agentdevlaw.legislation;

import java.util.List;

/**
 * A classe Lei é em um sentido da norma da ontologia processada onde possuimos
 * em um objeto as descricoes de cada norma. Podendo ja ter armazenado
 * previamente, para cada instancia de norma, os predicados e individuso
 * relacionados, sem a necessidade de retornar a realizar novas consultas à
 * ontologia.
 * 
 * @author fabiosperotto
 *
 */
public class Law {

	private String description;
	private String individual;
	protected List<Norm> norms;

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

}
