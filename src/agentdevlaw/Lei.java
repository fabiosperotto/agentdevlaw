package agentdevlaw;

public class Lei {
	
	private String norma;
	private String descricao;
	private String predicado;
	private String individuo;
	
	public Lei(String nome, String desc, String pred, String ind) {
		this.norma = nome;
		this.descricao = desc;
		this.predicado = pred;
		this.individuo = ind;
	}
	
	public Lei(String nome, String desc) {
		this.norma = nome;
		this.descricao = desc;
	}
	
	public String getNorma() {
		return norma;
	}
	public void setNorma(String norma) {
		this.norma = norma;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getPredicado() {
		return predicado;
	}
	public void setPredicado(String predicado) {
		this.predicado = predicado;
	}
	public String getIndividuo() {
		return individuo;
	}
	public void setIndividuo(String individuo) {
		this.individuo = individuo;
	}

}
