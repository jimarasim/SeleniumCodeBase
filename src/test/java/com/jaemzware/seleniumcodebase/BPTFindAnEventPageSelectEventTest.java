
package com.jaemzware.seleniumcodebase;
        import org.junit.Assert;
        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.List;
        import java.util.Random;

        import static com.jaemzware.seleniumcodebase.ParameterType.defaultImplicitWaitSeconds;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTFindAnEventPageSelectEventTest extends BPTFindAnEventPageTest {

    //FUNCTIONAL TEST PAGE OBJECTS
    //  THIS IS A FUNCTIONAL TEST THAT INVOLVES VERIFICATION ON MORE THAN ONE PAGE,
    //  SO THERE WILL BE MORE THAN ONE PAGE OBJECT NEEDED,
    //  IN ADDITION TO THE PAGE OBJECT WE ALREADY HAVE FROM THE CLASS THIS IS DERIVED FROM
    BPTEventPage testEventPage = new BPTEventPage();

    /**
     * Verifies an event can be selected
     */
    @Test
    public void BPTFindAnEventPageSelectFirstEventTest() throws Exception {

        super.BPTFindAnEventPageElementVerificationTest();

        if(!IsElementPresent(testFindAnEventPage.firstEventLink)) {
            throw new Exception("FAIL: COULD NOT FIND AN EVENT AT XPATH:"+testFindAnEventPage.firstEventLink);
        }
        else{
            System.out.println("PASS: FOUND FIRST EVENT AT XPATH:"+testFindAnEventPage.firstEventLink);
        }

        WebElement firstEventWebElement = driver.findElement(testFindAnEventPage.firstEventLink);

        //AUTOMATIONSTEP: CLICK THE FIRST EVENT LINK
        firstEventWebElement.click();

        //WAIT FOR LINK TO GO STALE TO INDICATE RESULTANT PAGE IS READY
        (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                .until(ExpectedConditions.stalenessOf(firstEventWebElement));

        System.out.println("PASS: FIRST EVENT SELECTED");
    }

    @Test public void BPTFindAnEventPageSelectSecondEventTest() throws Exception {

        super.BPTFindAnEventPageElementVerificationTest();

        if(!IsElementPresent(testFindAnEventPage.secondEventLink)) {
            throw new Exception("FAIL: COULD NOT FIND AN EVENT AT XPATH:"+testFindAnEventPage.secondEventLink);
        }
        else{
            System.out.println("PASS: FOUND FIRST EVENT AT XPATH:"+testFindAnEventPage.secondEventLink);
        }

        WebElement secondEventWebElement = driver.findElement(testFindAnEventPage.secondEventLink);

        //AUTOMATIONSTEP: CLICK THE FIRST EVENT LINK
        secondEventWebElement.click();

        //WAIT FOR LINK TO GO STALE TO INDICATE RESULTANT PAGE IS READY
        (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                .until(ExpectedConditions.stalenessOf(secondEventWebElement));

        System.out.println("PASS: SECOND EVENT SELECTED");


    }

    @Test public void BPTFindAnEventPageSelectRandomEventTest() throws Exception{
        super.BPTFindAnEventPageElementVerificationTest();

        if(!IsElementPresent(testFindAnEventPage.eventLinks)) {
            throw new Exception("FAIL: COULD NOT FIND ANY EVENTS AT XPATH:"+testFindAnEventPage.eventLinks);
        }
        else{
            System.out.println("PASS: FOUND SOME EVENTS AT XPATH:"+testFindAnEventPage.eventLinks);
        }

        //COUNT THE NUMBER OdsF EVENTS
        List<WebElement> webElements = driver.findElements(testFindAnEventPage.eventLinks);
        int numberOfEventLinks = webElements.size();

        //GENERATE A RANDOM NUMBER FROM ALL EVENT LINKS DETECTED
        Random randomGenerator = new Random();
        int randomEvent = randomGenerator.nextInt(numberOfEventLinks);

        //CREATE BY DEFINITION IN ORDER TO GET THE CORRESPONDING WEBELEMENT
        By randomEventLink = By.xpath("//td[contains(@onclick,'group')]["+randomEvent+"]");

        //GET THE RANDOM EVENT WEBELEMENT
        WebElement randomEventWebElement = driver.findElement(randomEventLink);

        //TODO: HAVING ISSUES FINDING ELEMENTS ON THE PAGE FOR HIGHER NUMBER EVENT INDECES

        //AUTOMATIONSTEP: CLICK THE FIRST EVENT LINK
        randomEventWebElement.click();

        //WAIT FOR LINK TO GO STALE TO INDICATE RESULTANT PAGE IS READY
        (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                .until(ExpectedConditions.stalenessOf(randomEventWebElement));

        System.out.println("PASS: RANDOM EVENT SELECTED");
    }
}

