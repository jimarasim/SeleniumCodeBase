
package com.jaemzware.seleniumcodebase;
        import org.junit.Assert;
        import org.junit.Test;
/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTSelectAnEventTest extends BPTFindAnEventTest{

    /**
     * Verifies an event can be selected
     */
    @Test
    public void BPTSelectAnEventHappyPathTest(){

        //RULE: IF YOU DERIVE FROM A TEST CLASS THAT IS NOT A BASE CLASS, USE ITS HAPPY PATH TEST IF FEASIBLE
        //running the base classes happy path test is redundant, but it doesnt take that long
        // and navigates to the page under test for you
        super.BPTFindAnEventPageHappyPathTest();

        try{
            if(IsElementPresent(testPage.firstEventLink)) {
                System.out.println("PASS: FOUND FIRST EVENT LINK:"+testPage.firstEventLink);
            }
            else{
                verificationErrors.append("MISSING FIRST EVENT LINK:"+testPage.firstEventLink);
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}

