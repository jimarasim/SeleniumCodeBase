package com.jaemzware.seleniumcodebase;
import io.appium.java_client.MobileElement;
import org.junit.*;
import org.openqa.selenium.By;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * @author jaemzware.com
 */
public class IosScratchApp extends CodeBase {
    @BeforeClass
    public static void BeforeClass(){
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
            List<MobileElement> elements = iosDriver.findElementsByIosUIAutomation("target.frontMostApp().mainWindow().textFields()[0]");
            elements.stream().forEach((mobileElement) -> {
                System.out.println("TAG:"+mobileElement.getTagName()+" TEXT:"+mobileElement.getText());
            });
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @Test
    public void FindAMobileElement(){
            MobileElement fieldOne = (MobileElement) iosDriver.findElementByAccessibilityId("firstnameIdentifier");
            System.out.println("TAG NAME:"+fieldOne.getTagName());
    }
    
    @After
    public void AfterTest(){
        // check if there were any verify errors, and fail whole test if so
        if (verificationErrors.length() > 0) {
            System.out.println("\nLOGO VERIFICATION REPORT\n" + verificationErrors.toString());
        }
    }
    
    @AfterClass
    public static void AfterClass() {
        try {
            if (iosDriver != null) {
                QuitIosDriver();
            }
        } catch (Exception ex) {
            CustomStackTrace("AFTER CLASS EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        }
    }
}
