package interfaces;

import java.util.Date;
import java.util.Map;

/**
 * @author Joao Vitor de Camargo
 */
public interface ExpirationDate {

	public Map<Integer, Date> getExpirationDates();

	public Date getExpirationDateByYear(int year);

	public boolean isValidDatePerYear(int year, Date comparingDate);

	public void newMap();
	
	public void addExpirationDate(int year, Date date);
}
