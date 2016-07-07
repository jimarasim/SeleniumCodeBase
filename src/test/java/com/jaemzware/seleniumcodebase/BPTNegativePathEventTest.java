package com.jaemzware.seleniumcodebase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTNegativePathEventTest extends BaseTest{

    //RULE: test page objects begin with "test" followed by the page class name without the BPT prefix
    BPTEventPage testEventPage = new BPTEventPage();

    /**
     * VERIFIES THE PAGE LOADS WITHOUT THE UNIQUE EVENT API ENDPOINT SPECIFIER (A NEGATIVE CASE. THERE ARE NO LINKS IN THE SITE THAT NAVIGATE TO THIS PAGE, BUT ITS A COMMON USAGE AMONG SMARTER INTERNET USERS)
     * REPORTS TIME IT TAKES TO LOAD THE PAGE
     * VERIFIES THE PAGE'S HEADER AND TITLE ARE PRESENT
     * FAILS WHEN EITHER PAGE'S HEADER OR TITLE ARE MISSING
     * FAILURE WILL REPORT IF ONE OR BOTH OF HEADER AND TITLE ARE MISSING
     */
    @Test
    public void BPTEventNegativePathTest(){
        try{

            //navigate to the page unter test's url
            driverGetWithTime(testEventPage.pageBaseUrl);

            //RULE: ALWAYS LOOK FOR SOMETHING BEFORE YOU USE IT
            //verify the negative page title is in the title
            if(IsElementPresent(testEventPage.negativePageTitle)) {
                System.out.println("PASS: CORRECT NEGATIVE CASE PAGE TITLE FOUND:"+testEventPage.negativePageTitle);
            }
            else{
                verificationErrors.append("MISSING EXPECTED NEGATIVE PAGE TITLE:"+testEventPage.negativePageTitle);
            }

            if(IsElementPresent(testEventPage.pageHeader)) {
                System.out.println("PASS: PAGEHEADER FOUND:"+testEventPage.pageHeader);
            }
            else{
                verificationErrors.append("MISSING HEADER:"+testEventPage.pageHeader);
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}

