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

public class ContactPage extends CommonHelper{

    // Element Locators
    String contactTabLink = "//span[contains(text(),\"Contacts\")]/parent::a[@title='Contacts']";
    String newButton = "//div[@title='New']";
    String contactModal = "//span[contains(text(),\"Contact Information\")]";
    String contactFNameTextBox = "//span[contains(text(),\"First Name\")]/parent::label/following-sibling::input";
    String contactLNameTextBox = "//span[contains(text(),\"Last Name\")]/parent::label/following-sibling::input";
    String salutationDropdown = "//span[contains(text(),\"Salutation\")]/parent::span/following-sibling::div//a[@class='select']";
    String dropDownvalue = "//ul//li/a[text()='__PLACE_HOLDER__']";
    String savebutton = "//button[@title=\"Save\"]";
    String searchAccounts = "//input[@title=\"Search Accounts\"]";
    String selectRelatedAccount = searchAccounts+"/following-sibling::div//a//div[@title='__PLACE_HOLDER__']";
    Logger logger = Logger.getLogger("Contact Page");
    public ContactPage(WebDriver incomingDriver) {
        super(incomingDriver);
    }

    public boolean clickContactLink(String typeOfElement, String title){
        try{
            logger.info("Clicking on Contact Link");
            clickElement(contactTabLink,typeOfElement);
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

    public boolean clickNewContactButton(String typeOfElement){
        try{
            logger.info("Clicking on New Contact Button");
            clickElement(newButton,typeOfElement);
            //Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(contactModal))));
            return true;
        }
        catch (Exception e){
            logger.severe("Error-ed out while Clicking on New Contact button."+e.getMessage());
            return false;
        }
    }

    public boolean fillContactDetails(String typeOfElement, String title){
        try{
            logger.info("Filling Contact details");
            Random random = new Random();
            String contactFName = "Mukheem - "+String.valueOf(random.nextInt(999));
            String contactLName = "A Shaik";
            Global.reqParameters.put("CONTACT_FIRST_NAME",contactFName);
            // Set Contact Name
            setText(contactFName, contactFNameTextBox,typeOfElement);
            setText(contactLName, contactLNameTextBox,typeOfElement);
            // Select Salutation
            clickElement(salutationDropdown,"xpath");
            clickElement(dropDownvalue.replace("__PLACE_HOLDER__","Mr."),typeOfElement);
            // Link Account
            String accName = Global.reqParameters.get("ACCOUNT_NAME");
            setText(accName,searchAccounts, typeOfElement);
            clickElement(selectRelatedAccount.replace("__PLACE_HOLDER__",accName),typeOfElement);
            // Click on Save
            clickElement(savebutton,"xpath");
            //Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.titleIs(contactFName+" "+contactLName+" | Salesforce"));
            return true;
        }
        catch (Exception e){
            logger.severe("Error-ed out while filling account details."+e.getMessage());
            return false;
        }
    }
}
