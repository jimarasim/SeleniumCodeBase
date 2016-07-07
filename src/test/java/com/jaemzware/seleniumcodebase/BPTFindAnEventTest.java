package com.jaemzware.seleniumcodebase;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTFindAnEventTest extends BaseTest{

    //RULE: test page objects begin with "test" followed by the page class name without the BPT prefix
    BPTFindAnEventPage testFindAnEventPage = new BPTFindAnEventPage();

    /**
     * VERIFIES THE PAGE LOADS
     * REPORTS TIME IT TAKES TO LOAD THE PAGE
     * VERIFIES THE PAGE'S HEADER AND TITLE ARE PRESENT
     * FAILS WHEN EITHER PAGE'S HEADER OR TITLE ARE MISSING
     * FAILURE WILL REPORT IF ONE OR BOTH OF HEADER AND TITLE ARE MISSING
     */
    @Test
    public void BPTFindAnEventPageHappyPathTest(){
        try{

            //navigate to the page unter test's url
            driverGetWithTime(testFindAnEventPage.pageUrl);

            //ALWAYS LOOK FOR SOMETHING BEFORE YOU USE IT
            if(IsElementPresent(testFindAnEventPage.pageTitle)) {
                System.out.println("PASS: PAGE TITLE:"+testFindAnEventPage.pageTitle);
            }
            else{
                verificationErrors.append("MISSING PAGE TITLE:"+testFindAnEventPage.pageTitle);
            }

            if(IsElementPresent(testFindAnEventPage.pageHeader)) {
                System.out.println("PASS: PAGE HEADER:"+testFindAnEventPage.pageHeader);
            }
            else{
                verificationErrors.append("MISSING PAGE HEADER:"+testFindAnEventPage.pageHeader);
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
