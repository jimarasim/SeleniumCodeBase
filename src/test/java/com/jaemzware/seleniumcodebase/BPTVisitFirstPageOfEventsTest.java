package com.jaemzware.seleniumcodebase;
        import org.junit.Assert;
        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;

        import java.util.List;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTVisitFirstPageOfEventsTest extends BPTFindAnEventTest{

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
            if(IsElementPresent(testPage.eventLinks)) {
                System.out.println("PASS: FOUND AT LEAST ONE EVENT LINK WITHIN THE XPATH:"+testPage.eventLinks);
            }
            else{
                verificationErrors.append("MISSING MINIMUM ONE EVENT LINK WITHIN THE XPATH:"+testPage.eventLinks);
            }

            //get all event links on the page
            List<WebElement> links = driver.findElements(testPage.eventLinks);

            //verify each event page
            for (WebElement eventLink:links){
                boolean evenLinkIsDisplayed = eventLink.isDisplayed();
                String eventLinkHref = eventLink.getAttribute("href");

                if(evenLinkIsDisplayed) {
                    System.out.println("EVENT LINK DISPLAYED:" + eventLinkHref);
                }
                else{
                    System.out.println("EVENT LINK DETECTED BUT NOT DISPLAYED:" + eventLinkHref);
                }
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}


