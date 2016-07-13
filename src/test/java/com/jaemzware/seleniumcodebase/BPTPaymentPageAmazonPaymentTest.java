package com.jaemzware.seleniumcodebase;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jameskarasim on 6/25/16.
 */
public class BPTPaymentPageAmazonPaymentTest extends BPTPaymentPageTest {

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
