package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;
import sun.reflect.generics.tree.BaseType;

/**
 * Created by jameskarasim on 5/23/16.
 */
public class SkateCreteOrDiePage  {
    public String pageUrl = "http://skatecreteordie.com";
    public By pageTitle  = By.xpath("//title[contains(text(),'skatecreteordie – skatepark mapplication')]");
    public By pageHeader = By.xpath("//header/h1[@class='entry-title']");
}
