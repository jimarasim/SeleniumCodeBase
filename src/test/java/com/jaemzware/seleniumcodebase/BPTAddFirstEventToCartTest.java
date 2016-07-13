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
public class BPTAddFirstEventToCartTest extends BPTFindAnEventPageSelectFirstEventTest {

    /**
     * This test adds the first event on the find events page to the shopping cart
     */
    @Test
    public void BPTAddFirstEventToCart() throws Exception {

        super.BPTFindAnEventPageSelectFirstEventTest();

        //TODO PUT bptAddToCartButton IN A PAGE CLASS DEFINITION
        By bptAddToCartButton = By.xpath("//button[@class='bpt_orangebutton']");

        //TODO PUT bptAddToCartMemberQuantityDropdown IN A PAGE CLASS DEFINITION
        By bptAddToCartMemberQuantityDropdown = By.name("price_4857813");

        //look for the select button and select a quantity
        if(IsElementPresent(bptAddToCartMemberQuantityDropdown)) {
            new Select(driver.findElement(bptAddToCartMemberQuantityDropdown)).selectByVisibleText("10");
        }

        //TODO: DEFINE AN EXCEPTION CLASS FOR AUTOMATION ERRORS
        //TODO: MAKE SURE SCREENSHOTS ARE TAKEN WHEN AN AUTOMATION ERROR OCCURS

        //AUTOMATIONSTEP: LOOK FOR ADD TO CART BUTTON AND CLICK IT
        if(!IsElementPresent(bptAddToCartButton)){
            throw new Exception("COULD NOT FIND ADD TO CART BUTTON");
        }

        WebElement weAddToCartButton = driver.findElement(bptAddToCartButton);
        weAddToCartButton.click();

        //TODO: ADD A CONVENIENCE METHOD FOR STALE WAIT TO CODEBASE WaitForStaleElement(By)
        //WAIT FOR LINK TO GO STALE TO INDICATE RESULTANT PAGE IS READY

        (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                .until(ExpectedConditions.stalenessOf(bptAddToCartButton));

        //LOOK FOR ADD TO CART BUTTON AND CLICK IT
        if(IsElementPresent(bptAddToCartButton)){
            driver.findElement(bptAddToCartButton).click();
        }

        //WAIT FOR LINK TO GO STALE TO INDICATE RESULTANT PAGE IS READY
        (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                .until(ExpectedConditions.stalenessOf(firstEventWebElement));


        //TODO PUT bptBigAddToCartButton IN A PAGE CLASS DEFINITION
        By bptCheckOutNowButton = By.xpath("//button[@class='bpt_orangebutton']");

        //look for the add to cart button and click it
        if(IsElementPresent(bptCheckOutNowButton)){
            driver.findElement(bptCheckOutNowButton).click();
        }

        //TODO: ADD A CONVENIENCE METHOD
        //wait for checkout now button to go stale
        (new WebDriverWait(driver, defaultImplicitWaitSeconds))
                .until(ExpectedConditions.stalenessOf(bptCheckOutNowButton));


        Thread.sleep(60000);
    }

    @Test
    public void BPTAddFirstEventWithoutTickets() throws InterruptedException {

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
