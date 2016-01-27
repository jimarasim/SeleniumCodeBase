package com.jaemzware.seleniumcodebase;
import org.junit.*;
import java.security.InvalidParameterException;

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

    
    @Test
    public void FindAMobileElement(){
            System.out.println(iosDriver.findElementsByClassName("UIAStaticText").get(0).getText());
            System.out.println(iosDriver.findElementsByClassName("UIAButton").get(0).getText());
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
