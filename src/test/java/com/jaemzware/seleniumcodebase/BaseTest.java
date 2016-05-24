package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by jameskarasim on 5/24/16.
 */
public abstract class BaseTest {

    public WebDriver driver=null;

    public String pageUrl;// = "http://skatecreteordie.com";
    public By pageTitle;// = By.xpath("//title[contains(text(),'skatecreteordie – skatepark mapplication')]");
    public By pageHeader;// = By.xpath("//header/h1[@class='entry-title']");


}
