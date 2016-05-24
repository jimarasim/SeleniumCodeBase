package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import sun.reflect.generics.tree.BaseType;

/**
 * Created by jameskarasim on 5/23/16.
 */
public class SkateCreteOrDiePage extends BaseTest {

    private WebDriver driver=null;

    SkateCreteOrDiePage(WebDriver driver) throws Exception{
        this.driver = driver;
        pageUrl = "http://skatecreteordie.com";
        pageTitle = By.xpath("//title[contains(text(),'skatecreteordie – skatepark mapplication')]");
        pageHeader = By.xpath("//header/h1[@class='entry-title']");
    }
}
