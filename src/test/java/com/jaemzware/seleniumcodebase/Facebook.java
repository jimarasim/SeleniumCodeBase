/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaemzware.seleniumcodebase;
import static com.jaemzware.seleniumcodebase.ParameterType.*;
import static com.jaemzware.seleniumcodebase.Scratch.properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
/**
 *
 * @author jameskarasim
 */
public class Facebook extends CodeBase{
    static final String propertiesFile = 
            "src/test/java/com/jaemzware/seleniumcodebase/selenium.properties";
    static Properties properties = new Properties();
    
    private final String loginButtonXpath="//input[@value='Log In']";
    @Before
    public void BeforeTest() {
        try {

            // properties file is in same directory as pom.xml
            properties.load(new FileInputStream(propertiesFile));

            // initialize verifification errors
            verificationErrors = new StringBuilder();

            // get the command line parameters that were specified
            // an error string will be returned if something went wrong
            // an empty string will be returned if everything is ok
            String getParameterResult = GetParameters();
            if (!getParameterResult.isEmpty()) {
                System.out.println(getParameterResult);
                throw new InvalidParameterException();
            }

        } catch (InvalidParameterException ipex) {
            Assert.fail("INVALID PARAMETERS FOUND:"+ipex.getMessage());
        } catch (FileNotFoundException fnfex) {
            Assert.fail(propertiesFile + " NOT FOUND:"+fnfex.getMessage());
        } catch (IOException ioex) {
            Assert.fail(propertiesFile + " IO EXCEPTION:"+ioex.getMessage());
        }
    
    }
    
    @Test
    public void FaceCrawlAllLinks() {
        
        String linksOnSplashPageXpath = 
            "//a[@href and not(@href='') and not(contains(@href,'javascript:')) and not(contains(@href,'mailto:'))]";
        String fileName = "index.htm";
        PrintWriter writer = null;
        String fileWriteString;

        try {
                
            // get xpath to look for
            String logoxpath = new String();
            if (aString != null && !aString.isEmpty()) {
                logoxpath = aString;
            } else {
                throw new Exception("LOGOXPATH NOT SPECIFIED (-DaString");
            }
            
            // get START url. first page to load
            String starturl = new String();
            if (input != null && !input.isEmpty()) {
                starturl = input;
            } else {
                throw new Exception("START URL NOT SPECIFIED (-Dinput)");
            }
            
            ////////////////////
            // open browser
            StartDriver();
            //check if driver setting was successful
            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
            
            //set defaultImplicitWaitSeconds
            if(!browser.toString().contains("APPIUM")){
                //set implicit wait
                driver.manage().timeouts().implicitlyWait(defaultImplicitWaitSeconds, TimeUnit.SECONDS);
            }

            //CREATE A REPORT WEB PAGE
            // create the web page
            writer = new PrintWriter(fileName, "UTF-8");

            // write the html header in the web page
            writer.println(HtmlReportHeader("VerifyLogos:<a href='"+starturl+"' target='_blank'>"+starturl+"</a> [linksOnSplashPageXpath (linksOnSplashPageXpath):"+linksOnSplashPageXpath+" starturl:"+starturl+" logoxpath:"+logoxpath+"]"));

            // NAVIGATE TO THE STARTING PAGE
            System.out.println("STARTURL:"+starturl);
            fileWriteString = driverGetWithTime(starturl);
            
            //LOGIN
            if(IsElementPresent(By.id("email") )){
               driver.findElement(By.id("email")).sendKeys("wontwon@joeypaintbrush.com");
               if(IsElementPresent(By.id("pass") )){
                   driver.findElement(By.id("pass")).sendKeys("Face@Book");
                   if(IsElementPresent(By.xpath(loginButtonXpath))){
                       driver.findElement(By.xpath(loginButtonXpath)).click();
                   }
                   else{
                       verificationErrors.append("LOGIN BUTTON NOT FOUND ON LOGIN PAGE AT XPATH:"+loginButtonXpath+" MAY ALREADY BE LOGGED IN");
                   }
               }   
               else{
                   verificationErrors.append("PASS TEXT BOX NOT FOUND ON LOGIN PAGE. MAY ALREADY BE LOGGED IN");
               }
            }
            else{
                   verificationErrors.append("EMAIL TEXT BOX NOT FOUND ON LOGIN PAGE. MAY ALREADY BE LOGGED IN");
            }

            /*
            * TODO - REFACTOR THIS HARDCODED WAIT
            */
            System.out.println(quickWaitMilliSeconds+"MILLI SECONDS TO LET FACEBOOK LOAD");
            Thread.sleep(quickWaitMilliSeconds);
            
            //GO TO PROFILE PAGE
            driver.findElement(By.xpath("//a[@title='Profile']")).click();
            
            /*
            * TODO - REFACTOR THIS HARDCODED WAIT
            */
            Thread.sleep(quickWaitMilliSeconds);

            // write stats to html report
            writer.println(fileWriteString);
            //LOGGING
            if(System.getProperty("logging")==null){
                System.out.println("LOGGING DISABLED - USE -Dlogging TO SEE BROWSER ERRORS AND WARNINGS");
            } 
            else if(browser.toString().contains("APPIUM")){
                System.out.println("LOGGING NOT SUPPORTED WITH APPIUM");
            }
            else {
                writer.println(ExtractJSLogs());
            }
            
            // verify logo
            System.out.println("VERIFYING LOGO AT:"+logoxpath+" ON:"+starturl);
            writer.println(VerifyXpathOnCurrentPage(logoxpath));
            
            //GET HREFS
            // get all non-empty/non-javascript href on the page that contain the baseurlxpath
            Map<String, String> hrefs = new HashMap<>();
            String hrefFound;

            //getting all internal hrefs
            System.out.println("GETTING ALL INTERNAL ANCHORS MATCHING XPATH:"+linksOnSplashPageXpath+" ON:"+starturl);
                        
            if (IsElementPresent(By.xpath(linksOnSplashPageXpath))) {
                
                System.out.println("FINDING ANCHOR ELEMENTS");
                List<WebElement> internalAnchors = driver.findElements(By.xpath(linksOnSplashPageXpath));
                
                if(internalAnchors.size()<1){
                    throw new Exception("NO LINKS FOUND AT XPATH:"+linksOnSplashPageXpath+" ON PAGE:"+starturl);
                }
                
                for (WebElement we : internalAnchors) {
                    try {
                        hrefFound = we.getAttribute("href");
                    } catch (Exception ex) {
                        System.out.println("WARNING: EXCEPTION GETTING HREF FROM LIST.");
                        writer.println("<span class='warning'>WARNING: EXCEPTION GETTING HREF FROM LIST</span>");
                        break;
                    }

                    
                    //getting a null pointer exception on hreffound for some reason sometimes
                    if(hrefFound==null){
                        verificationErrors.append("HREFFOUND WAS NULL");
                        continue;
                    }
                    
                    // only get hrefs that contain the base url, and html like pages
                    if (hrefFound.toLowerCase().contains("www.facebook.com") && 
                            !hrefFound.toLowerCase().contains("?ak_action") &&
                            !hrefFound.toLowerCase().contains("feed") &&
                            !hrefFound.toLowerCase().contains("rss") &&
                            !hrefFound.toLowerCase().contains("javascript") &&
                            !hrefFound.toLowerCase().contains(".jpg") &&
                            !hrefFound.toLowerCase().contains(".jpeg") &&
                            !hrefFound.toLowerCase().contains(".png") &&
                            !hrefFound.toLowerCase().contains(".gif")) {
                        hrefs.put(hrefFound, starturl);
                        System.out.println("WILL VISIT:" + hrefFound);
                    } else {
                        System.out
                                .println("SKIPPING: URL:" + hrefFound+" ON:"+starturl + " DOES NOT CONTAIN BASE URL XPATH (linksOnSplashPageXpath):" + linksOnSplashPageXpath);
                    }

                }
            } else {
                System.out.println("WARNING: NO LINKS FOUND ON PAGE MATCHING BASE URL XPATH (linksOnSplashPageXpath):" + linksOnSplashPageXpath+" ON:"+starturl);
            }

            //////////////////////////
            //VISIT HREFS
            // visit each href, report load time, and make sure the page has the logo
            int maxVisits = aNumber; //check if the max number was specified
            int visitCount = 0;
            
            System.out.println("VISITING HREFS FOUND AT XPATH:"+linksOnSplashPageXpath+" ON:"+starturl);
            for (String href : hrefs.keySet()) {

                // make sure we're on a real page, and not an image
                if (    href.endsWith(".jpg") || 
                        href.endsWith(".gif") || 
                        href.endsWith("rss2")) {
                    continue;
                }
                
                String oldUrl=driver.getCurrentUrl();
            
                //GET A LINK IN ALL THE LINKS WE FOUND, AND HIT IT AT RANDOM TIMES
                fileWriteString = driverGetWithTime(href,2);
                
                // write stats to html report
                writer.println(fileWriteString);
                
                //ERROR LOGGING - TAKES LONG - ADD CAPABILITY WHEN CREATING driver BEFORE USING
                if(System.getProperty("logging")==null || 
                        browser.toString().contains("APPIUM") ){
                } else {
                    System.out.println("WRITING OUT LOGS FOR:"+href);
                    writer.println(ExtractJSLogs());
                }
                
                System.out.println("VERIFYING LOGO AT:"+logoxpath+" ON:"+href);
                writer.println(VerifyXpathOnCurrentPage(logoxpath));
                
                //check the desired image count, and break if it's been reached
                if((maxVisits>0) && (++visitCount>maxVisits-1)){
                    break;
                }   
                
            }

            //COMPLETE WRITING REPORT WEB PAGE
            System.out.println("LOCAL REPORT WRITTEN:" + fileName);
            writer.println(HtmlReportFooter());

        } catch (Exception ex) {
            ScreenShot();
            System.out.println("FACECRAWL EXCEPTION:"+ex.getMessage());
        } finally {
            //WRITE THE FILE IF CREATED
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }

    }
    
    @After
    public void AfterTest() {
        try {
            if (driver != null) {
                QuitDriver();
            }

            // check if there were any verify errors, and fail whole test if so
            if (verificationErrors.length() > 0) {
                System.out.println("\nLOGO VERIFICATION REPORT\n" + verificationErrors.toString());
            }
        } catch (Exception ex) {
            CustomStackTrace("AFTER EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        }
    }
}
