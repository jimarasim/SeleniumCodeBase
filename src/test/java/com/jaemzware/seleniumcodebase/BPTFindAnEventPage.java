package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTFindAnEventPage {
    public String pageUrl = "https://brownpapertickets.com/browse.html";
    public By pageTitle  = By.xpath("//title[contains(text(),'Brown Paper Tickets - The fair-trade ticketing company.')]");
    public By pageHeader = By.xpath("//*[contains(text(),'The fair-trade ticketing company.')]");
    public By eventLinks = By.xpath("//td[contains(@onclick,'group')]");
    public By firstEventLink = By.xpath("//td[contains(@onclick,'group')][1]");
}
