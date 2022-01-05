package core;

import java.lang.reflect.Method;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

/**
 * The BaseTest class is the super object for any test files. It contains the
 * driver configuration and wait objects.
 *
 * @author Brian Ancliffe
 * @version 1.0
 * @since 2022-01-04
 */
public class BaseTest {

	protected ExtentReports extent;
	protected ExtentTest test;
	public WebDriver driver;

	// Where should the resulting report file be saved. In this case, in the root
	// directory
	final String reportFilePath = "Extent.html";

	/**
	 * Initializes the report manager, sets the webdriver system
	 * property, creates the driver object, and creates the fluentWait object. This
	 * method runs before the suite executes.
	 */
	@BeforeSuite
	public void init() {
		extent = ReportManager.getReporter(reportFilePath);
		System.setProperty("webdriver.gecko.driver", "d:/Selenium/SeleniumDemo/bin/geckodriver.exe");
		driver = new FirefoxDriver();
	}

	/**
	 * Creates the test extent object
	 * 
	 * @param method The name of the method being executed. This typically
	 *               represents the test case name.
	 */
	@BeforeMethod
	protected void beforeMethod(Method method) {
		test = extent.createTest(method.getName());
	}

	/**
	 * Evaluates the results of the test and records PASS, FAIL, etc and
	 * takes a screenshot
	 * 
	 * @param result The result of the executed test
	 */
	@AfterMethod
	protected void afterMethod(ITestResult result) {
		// log the result
		switch (result.getStatus()) {
		case ITestResult.FAILURE:
			test.log(Status.FAIL, result.getThrowable());
			break;
		case ITestResult.SKIP:
			test.log(Status.SKIP, "Test skipped " + result.getThrowable());
			break;
		case ITestResult.SUCCESS:
			test.log(Status.PASS, "Test passed");
			break;
		default:
			test.log(Status.INFO, "Unknown Test Result!");
		}

		// Push it to the report
		extent.flush();
	}

	/**
	 * Closes the webdriver object after the suite has executed
	 */
	@AfterSuite
	public void cleanup() {
		driver.close();
	}
	
	/**
	 * Take a screenshot and attach it to the report/test
	 * @param title the test to assign the screenshot
	 */
	public void takeScreenshot(String title) {
		// Record a screenshot
		String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(screenshot, title);
	}

	/**
	 * Documents test details
	 * 
	 * @param author   who authored the test
	 * @param category which test category it applies to.
	 */
	protected void addTestDetails(String author, String category) {
		test.assignAuthor(author);
		test.assignCategory(category);
	}

}
