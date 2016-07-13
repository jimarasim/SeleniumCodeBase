
package com.jaemzware.seleniumcodebase;
        import org.junit.Assert;
        import org.junit.Test;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import static com.jaemzware.seleniumcodebase.ParameterType.defaultImplicitWaitSeconds;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTFindAnEventPageSelectFirstEventTest extends BPTFindAnEventPageTest {

    //FUNCTIONAL TEST PAGE OBJECTS
    //  THIS IS A FUNCTIONAL TEST THAT INVOLVES VERIFICATION ON MORE THAN ONE PAGE,
    //  SO THERE WILL BE MORE THAN ONE PAGE OBJECT NEEDED,
    //  IN ADDITION TO THE PAGE OBJECT WE ALREADY HAVE FROM THE CLASS THIS IS DERIVED FROM
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
                WebElement firstEventWebElement = driver.findElement(testFindAnEventPage.firstEventLink);
                firstEventWebElement.click();
                (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                        .until(ExpectedConditions.stalenessOf(firstEventWebElement));
                System.out.println("PASS: FIRST EVENT SELECTED");
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

