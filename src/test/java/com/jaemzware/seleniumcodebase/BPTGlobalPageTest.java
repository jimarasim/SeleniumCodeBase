package com.jaemzware.seleniumcodebase;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by jameskarasim on 7/11/16.
 */
//RULE: EVERY TEST OBJECT CLASS DERIVES FROM BASETEST
//RULE4: BASE TEST FOR A PAGE CLASS IS THE SAME NAME AS THE PAGE CLASS WITH "Test" APPENDED
public class BPTGlobalPageTest extends BaseTest {

    //RULE1: test page objects begin with "test" followed by the page class name without the BPT prefix
    //RULE: TEST CLASS DERIVED FROM BASETEST DECLARES ITS OWN PAGE OBJECT REFERENCE TO THE PAGE UNDER TEST
    //RULE: AT LEAST ONE BASE TEST CLASS DERIVED FROM EVERY PAGE OBJECT
    BPTGlobalPage testGlobalPage = new BPTGlobalPage();

    @Test
    public void BPTGlobalPageElementVerificationTest(){
        try{
            ElementVerificationTest(testGlobalPage.getBasePageUrl(),testGlobalPage.getBasePageSanityCheckElements());
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
