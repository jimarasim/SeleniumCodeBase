package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jamesa on 7/7/16.
 */
public abstract class BasePage {

    //RULE: we want to make sure every page object defines a base page url
    public abstract String getBasePageUrl();

    //RULE: we want to make sure every page defines a set of element locators that would suffice a sanity check
    public abstract By[] getBasePageSanityCheckElements();
}
