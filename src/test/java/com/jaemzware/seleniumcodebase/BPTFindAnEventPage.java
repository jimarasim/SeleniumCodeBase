package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 6/26/16.
 */
public class BPTFindAnEventPage {

    public String pageUrl = "https://tony.bpt.me/browse.html";
    public By pageTitle  = By.xpath("//title[contains(text(),'Brown Paper Tickets - The fair-trade ticketing company.')]");
    public By pageHeader = By.xpath("//*[contains(text(),'The fair-trade ticketing company.')]");
    public By eventLinks = By.xpath("//a[contains(@href,'group')]");
    public By firstEventLink = By.xpath("//a[contains(@href,'group')][1]");
}