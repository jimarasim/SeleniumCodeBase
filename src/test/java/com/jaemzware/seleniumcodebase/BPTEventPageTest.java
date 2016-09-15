package com.jaemzware.seleniumcodebase;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by jamesa on 7/6/16.
 */
public class BPTEventPageTest extends BaseTest {

    BPTEventPage testEventPage = new BPTEventPage();

    @Test
    public void BPTEventPageElementVerificationTest(){
        try{
            ElementVerificationTest(testEventPage.getBasePageUrl(),testEventPage.getBasePageSanityCheckElements());
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
