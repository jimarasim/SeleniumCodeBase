package com.jaemzware.seleniumcodebase;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jameskarasim on 6/25/16.
 */
//RULE4: BASE TEST FOR A PAGE CLASS IS THE SAME NAME AS THE PAGE CLASS WITH "Test" APPENDED
public class BPTPaymentPageTest extends BaseTest{

    //TODO THE PAYMENT PAGE REQUIRES NAVIGATION THROUGH WEB FORM ELEMENTS; YOU CANT JUST GO DIRECTLY TO THE URL THUS THIS CLASS IS ALL MOCK DATA FOR NOW. E.G. basePageUrl IS NOT THE PAYMENT PAGE
    BPTPaymentPage testPaymentPage = new BPTPaymentPage();

    @Test
    public void BPTPaymentPageElementVerificationTest(){
        try{
            ElementVerificationTest(testPaymentPage.getBasePageUrl(),testPaymentPage.getBasePageSanityCheckElements());
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}

