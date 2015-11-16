package com.jaemzware.seleniumcodebase;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author jaemzware.com
 */
public class IosScratchApp extends CodeBase {

    private final String linksOnSplashPageXpath = 
            "//a[@href and not(@href='') and not(contains(@href,'javascript:')) and not(contains(@href,'mailto:'))]";


    @Before
    public void BeforeTest() {
        try {
            
            // get the command line parameters that were specified
            String getParameterResult = GetParameters();
            // an error string will be returned if something went wrong
            if (!getParameterResult.isEmpty()) {
                System.out.println(getParameterResult);
                throw new InvalidParameterException();
            }else{
                // initialize verifification errors
                verificationErrors = new StringBuilder();
            }
        } catch (InvalidParameterException ipex) {
            Assert.fail("INVALID PARAMETERS FOUND:"+ipex.getMessage());
        }
    }

    /**
     * This is a proof of concept test for testing real applicaions through appium
     */
    @Test 
    public void ClickButton(){
        try{
            
            StartAppiumDriver();
            
            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; A SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
            
            //this output elements in the app, but only saw it work once
            List<WebElement> elements = driver.findElements(By.xpath("//*"));
            elements.stream().forEach((web) -> {
                System.out.println("TAG:"+web.getTagName()+" TEXT:"+web.getText());
            });
            
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    @After
    public void AfterTest() {
        try {
            if (driver != null) {
                System.out.println("QUIT DRIVER");
                QuitDriver();
            }

            // check if there were any verify errors, and fail whole test if so
            if (verificationErrors.length() > 0) {
                System.out.println("\nLOGO VERIFICATION REPORT\n" + verificationErrors.toString());
            }
        } catch (Exception ex) {
            CustomStackTrace("AFTER EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        }
    }
    
    

}
