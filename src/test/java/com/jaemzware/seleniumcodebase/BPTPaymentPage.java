package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 6/25/16.
 */
public class BPTPaymentPage extends BasePage {
    //TODO THE PAYMENT PAGE REQUIRES NAVIGATION THROUGH WEB FORM ELEMENTS; YOU CANT JUST GO DIRECTLY TO THE URL
    //TODO THUS THIS CLASS IS ALL MOCK DATA FOR NOW. E.G. basePageUrl IS NOT THE PAYMENT PAGE
    public String basePageUrl = "https://brownpapertickets.com";
    public By pageTitle  = By.xpath("//title[contains(text(),'Brown Paper Tickets - The fair-trade ticketing company.')]");
    public By pageHeader = By.xpath("//*[contains(text(),'The fair-trade ticketing company.')]");

    //RULE: we want to make sure every page object class has this for polymorphic usage in CodeBase
    public String getBasePageUrl(){
        return basePageUrl;
    }

    //RULE: we want to make sure every page object returns an array of locators that it must have for a sanity check
    public By[] getBasePageSanityCheckElements(){
        By[] sanityCheckElements = {pageTitle,pageHeader};
        return sanityCheckElements;
    }
}
