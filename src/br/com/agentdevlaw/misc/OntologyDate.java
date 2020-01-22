package br.com.agentdevlaw.misc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OntologyDate {
	
	/**
	 * This is responsible to provide a well formatted string about timestamp accepted by 
	 * XSD datetime ontology
	 * @param year int
	 * @param month int
	 * @param day int
	 * @param hour int
	 * @param minute int
	 * @param seconds int
	 * @return String with formatted date
	 */
	public static String createDateFormat(int year, int month, int day, int hour, int minute, int seconds) {
		
		LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute, seconds);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dateFormated = date.format(formatter);
		dateFormated = dateFormated.replace(" ", "T");
		return dateFormated;
		
	}

}
