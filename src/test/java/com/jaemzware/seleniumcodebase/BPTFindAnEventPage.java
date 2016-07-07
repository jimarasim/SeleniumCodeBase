package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTFindAnEventPage extends BasePage {
    public String basePageUrl = "https://brownpapertickets.com/browse.html";
    public By pageTitle  = By.xpath("//title[contains(text(),'Brown Paper Tickets - The fair-trade ticketing company.')]");
    public By pageHeader = By.xpath("//*[contains(text(),'The fair-trade ticketing company.')]");
    public By eventLinks = By.xpath("//td[contains(@onclick,'group')]");
    public By firstEventLink = By.xpath("//td[contains(@onclick,'group')][1]");

    //RULE: we want to make sure every page object class has this for polymorphic usage in CodeBase
    public String getBasePageUrl(){
        return basePageUrl;
    }

    //RULE: we want to make sure every page object returns an array of locators that it must have for a sanity check
    public By[] getBasePageSanityCheckElements(){
        By[] sanityCheckElements = {pageTitle,pageHeader,eventLinks,firstEventLink};
        return sanityCheckElements;
    }
}
