package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import controllers.BudgetItemController;
import implementation.Rubric;
import implementation.RubricPerYear;
import interfaces.BudgetItem;
import util.FileInterpreter; 

/**
 * @author Pedro Perrone
 */
public class FileInterpreterTest {
	FileInterpreter<Rubric, RubricPerYear> fileInterpreter = new FileInterpreter<>(Rubric.class, RubricPerYear.class);

	@Test
	public void testReadBudgetItemHistoryFile() {
		try {
			// In the file are all bad situations, such as blank line os rubrics without code.
			BudgetItemController<Rubric, RubricPerYear> budgetController = fileInterpreter.readBudgetItemHistoryFile("tests", 2017);
			ArrayList<BudgetItem> budgetItemList = budgetController.getBudgetItemList(); 
			assertNull("Code from invalid line does not exists",
					budgetController.findByCode(budgetItemList, 10000));
			assertNotNull("Code from a valid line exists",
					budgetController.findByCode(budgetItemList, 103));
			assertCodesAreNotNull(budgetItemList);
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IOException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (ParseException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		}
	}

	@Test
	public void testReadBudgetItemHistoryFileNotFoundFile() {
		try {
			fileInterpreter.readBudgetItemHistoryFile("does_not_exists", 2017);
			fail("Should throw FileNotFoundExeption");
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (ParseException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		}
	}
	
	private void assertCodesAreNotNull(List<BudgetItem> budgetItemList) {
		for(BudgetItem rubric : budgetItemList) {
			assertNotNull("There is none null code", rubric.getCode());
			assertCodesAreNotNull(rubric.getChildBudgetItems());
		}
	}
}
