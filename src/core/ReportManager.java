package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
* The ReportManager class contains  the extent report object and
* reporter object.
*
* @author  Brian Ancliffe
* @version 1.0
* @since   2022-01-04 
*/
public class ReportManager {
	private static ExtentReports extent;
	private static ExtentSparkReporter spark;
	
	/**
	 * Get the report object. If it had not yet been initialized, 
	 * it will create the object and set the system info.
	 * @param filePath the location in the project to save the report
	 * @return ExtentReports returns the report object
	 */
    public synchronized static ExtentReports getReporter(String filePath) {
        if (extent == null) {
        	extent = new ExtentReports();  
        	spark = new ExtentSparkReporter(filePath);
        	spark.config().setTheme(Theme.DARK);
        	spark.config().setDocumentTitle("Brian Ancliffe - Selenium Demo");
    		
    		extent.attachReporter(spark);
        	extent.setSystemInfo("Selenium Version", "4.1.1");
        	extent.setSystemInfo("TestNG Version", "7.4.0");
        	extent.setSystemInfo("Extent Reports Version", "5.0.9");
        }
        
        return extent;
    }
}
