package tests;

import org.hamcrest.core.IsInstanceOf;
import org.junit.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import implementation.Rubric;
import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;
import tests.TestsHelper;

/**
 * @author Pedro Perrone
 */
public class RubricTest {
	BudgetItem rubric = new Rubric();
	BudgetItem childRubric = new Rubric();
	BudgetItem secondChild = new Rubric();
	BudgetItem grandchildRubric = new Rubric();
	TestsHelper helper = new TestsHelper();
	
	@Before
	public void setUp() {
		rubric = helper.initializeRubric(1, "Test Rubric", "1");
		childRubric = helper.initializeRubric(12, "Child Rubric", "1.1");
		secondChild = helper.initializeRubric(13, "Second Child", "1.2");
		grandchildRubric = helper.initializeRubric(123, "Grandchild Rubric", "1.1.1");
	}
	
	@Test
	public void testRubric() {
		assertNotNull("Initializer created child budget items correctly", this.rubric.getChildBudgetItems());
		assertNotNull("Initializer created annual projections correctly", this.rubric.getAnnualProjections());
	}

	@Test
	public void testGetName() {
		assertEquals("Test Rubric", this.rubric.getName());	
	}
	
	@Test
	public void testGetCode() {
		assertEquals(1, this.rubric.getCode());
	}
	
	@Test
	public void testIsCost() {
		this.rubric.setCost(true);		
		assertEquals(true, this.rubric.isCost());
		
		this.rubric.setCost(false);
		assertEquals(false, this.rubric.isCost());
	}
	
	@Test
	public void testSetAnnualProjections() {
		HashMap<Integer, BudgetItemAnnualValues> annualProj = new HashMap<Integer, BudgetItemAnnualValues>();
		this.rubric.setAnnualProjections(annualProj);
		
		assertEquals(annualProj, this.rubric.getAnnualProjections());
	}
	
	@Test
	public void testSetChildBudget() {
		List<BudgetItem> childBudget = new ArrayList<>();
		this.rubric.setChildBudgetItems(childBudget);
		
		assertEquals(childBudget, this.rubric.getChildBudgetItems());
	}
	
	@Test
	public void testGetFormula() {
		this.rubric.setFormula("teste");
		assertEquals("teste", this.rubric.getFormula());
	}
	
	@Test
	public void testAddChildBudgetItem() {
		List<BudgetItem> childrenList = new ArrayList<>();
		childrenList.add(childRubric);
		
		rubric.addChildBudgetItem(childRubric);
		assertEquals("Adds first child", rubric.getChildBudgetItems(), childrenList);
		
		childrenList.add(secondChild);
		rubric.addChildBudgetItem(secondChild);
		assertEquals("Adds a child when children list is not empty", rubric.getChildBudgetItems(), childrenList);
	}

	@Test
	public void testIsInBudgetItemSubtree() {
		//problem with test
		assertEquals(false, this.rubric.isInBudgetItemSubtree(childRubric));
		
		//check that
		createsKinships();
		assertTrue("Is child", childRubric.isInBudgetItemSubtree(rubric));
		assertTrue("Is grandchild", grandchildRubric.isInBudgetItemSubtree(rubric));
		assertFalse("Is not child", childRubric.isInBudgetItemSubtree(secondChild));
	}

	@Test
	public void testGetLevel() {
		assertEquals("Is in first level", 1, rubric.getLevel());
		assertEquals("Is in a higher level", 3, grandchildRubric.getLevel());
	}

	private void createsKinships() {
		rubric.addChildBudgetItem(childRubric);
		rubric.addChildBudgetItem(secondChild);
		childRubric.addChildBudgetItem(grandchildRubric);
	}
}
