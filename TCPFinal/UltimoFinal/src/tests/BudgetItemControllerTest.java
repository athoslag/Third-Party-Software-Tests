package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import controllers.BudgetItemController;
import implementation.Rubric;
import implementation.RubricPerYear;
import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;

/**
 * @author Pedro Perrone
 */
public class BudgetItemControllerTest {
	BudgetItemController<Rubric, RubricPerYear> budgetController = new BudgetItemController<Rubric, RubricPerYear>(Rubric.class, RubricPerYear.class);
	BudgetItem rubric = new Rubric();
	BudgetItem childRubric = new Rubric();
	BudgetItem secondChild = new Rubric();
	BudgetItem grandchildRubric = new Rubric();
	BudgetItem rubric2 = new Rubric();
	BudgetItem childRubric2 = new Rubric();
	BudgetItem secondChild2 = new Rubric();
	BudgetItem grandchildRubric2 = new Rubric();
	BudgetItemAnnualValues rubricPerYear = new RubricPerYear();
	BudgetItemAnnualValues rubricPerYear2 = new RubricPerYear();
	List<BudgetItem> rubricList = new ArrayList<BudgetItem>();
	Map<Integer, BudgetItemAnnualValues> projections = new HashMap<Integer, BudgetItemAnnualValues>();
	Map<Integer, BudgetItemAnnualValues> projections2 = new HashMap<Integer, BudgetItemAnnualValues>();
	TestsHelper helper = new TestsHelper();
	
	@Before
	public void setUp() {
		rubric = helper.initializeRubric(1, "Test Rubric", "1");
		childRubric = helper.initializeRubric(12, "Child Rubric", "1.1");
		secondChild = helper.initializeRubric(13, "Second Child", "1.2");
		grandchildRubric = helper.initializeRubric(123, "Grandchild Rubric", "1.1.1");
		rubric2 = helper.initializeRubric(2, "Test Rubric", "2");
		childRubric2 = helper.initializeRubric(22, "Child Rubric", "2.1");
		secondChild2 = helper.initializeRubric(23, "Second Child 2", "2.2");
		grandchildRubric2 = helper.initializeRubric(223, "Grandchild Rubric 2", "2.1.1");
		rubricPerYear.setPredictedValues(helper.predictionValues());
		rubricPerYear2.setPredictedValues(helper.predictionValues());
		createsKinships();
		rubricList.add(0, rubric);
		rubricList.add(1, rubric2);
		projections.put(2017, rubricPerYear);
		projections2.put(2017, rubricPerYear2);
		childRubric.setAnnualProjections(projections);
		grandchildRubric.setAnnualProjections(projections2);
	}

	@Test
	public void testFindFatherBudgetItem() {
		assertNull("Rubric is first element top level entity",
				budgetController.findFatherBudgetItem(rubricList, rubric));
		assertNull("Rubric is in some element top level entity",
				budgetController.findFatherBudgetItem(rubricList, rubric2));
		assertEquals("Rubric is in first subtree", childRubric,
				budgetController.findFatherBudgetItem(rubricList, grandchildRubric));
		assertEquals("Rubric is in the list, but not in first subtree", rubric2,
				budgetController.findFatherBudgetItem(rubricList, secondChild2));
		assertNull("Rubric is null",
				budgetController.findFatherBudgetItem(rubricList, null));
		BudgetItem foreignRubric = new Rubric();
		foreignRubric = helper.initializeRubric(555, "Some Rubric", "5.5.5.");
		assertNull("Rubric is not in the list",
				budgetController.findFatherBudgetItem(rubricList, foreignRubric));
		List<BudgetItem> emptyList = new ArrayList<BudgetItem>();
		assertNull("List is empty",
				budgetController.findFatherBudgetItem(emptyList, rubric));
		
	}

	@Test
	public void testManageReadBudgetItem() {
		try {
			// Insert first rubric
			budgetController.manageReadBudgetItem(mockFileLine("5", "5", "Test"), 2017, true);
			BudgetItem rubric = budgetController.getBudgetItemList().get(0);
			assertRubricValues("5", 5, "Test", rubric);
			
			//Insert a first rubric child
			budgetController.manageReadBudgetItem(mockFileLine("5.1", "6", "Child Test"), 2017, true);
			BudgetItem childRubric = rubric.getChildBudgetItems().get(0);
			assertRubricValues("5.1", 6, "Child Test", childRubric);
			
			//Insert a first level rubric
			budgetController.manageReadBudgetItem(mockFileLine("6", "7", "Brother Test"), 2017, true);
			BudgetItem brotherRubric = budgetController.getBudgetItemList().get(1);
			assertRubricValues("6", 7, "Brother Test", brotherRubric);
			
			//Insert a rubric without classification
			budgetController.manageReadBudgetItem(mockFileLine("", "8", "No Classification"), 2017, false);
			BudgetItem noClassificationRubric = budgetController.getBudgetItemList().get(2);
			assertRubricValues("", 8, "No Classification", noClassificationRubric);
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		}
	}

	@Test
	public void testFindByCode() {
		assertNull("Code does not exists", budgetController.findByCode(rubricList, 3));
		assertEquals("Code is in first element subtree", secondChild,
				budgetController.findByCode(rubricList, secondChild.getCode()));
		assertEquals("Code is in second element subtree", grandchildRubric2,
				budgetController.findByCode(rubricList, grandchildRubric2.getCode()));
		assertEquals("Rubric is a first level rubric", rubric2,
				budgetController.findByCode(rubricList, rubric2.getCode()));
	}

	@Test
	public void testGetBudgetItemAnnualValuesBudgetItem() {
		try {
			BudgetItemAnnualValues result = budgetController.getBudgetItemAnnualValues(grandchildRubric, 2017);
			assertEquals("Get correct value", projections2.get(2017), result);
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		}
	}

	@Test
	public void testGetSumOfChildrenValues() {
		budgetController.getSumOfChildrenValues(childRubric, 2017, 3);
		assertTrue("Calculates correctly", budgetController.getCalculatedValue() == 1600f);
	}

	private void createsKinships() {
		rubric.addChildBudgetItem(childRubric);
		rubric.addChildBudgetItem(secondChild);
		childRubric.addChildBudgetItem(grandchildRubric);
		rubric2.addChildBudgetItem(childRubric2);
		rubric2.addChildBudgetItem(secondChild2);
		childRubric2.addChildBudgetItem(grandchildRubric2);
	}
	
	private String[] mockFileLine(String classification, String code, String name) {
		String predictions = new String(";1;2;3;4;5;6;7;8;9;10;11;12;;;N;;;");
		return classification.concat(";" + code).concat(";" + name).concat(predictions).split(";");
	}
	
	private void assertRubricValues(String classification, int code, String name, BudgetItem rubric) {
		assertEquals(classification, rubric.getClassification());
		assertEquals(name, rubric.getName());
		assertEquals(code, rubric.getCode());
	}
}
