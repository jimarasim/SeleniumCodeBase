package com.jaemzware.seleniumcodebase;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by jamesa on 7/6/16.
 */
//RULE: EVERY TEST OBJECT CLASS DERIVES FROM BASETEST
//RULE4: BASE TEST FOR A PAGE CLASS IS THE SAME NAME AS THE PAGE CLASS WITH "Test" APPENDED
public class BPTEventPageTest extends BaseTest {

    //RULE1: test page objects begin with "test" followed by the page class name without the BPT prefix
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
