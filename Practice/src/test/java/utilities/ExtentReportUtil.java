package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class ExtentReportUtil {
	
	private static ExtentHtmlReporter htmlReporter;
	private static ExtentReports extent;
	private static ExtentTest test;
	
	
	public static ExtentTest getTest() {
		return test;
	}


	public static void intitalizeExtent() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Reports/OrangeHRMInt_Report.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);	
		htmlReporter.config().setDocumentTitle("OrangeHRM Execution Report");
		htmlReporter.config().setReportName("Orange HRM");
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Browser", PropertyUtil.readProperty("browserName"));
	}
	
	
	public static void addTestCaseInExtentReport(String testName) { //add testcase name to report
		test = extent.createTest(testName);  //createTest() is the predefined method of ExtentReports class
	}
	

	

	public static void logStep(Status status,String stepName) {		//add log steps to the report
		//test.log(status, stepName);	
		test.log(status, stepName);     //log() is the predefined method of ExtentTest class
	
		
	}
	
	public static void endReport() {
		extent.flush();
	}


}
