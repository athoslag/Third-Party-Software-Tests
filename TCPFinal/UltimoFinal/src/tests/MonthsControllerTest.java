package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import util.MonthsController; 


public class MonthsControllerTest {
	MonthsController monthsControllerTest = new MonthsController();
	
	@Test
	public void testReadBudgetItemHistoryFile() { 
		
		int index = 0;
		
		for (index = 0; index < 13; index++) {	
			assertEquals(monthsControllerTest.getMonthIndex(monthsControllerTest.getMonthName(index)), index);
		}
	}

}

