package com.jaemzware.seleniumcodebase;

import static com.jaemzware.seleniumcodebase.AutomationCodeBase.GetParameters;
import static com.jaemzware.seleniumcodebase.AutomationCodeBase.environment;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebElement;

/**
 *@author jaemzware@hotmail.com
 * 
 */
public class Scratch extends AutomationCodeBase
{
    static Properties properties = new Properties();
    
    @Before
    public void BeforeTest()
    {
        try
        {//start the webdriver
            
            //properties file is in same directory as pom.xml
            properties.load(new FileInputStream("src/test/java/com/jaemzware/seleniumcodebase/selenium.properties"));
            
            //get input parameters HERE
            GetParameters();
            
            StartDriver();
        }
        catch(Exception ex)
        {
            Assert.fail("BEFORE TESTS EXCEPTION:"+ex.getMessage());
        }
    }
    
    @Test
    public void OpenUrl()
    {
        try
        {
            final String url = properties.getProperty(environment.toString()+".url");
            driver.get(url);
            Thread.sleep(5000);
            
        }
        catch(Exception ex)
        {
            ScreenShot();
            CustomStackTrace("Scratch exception",ex);
            Assert.fail(ex.getMessage());
        }
    }
    
    
    @After
    public void AfterTest()
    {
        try
        {
            QuitDriver();
        }
        catch(Exception ex)
        {
            ScreenShot();
            this.CustomStackTrace("After test exception",ex);
            Assert.fail(ex.getMessage());
        }
    }
}
