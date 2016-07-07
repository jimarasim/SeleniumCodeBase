
package com.jaemzware.seleniumcodebase;
        import org.junit.Assert;
        import org.junit.Test;
/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTFindAnEventPageSelectAnEventTest extends BPTFindAnEventPageTest {

    //RULE1: test page objects begin with "test" followed by the page class name without the BPT prefix
    BPTEventPage testEventPage = new BPTEventPage();

    /**
     * Verifies an event can be selected
     */
    @Test
    public void BPTSelectAnEventHappyPathTest(){

        //RULE2: IF YOU DERIVE FROM A TEST CLASS THAT IS NOT A BASE CLASS, USE ITS HAPPY PATH TEST IF FEASIBLE (not too time consuming)
        super.BPTFindAnEventPageElementVerificationTest();

        try{
            if(IsElementPresent(testFindAnEventPage.firstEventLink)) {
                System.out.println("PASS: FOUND FIRST EVENT LINK:"+testFindAnEventPage.firstEventLink);
                driver.findElement(testFindAnEventPage.firstEventLink).click();
                Thread.sleep(2000); //TODO REMOVE HARD CODED WAIT
                driver.findElement(testEventPage.addToCartButton).click();
                Thread.sleep(2000); //TODO REMOVE HARD CODED WAITs
            }
            else{
                verificationErrors.append("MISSING FIRST EVENT LINK:"+testFindAnEventPage.firstEventLink);
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}

