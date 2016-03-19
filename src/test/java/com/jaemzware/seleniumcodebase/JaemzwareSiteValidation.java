package com.jaemzware.seleniumcodebase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.jaemzware.seleniumcodebase.ParameterType.*;

/**
 * @author jaemzware@hotmail.com
 */
public class JaemzwareSiteValidation extends CodeBase {
    
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

    @Test
    public void BlackMarketSkatesHomePageLogoTest(){
        try{
            StartDriver();
            
            //check if driver setting was successful
            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
            
            this.driverGetWithTime("https://blackmarketskates.com");
            if(!IsElementPresent(By.xpath("//img[@src='https://blackmarketskates.com/wp-content/uploads/2015/03/WEB.BANNER31-e14252697876171.png'"))){
                throw new Exception("HOME PAGE MISSING LOGO");
            }
        }
        catch (Exception ex) {
            ScreenShot();
            Assert.fail(ex.getMessage());
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
