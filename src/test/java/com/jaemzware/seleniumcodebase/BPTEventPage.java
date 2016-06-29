package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTEventPage {
    public String pageBaseUrl = "http://brownpapertickets.com/event/";
    public By negativePageTitle = By.xpath("/html/head/title[contains(text(),'Brown Paper Tickets - The fair-trade ticketing company.')]");
    public By pageHeader = By.xpath("//*[contains(text(),'The fair-trade ticketing company.')]");
    public By eventLinks = By.xpath("//a[contains(@href,'group')]");
    public By firstEventLink = By.xpath("//a[contains(@href,'group')][1]");
}