package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class TestPage {
	private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(30);
	private static final Duration WAIT_POLLING_INTERVAL = Duration.ofSeconds(1);
	WebDriver driver;
	private FluentWait<WebDriver> wait;
	
	// URL of index
	public static String URL = "http://automationpractice.com/index.php";	
	// Login Button
	public static By btnLogin = By.cssSelector("a[title='Log in to your customer account']");
	// Create Account email field
	public static By txtEmailCreate = By.id("email_create");
	// Create Account button
	public static By btnEmailCreate = By.id("SubmitCreate");
	// Error text box
	public static By lblCreateAccountError = By.id("create_account_error");
	
	public TestPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new FluentWait<WebDriver>(driver)
				.withTimeout(WAIT_TIMEOUT)
				.pollingEvery(WAIT_POLLING_INTERVAL)
				.ignoring(NoSuchElementException.class);
	}

	public void createNewAccount(String emailAddress) {
		//Load the page
		driver.get(TestPage.URL);		
		// Wait until the login button is 	
		wait.until(ExpectedConditions.elementToBeClickable(TestPage.btnLogin));		
		// Click the login button
		driver.findElement(TestPage.btnLogin).click();		
		
		// Wait until the next page loads
		wait.until(ExpectedConditions.presenceOfElementLocated(TestPage.txtEmailCreate));
		// Enter my email address
		driver.findElement(TestPage.txtEmailCreate).sendKeys(emailAddress);		
		// Click create button
		driver.findElement(TestPage.btnEmailCreate).click();				
	}

	public String getAccountCreateErrorID() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(TestPage.lblCreateAccountError));
		return driver.findElement(TestPage.lblCreateAccountError).getAttribute("id");	
	}	
}