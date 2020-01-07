package br.com.agentdevlaw.legislation;

/**
 * This class is used to process values from indivudual concepts with values properties 
 * like values to pay a fine, a integer about prison years and others. The valueType attribute 
 * is needed to explain about XSD datatypes  from OWL/RDF ontology (float, integer, datetime 
 * and others).
 *
 */
public class Consequence {
	
	private String individualName;
	private String type;
	private String value;
	private String valueType;
	
	public Consequence(String individual, String type, String val, String valType) {
		this.individualName = individual;
		this.type = type;
		this.value = val;
		this.valueType = valType;
	}

	public String getIndividualName() {
		return individualName;
	}

	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	
	
	

}
