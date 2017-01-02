package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jamesa on 7/7/16.
 */
public abstract class BasePage {
    public abstract String getBasePageUrl();
    public abstract By[] getBasePageSanityCheckElements();
    public static final String baseSiteUrl="https://jaemzware.com/";
}
