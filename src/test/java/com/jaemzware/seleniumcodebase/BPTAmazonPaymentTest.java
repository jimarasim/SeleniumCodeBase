package com.jaemzware.seleniumcodebase;

import com.sun.xml.internal.rngom.parse.host.Base;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jameskarasim on 6/25/16.
 */
public class BPTAmazonPaymentTest extends BaseTest{
    @Test
    public void BPTAmazonPaymentHappyPathTest(){
        try{
            BPTAmazonPaymentPage testPage = new BPTAmazonPaymentPage();

            driverGetWithTime(testPage.pageUrl);
            if(IsElementPresent(testPage.pageTitle)) {
                System.out.println("PASS: PAGETITLE");
            }
            else{
                verificationErrors.append("MISSING TITLE");
            }

            if(IsElementPresent(testPage.pageHeader)) {
                System.out.println("PASS: PAGEHEADER");
            }
            else{
                verificationErrors.append("MISSING HEADER");
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
