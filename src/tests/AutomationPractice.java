package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.TestPage;
import core.BaseTest;

public class AutomationPractice extends BaseTest {
	private TestPage page;
	
	@Test
	public void createAccountFails() {
		addTestDetails("Brian Ancliffe", "negative test");
		try {						
			page = new TestPage(driver);
			page.createNewAccount("bancliffe@gmail.com");						
			Assert.assertEquals(page.getAccountCreateErrorID(), "create_account_error");	
			takeScreenshot("Error Message");
		}catch (Exception ex) {
			Assert.fail(ex.getLocalizedMessage());
		}
	}
}
