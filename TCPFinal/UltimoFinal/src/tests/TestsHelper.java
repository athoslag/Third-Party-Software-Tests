package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import implementation.Rubric;
import interfaces.BudgetItem;

/**
 * @author Pedro Perrone
 */
public class TestsHelper {
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	public BudgetItem initializeRubric(int code, String name, String classification) {
		BudgetItem rubric = new Rubric();
		rubric.setCode(code);
		rubric.setName(name);
		rubric.setClassification(classification);
		return rubric;
	}
	
	public Number[] predictionValues() {
		Number predictionList[] = new Number[] {1000f, 2000f, 1500f, 1600f, 2200f, 5000f,
												3000f, 3100f, 4000f, 3800f, 1900f, 1234f};
		return predictionList;
	}

	public Date parseDate(String dateString) {
		try {
			return dateFormatter.parse(dateString);
        } catch (ParseException ex) {
            System.out.println("Erro fatal de data");
            return null;
        }
	}
}
