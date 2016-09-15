package com.jaemzware.seleniumcodebase;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by jameskarasim on 6/26/16.
 */
//RULE: EVERY TEST OBJECT CLASS DERIVES FROM BASETEST
//RULE4: BASE TEST FOR A PAGE CLASS IS THE SAME NAME AS THE PAGE CLASS WITH "Test" APPENDED
public class BPTFindAnEventPageTest extends BaseTest{

    //RULE1: test page objects begin with "test" followed by the page class name without the BPT prefix
    BPTFindAnEventPage testFindAnEventPage = new BPTFindAnEventPage();

    @Test
    public void BPTFindAnEventPageElementVerificationTest(){
        try{
            ElementVerificationTest(testFindAnEventPage.getBasePageUrl(),testFindAnEventPage.getBasePageSanityCheckElements());
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
