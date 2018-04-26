package controllers;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import interfaces.ExpirationDate;

/**
* @author Joao Vitor de Camargo
*/
public class ExpirationDateController<UsedExpirationDate extends ExpirationDate> {

	private Class<UsedExpirationDate> usedExpirationDateClass;
	private ExpirationDate expirationDateObject;

	public ExpirationDateController(Class<UsedExpirationDate> usedExpirationDateClass) {
		this.usedExpirationDateClass = usedExpirationDateClass;
	}

	public ExpirationDate setExpirationDateForYear(Integer year, Date expirationDate)
			throws InstantiationException, IllegalAccessException {
		if (expirationDateObject == null)
			expirationDateObject = usedExpirationDateClass.newInstance();
		expirationDateObject.newMap();
		expirationDateObject.addExpirationDate(year, expirationDate);
		return expirationDateObject;
	}

	public boolean isValidDatePerYear(int year, Date comparingDate) {
		return expirationDateObject.isValidDatePerYear(year, comparingDate);
	}

	public boolean isValidDatePerYear(int year) {
		return expirationDateObject.isValidDatePerYear(year, removeTime(new Timestamp(System.currentTimeMillis())));
	}

	private Date removeTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

}
