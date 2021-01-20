package HelperClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public class LoginPage extends CommonHelper{

    // Element Locators
    String userName = "//input[@id='username']";
    String password = "//input[@id='password']";
    String loginButton = "//input[@id='Login']";
    Logger logger = Logger.getLogger("LoginPage");
    public LoginPage(WebDriver incomingDriver) {
        super(incomingDriver);
    }

    public boolean Login(String inputUserName, String inputPassword, String typeOfElement){
        try{
            logger.info("Logging in with user name and password");
            setText(inputUserName, userName, typeOfElement);
            logger.finer("Username entered");
            setText(inputPassword,password,typeOfElement);
            logger.finer("Password entered");
            clickElement(loginButton,typeOfElement);
            return true;
        }
        catch (Exception e){
            logger.severe("Error-ed out while filling in text"+e.getMessage());
            return false;
        }
    }
}
