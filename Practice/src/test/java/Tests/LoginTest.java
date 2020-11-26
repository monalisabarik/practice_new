package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Pages.LoginPage;
import utilities.ExtentReportUtil;
import utilities.Log;
import utilities.PropertyUtil;


public class LoginTest extends BaseTest {
	
	@Test
	public void verifyLoginWithValidCredentials(String uname,String pwd) throws InterruptedException {

		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.login(PropertyUtil.readProperty("userName"),PropertyUtil.readProperty("password"));
		
		ExtentReportUtil.logStep(Status.INFO, "Successfully logged in to the Application");
		String actualMsg = loginPage.getWelComeMsg(getDriver());
		Assert.assertEquals(actualMsg, "Welcome Admin", "Verify Welcome Message");
		ExtentReportUtil.logStep(Status.INFO, "Successfully verified the welcome message");
		Log.info("Verified Welcome message");
		loginPage.logout(getDriver());
		ExtentReportUtil.logStep(Status.INFO, "Successfully logged out");

	} 


}
