package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 6/26/16.
 */
//RULE: EVERY PAGE OBJECT CLASS DERIVES FROM BASEPAGE
public class BPTEventPage extends BasePage{
    public String basePageUrl = "http://brownpapertickets.com/event/";
    public By negativePageTitle = By.xpath("/html/head/title[contains(text(),'Brown Paper Tickets - The fair-trade ticketing company.')]");
    public By pageHeader = By.xpath("//*[contains(text(),'The fair-trade ticketing company.')]");
    public By addToCartButton = By.xpath("//button[contains(text(),'Add to Cart')]");
    public By pageLoadedIndicator = By.xpath("//td[contains(text(),'Get Tickets')]");

    //RULE: we want to make sure every page object defines a base page url
    public String getBasePageUrl(){
        return basePageUrl;
    }

    //RULE: we want to make sure every page object returns an array of locators that it must have for a sanity check
    public By[] getBasePageSanityCheckElements(){
        By[] sanityCheckElements = {pageHeader,addToCartButton,pageLoadedIndicator};
        return sanityCheckElements;
    }
}