package implementation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import interfaces.ExpirationDate;

/**
 * @author Joao Vitor de Camargo
 * Class that manages the expiration date per year After the defined expiration
 * date, prediction can no longer be updated that year
 */
public class PredictionExpirationDate implements ExpirationDate {

	private Map<Integer, Date> expirationDates;

	public Map<Integer, Date> getExpirationDates() {
		return expirationDates;
	}

	public Date getExpirationDateByYear(int year) {
//		assert this.expirationDates != null : "No expiration date";
		return this.expirationDates.get(year);
	}

	/***
	 * @param year: the year itself
	 * @param comparingDate: the date that will be compared to the expirationDate
	 * @return true if the comparingDate is not after the expirationDate
	 */
	public boolean isValidDatePerYear(int year, Date comparingDate) {
//		assert comparingDate != null : "Received date is null";
		Date expirationDate = getExpirationDateByYear(year);
		return !expirationDate.before(comparingDate);
	}

	@Override
	public void newMap() {
		this.expirationDates = new HashMap<>();
	}

	@Override
	public void addExpirationDate(int year, Date date) {
		this.expirationDates.put(year, date);
		
	}

}
