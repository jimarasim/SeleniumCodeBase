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
    public void JoeyPaintbrush(){
        try{
            By[] elementsToVerify={By.xpath("//img")};
            BasicTest("https://joeypaintbrush.com",elementsToVerify);
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }

    @Test
    public void DynamicUtilityServices(){
        try{
            By[] elementsToVerify={By.xpath("//img")};
            BasicTest("http://dynamicutilityservices.com",elementsToVerify);
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }

    @Test
    public void AnalogArchive(){
        try{
            By[] elementsToVerify={By.xpath("//img")};
            BasicTest("http://analogarchive.com",elementsToVerify);
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }

    @Test
    public void Jaemzware(){
        try{
            By[] elementsToVerify={By.xpath("//img")};
            BasicTest("http://jaemzware.com",elementsToVerify);
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }

    @Test
    public void SeattleRules(){
        try{
            By[] elementsToVerify={By.xpath("//img")};
            BasicTest("https://seattlerules.com",elementsToVerify);
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }

    @Test
    public void Skatecreteordie(){
        try{
            By[] elementsToVerify={By.xpath("//img")};
            BasicTest("http://skatecreteordie.com",elementsToVerify);
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }

    @Test
    public void BlackMarketSkatesHomePageImages(){
        //IMAGES EXPECTED TO BE ON BLACK MARKET SKATES HOMEPAGE
        String expectedImages[] = {
                "https://blackmarketskates.com/wp-content/uploads/2015/03/WEB.BANNER31-e14252697876171.png",
                "https://blackmarketskates.com/wp-content/uploads/2014/11/bmbeaniegrey-1024x1024-90x90.jpg",
                "https://blackmarketskates.com/wp-content/uploads/2014/11/bmbeanieblack-e1417193295282-1024x1024-90x90.jpg",
                "https://blackmarketskates.com/wp-content/uploads/2014/11/BMbeanieblue-e1417194216596-1024x1024-90x90.jpg",
                "https://blackmarketskates.com/wp-content/uploads/2014/11/blackmarketpatch-e1417191704674-768x10241-90x90.jpg",
                "https://blackmarketskates.com/wp-content/uploads/2014/11/HOODY-90x90.jpg",
                "https://blackmarketskates.com/wp-content/uploads/2014/09/SHOPDECK1-90x90.jpg",
                "https://blackmarketskates.com/wp-content/uploads/2014/09/SHOPDECK.2-90x90.jpg",
                "https://blackmarketskates.com/wp-content/uploads/2014/09/SHOPDECK3-90x90.jpg",
                "https://blackmarketskates.com/wp-content/uploads/2014/09/SHOPDECK4-90x90.jpg"
        };

        try{
            By[] elementsToVerify={By.xpath("//img")};
            BasicPageImagesTest("https://blackmarketskates.com",expectedImages);
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }

    @Test 
    public void BlackMarketSkatesCustomVideoPlayer(){
        //START DRIVER AND MAKE SURE ITS RUNNING
        try{
            StartDriver();
            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }

            //NAVIGATE TO BLACK MARKET SKATES HOME PAGE WITH VIDEO VERIFY ALL IMAGES ARE THERE
            this.driverGetWithTime("https://blackmarketskates.com/?p=760");

            if(!IsElementPresent(By.id("jaemzwarevideoadam20150207VideoStripDiv"))){
                throw new Exception("VIDEO PAGE MISSING VIDEO PLAYER");
            }
            else{
                System.out.println("PASS: FOUND VIDEO PLAYER");
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
