package com.jaemzware.seleniumcodebase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.security.InvalidParameterException;

/**
 * Created by jameskarasim on 3/15/17.
 */
public class BaseAppiumTest extends  CodeBase{

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

            StartAppiumDriver();

            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
        } catch (InvalidParameterException ipex) {
            Assert.fail("INVALID PARAMETERS FOUND:"+ipex.getMessage());
        } catch (Exception ex) {
            Assert.fail("START DRIVER EXCEPTION:"+ex.getMessage());
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
                System.out.println("\nVERIFICATION ERRORS " + verificationErrors.toString());
            }
        } catch (Exception ex) {
            CustomStackTrace("AFTER EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        }
    }
}
