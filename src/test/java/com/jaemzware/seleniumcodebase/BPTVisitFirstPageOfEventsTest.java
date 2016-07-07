package com.jaemzware.seleniumcodebase;
        import org.junit.Assert;
        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.List;

        import static com.jaemzware.seleniumcodebase.ParameterType.defaultImplicitWaitSeconds;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTVisitFirstPageOfEventsTest extends BPTFindAnEventTest{

    //RULE: test page objects begin with "test" followed by the page class name without the BPT prefix
    BPTEventPage testEventPage = new BPTEventPage();

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
            if(IsElementPresent(testFindAnEventPage.eventLinks)) {
                System.out.println("PASS: FOUND AT LEAST ONE EVENT LINK WITHIN THE XPATH:"+testFindAnEventPage.eventLinks);
            }
            else{
                verificationErrors.append("MISSING MINIMUM ONE EVENT LINK WITHIN THE XPATH:"+testFindAnEventPage.eventLinks);
            }

            //get all event links on the page
            List<WebElement> links = driver.findElements(testFindAnEventPage.eventLinks);

            //verify each event page
            for (WebElement eventLink:links){
                boolean evenLinkIsDisplayed = eventLink.isDisplayed();
                boolean evenLinkIsEnabled = eventLink.isEnabled();

                String eventLinkHref = eventLink.getAttribute("href");

                if(evenLinkIsDisplayed && evenLinkIsEnabled) {
                    //TODO: CLICK THE LINK. SELENIUM IS HAVING A HARD TIME WITH THIS ONE, AND NOTICED THAT THE BROWSER WINDOW IS NOT MAXIMIZING. I
                    System.out.println("EVENT LINK DISPLAYED AND ENABLED:" + eventLinkHref);

                    //CLICK THE LINK
                    eventLink.click();

                    (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                            .until(ExpectedConditions.stalenessOf(eventLink));

                    System.out.println("EVENT LINK STALE CONDITION MET");

                    //go back
                    driver.navigate().back();

                    //look for event page indicator
                    (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                            .until(ExpectedConditions.presenceOfElementLocated(testEventPage.pageLoadedIndicator));

                    //not sure, might have to get the web elements again here since the old reference is stale
                    System.out.println("MIGHT CHOKE HERE BECAUSE WEB ELEMENTS ARE STALE, BUT DID IT GO BACK?");
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


