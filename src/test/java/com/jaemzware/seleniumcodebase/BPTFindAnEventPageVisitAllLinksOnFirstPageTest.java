package com.jaemzware.seleniumcodebase;
        import org.junit.Assert;
        import org.junit.Test;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.List;

        import static com.jaemzware.seleniumcodebase.ParameterType.defaultImplicitWaitSeconds;

/**
 * Created by jameskarasim on 6/26/16.
 */
//RULE: TEST CLASS NAME ENDS WITH TEST
public class BPTFindAnEventPageVisitAllLinksOnFirstPageTest extends BPTFindAnEventPageTest {

    //RULE1: test page objects begin with "test" followed by the page class name without the BPT prefix
    BPTFindAnEventPage testFindAnEventPage = new BPTFindAnEventPage();

    //RULE3: TEST NAME CONTAINS CLASS NAME
    @Test
    public void BPTVisitFirstPageOfEventsHappyPathTest(){

        super.BPTFindAnEventPageElementVerificationTest();

        try{

            //navigate to the FindAnEvent page
            driverGetWithTime(testFindAnEventPage.getBasePageUrl());

            //get all event links on the page
            List<WebElement> webElementlinks = driver.findElements(testFindAnEventPage.eventLinks);

            //verify each event page
            int originalWebElementLinksSize = webElementlinks.size();

            //PLACEHOLDERS FOR THE CURRENT LINK BEING VISITED, SINCE THE BACK AND FORTH BETWEEN THE FIND EVENTS PAGE
            //AND THE INDIVIDUAL EVENTS PAGE WILL PUT THE ORIGINAL WEBELEMENT LIST INTO A STALE STATE
            WebElement eventLink=null;
            String eventLinkHref=null;
            boolean evenLinkIsDisplayed=false;
            boolean evenLinkIsEnabled=false;

            System.out.println("NUMBER OF EVENTS ON FIRST PAGE:"+originalWebElementLinksSize);

            for(int i=0;i<originalWebElementLinksSize;i++){
                if(i>=webElementlinks.size()){
                    System.out.println("WARNING: NUMBER OF ELEMENTS ON PAGE "+testFindAnEventPage.getBasePageUrl()+" INCREASED FROM:"+Integer.toString(originalWebElementLinksSize)+" TO:"+i+" DURING TEST");
                    break;
                }

                eventLink = webElementlinks.get(i);
                eventLinkHref = eventLink.getAttribute("href");
                evenLinkIsDisplayed = eventLink.isDisplayed();
                evenLinkIsEnabled = eventLink.isEnabled();
                if(evenLinkIsDisplayed && evenLinkIsEnabled) {
                    //CLICK THE LINK
                    eventLink.click();

                    (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                            .until(ExpectedConditions.stalenessOf(eventLink));

                    //TODO: RUN THE EVENT PAGE ELEMENT VERIFICATION TEST

                    //go back
                    driver.navigate().back();

                    //RULE: look for event page indicator; dont hardcode sleep
                    (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                            .until(ExpectedConditions.presenceOfElementLocated(testFindAnEventPage.getBasePageSanityCheckElements()[0]));
                }
                else{
                    System.out.println("EVENT LINK DETECTED BUT NOT DISPLAYED AND ENABLED:" + eventLinkHref);
                }

                //refresh stale web elements
                webElementlinks = driver.findElements(testFindAnEventPage.eventLinks);
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}


