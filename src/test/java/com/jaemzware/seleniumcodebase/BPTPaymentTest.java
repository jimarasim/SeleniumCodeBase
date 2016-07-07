package com.jaemzware.seleniumcodebase;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jameskarasim on 6/25/16.
 */
public class BPTPaymentTest extends BaseTest{

    //RULE: test page objects begin with "test" followed by the page class name without the BPT prefix
    BPTPaymentPage testPaymentPage = new BPTPaymentPage();

    /**
     * TODO: VALID TEST. THIS WAS COPIED
     */
    @Test
    public void BPTPaymentPageHappyPathTest(){
        try{
            System.out.println("TODO: VALID TEST. THIS WAS COPIED");
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}

