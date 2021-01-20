import HelperClasses.AccountPage;
import HelperClasses.CommonHelper;
import HelperClasses.ContactPage;
import HelperClasses.LoginPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TestFile {
    public static void main(String[] args){
        try {
            // Path to Chrome Driver
            System.setProperty("webdriver.chrome.driver", "I:\\Softwares\\Selenium\\chromedriver.exe");
            // Setting Chrome Options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            //Driver Initiailization
            WebDriver driver = new ChromeDriver(options);
            Logger logger = Logger.getLogger("TestFile");
            // Implicit Wait
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            // Maximize window
            driver.manage().window().maximize();

            // Page Login
            LoginPage lp = new LoginPage(driver);
            lp.OpenRecord("https://amdocs-e-dev-ed.my.salesforce.com/", "Login | Salesforce");
            lp.Login("mukheemuddin@mukheem.com", "Mukhee@5", "xpath");

            // Naigate to Sales App
            driver.findElement(By.xpath("//div[@role=\"navigation\"]//button")).click();
            driver.findElement(By.xpath("//input[@type=\"search\" and @placeholder=\"Search apps and items...\"]")).sendKeys("Sales");
            WebElement elementToBeClicked = driver.findElement((By.xpath("//a[@data-label=\"Sales\"]")));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].click();",elementToBeClicked);

            //Open Home Page
            //lp.OpenRecord("https://amdocs-e-dev-ed.lightning.force.com/lightning/page/home","Home | Salesforce");

            // Create Account
            AccountPage ap = new AccountPage(driver);
            boolean result = ap.clickAccountLink("xpath", "Recently Viewed | Accounts | Salesforce");
            assert result == true;
            result = ap.clickNewAccountButton("xpath");
            assert result == true;
            result = ap.fillAccountDetails("xpath","Recently Viewed | Accounts | Salesforce" );
            assert result == true;

            // Create Contact
            ContactPage cp = new ContactPage(driver);
            result = cp.clickContactLink("xpath","Recently Viewed | Contacts | Salesforce");
            assert result == true;
            result = cp.clickNewContactButton("xpath");
            assert  result == true;
            result = cp.fillContactDetails("xpath","Recently Viewed | Contacts | Salesforce");
            assert result == true;
            Thread.sleep(5000);
            driver.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
