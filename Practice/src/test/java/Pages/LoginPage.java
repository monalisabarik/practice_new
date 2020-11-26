package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.Log;



public class LoginPage {
	@FindBy(id = "txtUsername")
	private WebElement txtboxUserName;

	@FindBy(id = "txtPassword")
	private WebElement txtboxPassword;

	@FindBy(id = "btnLogin")
	private WebElement btnLogin;

	@FindBy(xpath = "//a[@id='welcome']")
	private WebElement linkWelcome;

	@FindBy(xpath = "//a[text()='Logout']")
	private WebElement linkLogout;

	@FindBy(id = "spanMessage")
	private WebElement msgLoginError;

	@FindBy(id = "spanCopyright")
	private WebElement msgFooter;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void login(String userName, String password) {
		try {
			txtboxUserName.sendKeys(userName);
			Log.info("Entered username: " + userName);
			txtboxPassword.sendKeys(password);
			Log.info("Entered Password: " + password);
			btnLogin.click();
			Log.info("Click on Login");
		} catch (Exception e) {
			Log.error("Failed to Login:" + e.getMessage());
		}

	}

	public String getWelComeMsg(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(linkWelcome));
		return linkWelcome.getText();
	}

	public void logout(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", linkWelcome);
		// linkWelcome.click();
		// WebDriverWait wait = new WebDriverWait(driver, 20);
		// linkLogout = wait.until(ExpectedConditions.visibilityOf(linkLogout));
		js.executeScript("arguments[0].click();", linkLogout);
		//linkLogout.click();
		Log.info("Logged out successfully");
	}

	public String getLoginErrorMsg() {
		return msgLoginError.getText();
	}

	public String getFooterMsg() {
		return msgFooter.getText();
	}


}
