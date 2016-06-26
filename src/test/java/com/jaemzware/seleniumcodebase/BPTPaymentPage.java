package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 6/25/16.
 */
public class BPTPaymentPage {
    public String pageUrl = "https://brownpapertickets.com";
    public By pageTitle  = By.xpath("//title[contains(text(),'Brown Paper Tickets - The fair-trade ticketing company.')]");
    public By pageHeader = By.xpath("//*[contains(text(),'The fair-trade ticketing company.')]");
}
