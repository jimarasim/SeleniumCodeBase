package com.jaemzware.seleniumcodebase;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTFindAnEventTest extends BaseTest{

    BPTFindAnEventPage testPage = new BPTFindAnEventPage();

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
            driverGetWithTime(testPage.pageUrl);

            //ALWAYS LOOK FOR SOMETHING BEFORE YOU USE IT
            if(IsElementPresent(testPage.pageTitle)) {
                System.out.println("PASS: PAGE TITLE:"+testPage.pageTitle);
            }
            else{
                verificationErrors.append("MISSING PAGE TITLE:"+testPage.pageTitle);
            }

            if(IsElementPresent(testPage.pageHeader)) {
                System.out.println("PASS: PAGE HEADER:"+testPage.pageHeader);
            }
            else{
                verificationErrors.append("MISSING PAGE HEADER:"+testPage.pageHeader);
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
