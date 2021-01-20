package HelperClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.Random;
import java.util.logging.Logger;

public class AccountPage extends CommonHelper{

    // Element Locators
    String accountTabLink = "//span[contains(text(),\"Accounts\")]/parent::a[@title='Accounts']";
    String newButton = "//div[@title='New']";
    String accountModal = "//span[contains(text(),\"Account Information\")]";
    String accountNameTextBox = "//span[contains(text(),\"Account Name\")]/parent::label/following-sibling::input";
    String ratingDropdown  = "//span[contains(text(),\"Rating\")]/parent::span/following-sibling::div//a[@class='select']";
    String dropDownvalue = "//ul//li/a[text()='__PLACE_HOLDER__']";
    String savebutton = "//button[@title=\"Save\"]";
    String TypeDropdown  = "//span[contains(text(),\"Type\")]/parent::span/following-sibling::div//a[@class='select']";

    Logger logger = Logger.getLogger("AccountPage");
    public AccountPage(WebDriver incomingDriver) {
        super(incomingDriver);
    }

    public boolean clickAccountLink(String typeOfElement, String title){
        try{
            logger.info("Clicking on Account Link");
            clickElement(accountTabLink,typeOfElement);
            //Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.titleIs(title));
        }
        catch (Exception e){
            logger.severe("Error-ed out while Clicking on Account Tab Link."+e.getMessage());
            return false;
        }
    }

    public boolean clickNewAccountButton(String typeOfElement){
        try{
            logger.info("Clicking on Account Link");
            clickElement(newButton,typeOfElement);
            //Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(accountModal))));
            return true;
        }
        catch (Exception e){
            logger.severe("Error-ed out while Clicking on New Account button."+e.getMessage());
            return false;
        }
    }

    public boolean fillAccountDetails(String typeOfElement, String title){
        try{
            logger.info("Filling account details");
            Random random = new Random();
            String accName = "Account - "+String.valueOf(random.nextInt());
            Global.reqParameters.put("ACCOUNT_NAME",accName);
            // Set Account Name
            setText(accName,accountNameTextBox,typeOfElement);
            // Select Rating
            clickElement(ratingDropdown,"xpath");
            clickElement(dropDownvalue.replace("__PLACE_HOLDER__","Warm"),typeOfElement);
            //Select Type
            clickElement(TypeDropdown,"xpath");
            clickElement(dropDownvalue.replace("__PLACE_HOLDER__","Prospect"),typeOfElement);
            // Click on Save
            clickElement(savebutton,"xpath");
            //Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.titleIs(accName+" | Salesforce"));
            return true;
        }
        catch (Exception e){
            logger.severe("Error-ed out while filling account details."+e.getMessage());
            return false;
        }
    }
}
