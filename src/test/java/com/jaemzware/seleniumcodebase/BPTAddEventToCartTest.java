package com.jaemzware.seleniumcodebase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.jaemzware.seleniumcodebase.ParameterType.defaultImplicitWaitSeconds;

/**
 * Created by jamesa on 7/13/16.
 */
public class BPTAddEventToCartTest extends BPTFindAnEventPageSelectEventTest {
    //TODO PUT bptAddToCartMemberQuantityDropdown IN A PAGE CLASS DEFINITION
    private static final By byBptAddToCartMemberQuantityDropdown = By.xpath("//select[@class='bpt_widget_input' and contains(@name,'price_')]");
    //TODO PUT bptAddToCartButton IN A PAGE CLASS DEFINITION
    private static final By byBptAddToCartButton = By.xpath("//button[@class='bpt_orangebutton']");
    //TODO PUT bptBigAddToCartButton IN A PAGE CLASS DEFINITION
    private static final By bptCheckOutNowButton = By.xpath("//button[@class='bpt_orangebutton' or @class='bpt_bigorangebutton']");

    //TODO: MAKE SURE SCREENSHOTS ARE TAKEN WHEN AN AUTOMATION ERROR OCCURS
    /**
     * This test adds the first event on the find events page to the shopping cart
     */
    @Test
    public void BPTAddFirstEventToCart() throws Exception {

        defaultImplicitWaitSeconds = 60;

        super.BPTFindAnEventPageSelectSecondEventTest();

        //AUTOMATIONSTEP: ASSERT THE QUANTITY DROP DOWN EXISTS
        if(!IsElementPresent(byBptAddToCartMemberQuantityDropdown)) {
            //TODO: DEFINE AN EXCEPTION CLASS FOR AUTOMATION ERRORS
            throw new Exception("COULD NOT FIND QUANTITY DROPDOWN AT XPATH:"+byBptAddToCartMemberQuantityDropdown);
        }
        else{
            System.out.println("FOUND QUANTITY DROPDOWN AT XPATH:"+byBptAddToCartMemberQuantityDropdown);
        }

        //AUTOMATIONSTEP: SELECT THE TICKET QUANTITY
        new Select(driver.findElement(byBptAddToCartMemberQuantityDropdown)).selectByVisibleText("10");

        //AUTOMATIONSTEP: ASSERT THE ADD TO CART BUTTON EXISTS
        if(!IsElementPresent(byBptAddToCartButton)){
            throw new Exception("COULD NOT FIND ADD TO CART BUTTON AT XPATH:"+byBptAddToCartButton);
        }
        else{
            System.out.println("PASS: FOUND ADD TO CART BUTTON AT XPATH:"+byBptAddToCartButton);
        }

        //AUTOMATIONSTEP: CLICK THE ADD TO CART BUTTON
        WebElement weBptAddToCartButton = driver.findElement(byBptAddToCartButton);
        weBptAddToCartButton.click();

        //TODO: ADD A CONVENIENCE METHOD FOR STALE WAIT TO CODEBASE WaitForStaleElement(By) throws TimeoutException
        //AUTOMATIONSTEP: WAIT FOR weBptAddToCartButton TO GO STALE TO INDICATE RESULTANT PAGE IS READY
        (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                .until(ExpectedConditions.stalenessOf((WebElement) weBptAddToCartButton));

        //TODO: ADD A CONVENIENCE METHHOD FOR CHECKING AN ELEMENT WITH ITS 'BY' THEN RETURNING A WEBELEMENT REFERENCE public WebElement IsElementPresent(by)
        //AUTOMATIONSTEP: LOOK FOR CHECK OUT NOW BUTTON
        if(!IsElementPresent(bptCheckOutNowButton)){
            throw new Exception("COULD NOT FIND CHECK OUT NOW BUTTON AT XPATH:"+bptCheckOutNowButton);
        }
        else{
            System.out.println("PASS: FOUND CHECK OUT NOW BUTTON AT XPATH:"+bptCheckOutNowButton);
        }

        //AUTOMATIONSTEP: CLICK THE CHECK OUT NOW BUTTON
        WebElement weBptCheckOutNowButton = driver.findElement(bptCheckOutNowButton);
        driver.findElement(bptCheckOutNowButton).click();

        //WAIT FOR CHECK OUT NOW BUTTON TO GO STALE TO INDICATE RESULTANT PAGE IS READY
        (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                .until(ExpectedConditions.stalenessOf(weBptCheckOutNowButton));

        Thread.sleep(30000);
    }

    @Test
    public void BPTAddFirstEventWithoutTickets() throws Exception {

        super.BPTFindAnEventPageSelectFirstEventTest();

        //TODO PUT bptAddToCartButton IN A PAGE CLASS DEFINITION
        By bptAddToCartButton = By.xpath("//button[@class='bpt_orangebutton']");

        //ISSUE: BUTTON SHOULD JUST BE DISABLED IF NO TICKET COUNT IS SELECTED
        //click the add to cart button
        if(IsElementPresent(bptAddToCartButton)){
            driver.findElement(bptAddToCartButton).click();
        }

        //TODO PUT bptSpecifyTicketQuantityNotice IN A PAGE CLASS DEFINITION
        By bptSpecifyTicketQuantityNotice = By.xpath("//td[contains(text(),'You must select one or more tickets to add to your cart.')]");
        if(IsElementPresent(bptSpecifyTicketQuantityNotice)){
            System.out.println("PASS: FOUND MUST SELECT TICKETS MESSAGE AT XPATH:"+bptSpecifyTicketQuantityNotice);
        }
        else{
            System.out.println("FAIL: COULD NOT FIND SELECT TICKETS MESSAGE AT XPATH:"+bptSpecifyTicketQuantityNotice);

        }
    }
}
