package HelperClasses;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.Timer;
import java.util.logging.Logger;

public class CommonHelper {
    WebDriver driver;
    Logger logger = Logger.getLogger("HelperClasses");
    public CommonHelper(WebDriver incomingDriver) {
        driver = incomingDriver;
    }

    protected boolean setText(String textToBeInserted, String element, String typeOfElement){
        try{
            // String typeOfElement = 'xpath'; --?Default setting to xpath but we need to write if else ladder in real life
            WebElement textbox = driver.findElement(By.xpath(element));
            if (typeOfElement.equals("css")) {
                textbox = driver.findElement(By.cssSelector(element));
            }
            logger.fine("Setting text in "+element);
            textbox.sendKeys(textToBeInserted);
            textToBeInserted.equalsIgnoreCase(textbox.getText());
            logger.fine("Text is now set into "+element);
            return true;
        }
        catch (Exception e){
            logger.severe("Error-ed out while filling in text"+e.getMessage());
            return false;
        }
    }

    protected boolean clickElement(String element, String typeOfElement){
        try{
            // String typeOfElement = 'xpath'; --?Default setting to xpath but we need to write if else ladder in real life
            WebElement elementToBeClicked = driver.findElement(By.xpath(element));

            if (typeOfElement.equals("css")) {
                elementToBeClicked = driver.findElement(By.cssSelector(element));
            }
            //Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(elementToBeClicked));
            logger.fine("Clicking on "+element);
            elementToBeClicked.click();
            logger.fine("Clicked on "+element);
            return true;
        }
        catch(JavascriptException | TimeoutException jse){
            logger.info("Clicking elemnt thru JS");
            WebElement elementToBeClicked = driver.findElement(By.xpath(element));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].click();",elementToBeClicked);
            return true;
        }
        catch (Exception e){
            logger.severe("Error-ed out while filling in text"+e.getMessage());
            return false;
        }
    }

    public boolean OpenRecord(String url,String title){
        try{
            driver.get(url);
            //Fluent Wait
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.titleIs(title));
            return driver.findElement(By.xpath("//title")).getAttribute("innerHTML").equals(title);
        }
        catch (Exception e){
            logger.severe("Error-ed out while Opening record"+e.getMessage());
            return false;
        }
    }

    protected boolean selectOptionFromDropdown(String textToBeSelected, String element, String typeOfElement){
        try{
            // String typeOfElement = 'xpath'; --?Default setting to xpath but we need to write if else ladder in real life
            Select selectItem = new Select(driver.findElement(By.xpath(element)));
            WebElement textbox = driver.findElement(By.xpath(element));
            if (typeOfElement.equals("css")) {
                selectItem = new Select(driver.findElement(By.cssSelector(element)));
            }
            selectItem.selectByVisibleText(textToBeSelected);
            logger.fine("Setting text in "+element);
            textbox.sendKeys(textToBeSelected);
            textToBeSelected.equalsIgnoreCase(textbox.getText());
            logger.fine("Text is now set into "+element);
            return true;
        }
        catch (Exception e){
            logger.severe("Error-ed out while filling in text"+e.getMessage());
            return false;
        }
    }
}
