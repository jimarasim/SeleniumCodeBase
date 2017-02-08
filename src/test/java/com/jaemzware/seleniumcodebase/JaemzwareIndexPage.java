package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 2/8/17.
 */
public class JaemzwareIndexPage extends BasePage {
    public String getBasePageUrl(){
        return "http://jaemzware.com";
    }

    public By[] getBasePageSanityCheckElements(){

        //TODO OPTIONAL: CREATE PRIVATE VARIABLES FOR EACH XPATH DEFINITION
        By[] sanityCheckElements = new By[] {
                By.xpath("//h1[contains(text(),'jaemzware')]"),
                By.xpath("//a[contains(text(),'skatecreteordie')]"),
                By.xpath("//a[@href='http://seattlenative.org']"),
                By.xpath("//a[@href='https://seattlerules.com/geturls/']"),
                By.xpath("//a[@href='https://itunes.apple.com/us/app/skatecreteordie/id1099516729?ls=1&mt=8']"),
                By.xpath("//a[@href='http://www.skatecreteordie.com']"),
                By.xpath("//a[contains(@href,'http://analogarchive.com/')]"),
                By.xpath("//img[@alt='skatecreteordie park map screenshot']")
        };

        return sanityCheckElements;
    }
}
