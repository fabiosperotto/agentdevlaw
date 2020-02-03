package br.com.agentdevlaw.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A helper class to manipulate test files. This avoids the insertions 
 * of test data in the used official ontology.
 */
public class HelperFile {
	
	private String ontologyNameFile;
	private String ontologyTestsNameFile;
	
	public HelperFile() {
		
	}
	
	public HelperFile(String file, String testsFile) {
		this.ontologyNameFile = file;
		this.ontologyTestsNameFile = testsFile;
	}
	
	public String getOntologyNameFile() {
		return ontologyNameFile;
	}

	public void setOntologyNameFile(String ontologyNameFile) {
		this.ontologyNameFile = ontologyNameFile;
	}

	public String getOntologyTestsNameFile() {
		return ontologyTestsNameFile;
	}

	public void setOntologyTestsNameFile(String ontologyTestsNameFile) {
		this.ontologyTestsNameFile = ontologyTestsNameFile;
	}

	/**
	 * Copy the ontology OWL file to another one to tests purposes
	 * @return true if the ontology file was copied, false otherwise
	 */
	public boolean ontologyFilesToTest() {
		
		InputStream inStream = null;
		OutputStream outStream = null;
			
	    try{
	    	File fileBase = new File(this.ontologyNameFile);
	    	File fileTests = new File(this.ontologyTestsNameFile);	
	    	inStream = new FileInputStream(fileBase);
	    	outStream = new FileOutputStream(fileTests);
	        byte[] buffer = new byte[1024];
	    		
	        int length;
	    	while ((length = inStream.read(buffer)) > 0){
	    		outStream.write(buffer, 0, length);
	    	}
	    	 
	    	inStream.close();
	    	outStream.close();
	    	return true;
	    	
	    }catch(IOException e){
	    	System.out.println("The ontolgy file is not available!");
	    	e.printStackTrace();
	    }
	    return false;
	}
	
	
	public boolean deleteOntologyTestsFile() {
		File testsFile = new File(this.ontologyTestsNameFile);
		return testsFile.delete();
	}

}
