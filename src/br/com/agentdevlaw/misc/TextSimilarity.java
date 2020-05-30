package br.com.agentdevlaw.misc;

import org.apache.commons.text.similarity.CosineDistance;

public class TextSimilarity {
	
	public double CosineDistance(String base, String text) {
		double cosineDistanceOfGravitDefinitions = new CosineDistance().apply(base, text);
		return Math.round( (1 - cosineDistanceOfGravitDefinitions) * 100);
		
	}

}
