package com.jaemzware.seleniumcodebase;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jameskarasim on 6/25/16.
 */
public class BPTPaymentPageAmazonPaymentTest extends BPTPaymentPageTest {

    //RULE1: test page objects begin with "test" followed by the page class name without the BPT prefix
    /**
     * TODO: VALID TEST FOR AMAZON PAYMENTS
     */
    @Test
    public void BPTAmazonPaymentHappyPathTest(){
        try{
            super.BPTPaymentPageElementVerificationTest();
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
