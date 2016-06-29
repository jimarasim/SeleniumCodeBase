package com.jaemzware.seleniumcodebase;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jameskarasim on 6/25/16.
 */
public class BPTAmazonPaymentTest extends BPTPaymentTest{

    /**
     * TODO: VALID TEST. THIS WAS COPIED
     */
    @Test
    public void BPTAmazonPaymentHappyPathTest(){
        try{

            super.BPTPaymentPageHappyPathTest();

        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
