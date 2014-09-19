package com.jaemzware.seleniumcodebase;

import static com.jaemzware.seleniumcodebase.AutomationCodeBase.browser;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

/**
 * @author jaemzware@hotmail.com
 */
public class Scratch extends AutomationCodeBase {
    static Properties properties = new Properties();
    StringBuilder verificationErrors;

    // monster tamer
    private final String monsterTamerDomain = "monster-tamer.com";
    private final String monsterTamer404Xpath = "//h1[contains(text(),'404 Page')]";
    private final String linksOnSpashPageXpath = "//a[@href and not(@href='') and not(contains(@href,'javascript:'))]";

    @Before
    public void BeforeTest() {
        try {
            // properties file is in same directory as pom.xml
            properties.load(new FileInputStream("src/test/java/com/jaemzware/seleniumcodebase/selenium.properties"));

            // initialize verifification errors
            verificationErrors = new StringBuilder();

            // get input parameters HERE
            GetParameters();
        } catch (Exception ex) {
            CustomStackTrace("BEFORE TESTS EXCEPTION", ex);
            Assert.fail("BEFORE TESTS EXCEPTION:" + ex.getMessage());
        }
    }

    
    
    @Test
    public void VerifyLogos() {

        //create a file for the web page log
        String fileName = "Index-VerifyLogos-" + getDateStamp() + ".htm";
        PrintWriter writer = null;
        String fileWriteString = "";

        try {
            
            //create the web page
            writer = new PrintWriter(fileName, "UTF-8");

            //write the html header in the web page
            writer.println(HtmlReportHeader("VerifyLogos"));

            // open browser
            StartDriver();

            // get base url
            String url = new String();
            if (input != null && !input.isEmpty()) {
                url = input;
            } else {
                throw new Exception("URL NOT SPECIFIED (-Dinput)");
            }

            // get xpath to look for
            String logoxpath = new String();
            if (aString != null && !aString.isEmpty()) {
                logoxpath = aString;
            } else {
                throw new Exception("LOGOXPATH NOT SPECIFIED (-DaString");
            }

            // navigate to the starting page
            fileWriteString = driverGetWithTime(url);

            // write stats to html report
            writer.println(fileWriteString);
            
            //write debug info to the html report
            if(browser.equals(BrowserType.CHROMEMAC)||browser.equals(BrowserType.CHROMELINUX)||browser.equals(BrowserType.CHROMEMAC)){
                writer.println(ExtractJSLogs());
                
            }

            // get all non-empty/non-javascript href on the page
            Map<String, String> hrefs = new HashMap<String, String>();
            String hrefFound;
            
            if(IsElementPresent(By.xpath(linksOnSpashPageXpath))){
                List<WebElement> internalAnchors = driver.findElements(By.xpath(linksOnSpashPageXpath));
                int exceptionCount = 0;
                for (WebElement we : internalAnchors) {
                    // TODO FIND OUT WHY THIS THROWS AN EXCEPTION SOMETIMES
                    try {
                        we.getAttribute("href");
                    } catch (Exception ex) {
                        System.out.println("EXCEPTION GETTING HREF FROM LIST. COUNT:" + exceptionCount++);
                        continue;
                    }
                    hrefFound = we.getAttribute("href");

                    //only visit hrefs that contain the base url
                    if (hrefFound.contains(url)) {
                        hrefs.put(hrefFound, url);
                        System.out.println("WILL VISIT:"+hrefFound);
                    }
                    else{
                        System.out.println("SKIPPING: "+hrefFound+" DOES NOT CONTAIN BASE URL:"+url);
                    }
                        
                }
            }
            else{
                System.out.println("WARNING: NO LINKS FOUND ON PAGE MATCHING XPATH:"+linksOnSpashPageXpath);
            }

            // visit each href, report load time, and make sure the page has the logo
            for (String href : hrefs.keySet()) {

                // go to the href
                fileWriteString = driverGetWithTime(href);
                
                // write stats to html report
                writer.println(fileWriteString);
                
                //write debug info to the html report
                if(browser.equals(BrowserType.CHROMEMAC)||browser.equals(BrowserType.CHROMELINUX)||browser.equals(BrowserType.CHROMEMAC)){
                    writer.println(ExtractJSLogs());
                }

                // save off the page for later analysis
                RestRequest(href);

                // if logo is not present, don't assert/fail, just add a verification error,
                // so all links get checked regardless of ones that fail

                // make sure we're on a real page, and not an image
                if (    !href.endsWith(".jpg") && 
                        !href.endsWith(".gif") &&
                        !href.endsWith("rss2")) {
                    // verify logo
                    if (!IsElementPresent(By.xpath(logoxpath), 1000)) {
                        verificationErrors.append("URL:").append(href).append(" MISSING LOGO:").append(logoxpath)
                                .append("\n");

                        // write error to html report
                        fileWriteString = "<br />ISSUE:MISSING LOGO URL:<a href='" + href + "' target='_blank'>" + href
                                + "</a>";
                        writer.println(fileWriteString);

                    }

                    // check for 404 (monster-tamer)
                    if (href.contains(monsterTamerDomain) && IsElementPresent(By.xpath(monsterTamer404Xpath), 1000)) {
                        verificationErrors.append("URL:").append(href).append(" 404 PAGE:")
                                .append(monsterTamer404Xpath).append("\n");

                        // write error to html report
                        fileWriteString = "<br />ISSUE:404 URL:<a href='" + href + "' target='_blank'>" + href + "</a>";
                        writer.println(fileWriteString);
                    }

                }

            }

            writer.println(HtmlReportFooter());

            System.out.println("INDEX FILE WRITTEN:" + jenkinsReportPath + fileName);

        } catch (Exception ex) {
            ScreenShot();
            CustomStackTrace("VerifyLogos EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        } finally {
            // write the file if it was created
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    @Test
    public void CheckGmail() {
        // uses app password for gmail. app name "Scratch" https://support.google.com/accounts/answer/185833
        try {
            System.out.println(GetFirstEmailMessageForSearchTerm("imap.gmail.com", "jaemzware", "jhnuhivmbvubjuds",
                    "Inbox", "Metabliss", 30000));

        } catch (Exception ex) {
            System.out.println("COULD NOT GET EMAIL:" + ex.getMessage());
        }
    }

    @Test
    public void MakeRestRequest() {
        try {
            // get base url
            String url = new String();
            if (input != null && !input.isEmpty()) {
                url = input;
            } else {
                throw new Exception("URL NOT SPECIFIED (-Dinput)");
            }

            RestRequest(url);
        } catch (Exception ex) {
            CustomStackTrace("RestRequest EXCEPTION", ex);
            Assert.fail(ex.getMessage());
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
                Assert.fail("\nVERIFICATION ERRORS\n" + verificationErrors.toString());
            }
        } catch (Exception ex) {
            CustomStackTrace("AFTER EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        }
    }
    
    /**
     * get js log errors, if any
     * @return 
     */
    private String ExtractJSLogs() {
        StringBuilder logString = new StringBuilder();
        
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            logString.append(getDateStamp()).append(entry.getLevel()).append("<b>").append(entry.getMessage()).append("</b><br />");
        }
        
        return logString.toString();
    }
    
}
