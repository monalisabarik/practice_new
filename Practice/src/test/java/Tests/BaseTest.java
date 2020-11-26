package Tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import utilities.ExtentReportUtil;
import utilities.Log;
import utilities.PropertyUtil;
import utilities.ScreenShotUtil;

public class BaseTest {
	
	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeSuite
	public void intialize() throws IOException {
		DOMConfigurator.configure("Log4j.xml");
		PropertyUtil.intializePropertyFile();//configdata.property file 
		// ExcelUtil.initilizeExcel();
		ExtentReportUtil.intitalizeExtent();
	}

	@BeforeMethod
	public void openBrowser(Method method) {
		ExtentReportUtil.addTestCaseInExtentReport(method.getName());
		// String runMode = PropertyUtil.readProperty("runMode");

		String browserName = PropertyUtil.readProperty("browserName");
		// if (runMode.equalsIgnoreCase("Local")) {

		if (browserName.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + PropertyUtil.readProperty("chromeDriverPath"));
			ChromeOptions capability = new ChromeOptions();
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capability.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);

			driver = new ChromeDriver(capability);
			driver.manage().window().maximize();

		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + PropertyUtil.readProperty("firefoxDriverPath"));
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + PropertyUtil.readProperty("ieDriverPath"));
			driver = new InternetExplorerDriver();

		} else {
			Log.error(
					"Looks like you provided Invalid Browser Name.PLease check config file for the property -->browserName");
		}
		// }
		/*
		 * else if (runMode.equalsIgnoreCase("Grid")) { String hubUrl =
		 * PropertyUtil.readProperty("HubUrl"); if
		 * (browserName.equalsIgnoreCase("chrome")) { DesiredCapabilities capability =
		 * DesiredCapabilities.chrome(); capability.setBrowserName("chrome");
		 * capability.setPlatform(Platform.WINDOWS); driver = new RemoteWebDriver(new
		 * URL(hubUrl), capability); } }
		 */

		Log.startTestCase(method.getName());
		Log.info("Successfully launched the Browser");
		ExtentReportUtil.logStep(Status.INFO, "Successfully launched the Browser");
		String timeOut = PropertyUtil.readProperty("timeOut");
		long time = Long.parseLong(timeOut);
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

	}

	@BeforeMethod(dependsOnMethods = "openBrowser")
	public void openApplication() {

		driver.get(PropertyUtil.readProperty("url"));
		Log.info("Successfully launched the application");
		ExtentReportUtil.logStep(Status.INFO, "Successfully launch the application");

	}

	@AfterMethod
	public void closeBrowser(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			String returned_screenshot_path = ScreenShotUtil.getScreenshot(driver);
			try {
				ExtentReportUtil.getTest().fail(result.getThrowable().getMessage(),
						MediaEntityBuilder.createScreenCaptureFromPath(returned_screenshot_path).build());
			} catch (IOException e) {
				Log.error("Failed to capture the Screenshot" + e.getMessage());
			}
		}
		driver.quit();
		Log.info("Successfully close the browser");
		ExtentReportUtil.logStep(Status.INFO, "Successfully close the browser");
		Log.endTestCase();
	}

	@AfterSuite
	public void tearDown() {
		ExtentReportUtil.endReport();
	}


}
