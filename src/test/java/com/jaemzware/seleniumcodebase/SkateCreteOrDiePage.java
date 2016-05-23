package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by jameskarasim on 5/23/16.
 */
public class SkateCreteOrDiePage {

    private WebDriver driver=null;

    public String pageUrl = "http://skatecreteordie.com";
    public By pageTitle = By.xpath("//title[contains(text(),'skatecreteordie – skatepark mapplication')]");
    public By pageHeader = By.xpath("//header/h1[@class='entry-title']");

    SkateCreteOrDiePage(WebDriver driver) throws Exception{
        this.driver = driver;
    }
}
