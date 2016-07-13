package com.jaemzware.seleniumcodebase;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by jameskarasim on 7/11/16.
 */
public class BPTGlobalPageTest extends BaseTest {

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
