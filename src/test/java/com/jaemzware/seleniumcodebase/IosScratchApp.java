package com.jaemzware.seleniumcodebase;
import io.appium.java_client.MobileElement;
import java.security.InvalidParameterException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author jaemzware.com
 */
public class IosScratchApp extends CodeBase {
    @BeforeClass
    public void BeforeClass(){
        try {
            // get specified command line parameters
            String getParameterResult = GetParameters();
            // check for errors
            if (!getParameterResult.isEmpty()) {
                System.out.println(getParameterResult);
                throw new InvalidParameterException();
            }else{
                // initialize verifification errors
                verificationErrors = new StringBuilder();
            }
        } catch (Exception ex) {
            Assert.fail("INVALID PARAMETERS FOUND:"+ex.getMessage());
        }        
        
        //start the service
        StartAppiumDriver();
        if(iosDriver==null){
            Assert.fail("COULD NOT START APPIUM DRIVER SERVICE");
        }
    }

    /**
     * This is a proof of concept test for testing real applicaions through appium
     */
    @Test 
    public void EnumerateElements(){
        try{
            //this output elements in the app, but only saw it work once
            List<MobileElement> elements = iosDriver.findElements(By.xpath("//*"));
            elements.stream().forEach((mobileElement) -> {
                System.out.println("TAG:"+mobileElement.getTagName()+" TEXT:"+mobileElement.getText());
            });
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @After
    public void AfterTest(){
        // check if there were any verify errors, and fail whole test if so
        if (verificationErrors.length() > 0) {
            System.out.println("\nLOGO VERIFICATION REPORT\n" + verificationErrors.toString());
        }
    }
    
    @AfterClass
    public void AfterClass() {
        try {
            if (iosDriver != null) {
                QuitIosDriver();
            }
        } catch (Exception ex) {
            CustomStackTrace("AFTER CLASS EXCEPTION", ex);
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }
    
    

}
