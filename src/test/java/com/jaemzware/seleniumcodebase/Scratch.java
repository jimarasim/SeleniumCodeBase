package com.jaemzware.seleniumcodebase;

import static com.jaemzware.seleniumcodebase.AutomationCodeBase.browser;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

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
    private final String linksOnSpashPageXpath = "//a[@href and not(@href='') and not(contains(@href,'javascript:')) and not(contains(@href,'mailto:'))]";

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

        // create a file for the web page log
        String fileName = "Index-VerifyLogos-" + getDateStamp() + ".htm";
        PrintWriter writer = null;
        String fileWriteString = "";

        try {


            // open browser
            StartDriver();

        //GET PARAMETERS
            // get START url
            String starturl = new String();
            if (input != null && !input.isEmpty()) {
                starturl = input;
            } else {
                throw new Exception("START URL NOT SPECIFIED (-Dinput)");
            }

            // get base url
            String baseurl = new String();
            if (userid != null && !userid.isEmpty()) {
                baseurl = userid;
            } else {
                throw new Exception("BASE URL NOT SPECIFIED (-Duserid)");
            }

            // get xpath to look for
            String logoxpath = new String();
            if (aString != null && !aString.isEmpty()) {
                logoxpath = aString;
            } else {
                throw new Exception("LOGOXPATH NOT SPECIFIED (-DaString");
            }
            
        //CREATE A REPORT WEB PAGE
            // create the web page
            writer = new PrintWriter(fileName, "UTF-8");

            // write the html header in the web page
            writer.println(HtmlReportHeader("jaemzware-verifylogos [baseurl:"+baseurl+" starturl:"+starturl+" logoxpath:"+logoxpath+"]"));

            // navigate to the starting page
            fileWriteString = driverGetWithTime(starturl);

            // write stats to html report
            writer.println(fileWriteString);

            // write debug info to the html report
            if (browser.equals(BrowserType.CHROME) || 
                    browser.equals(BrowserType.CHROMELINUX) ||
                    browser.equals(BrowserType.CHROMEMAC) 
                    ||
                    browser.equals(BrowserType.FIREFOX) ||
                    browser.equals(BrowserType.FIREFOXLINUX) ||
                    browser.equals(BrowserType.FIREFOXMAC)
                    ) {
                writer.println(ExtractJSLogs());

            }

        //GET HREFS
            // get all non-empty/non-javascript href on the page that contain the baseurl
            Map<String, String> hrefs = new HashMap<String, String>();
            String hrefFound;

            if (IsElementPresent(By.xpath(linksOnSpashPageXpath))) {
                List<WebElement> internalAnchors = driver.findElements(By.xpath(linksOnSpashPageXpath));
                int exceptionCount = 0;
                for (WebElement we : internalAnchors) {
                    // TODO FIND OUT WHY THIS THROWS AN EXCEPTION SOMETIMES
                    try {
                        we.getAttribute("href");
                    } catch (Exception ex) {
                        System.out.println("WARNING: EXCEPTION GETTING HREF FROM LIST. COUNT:" + exceptionCount++);
                        break;
                    }
                    hrefFound = we.getAttribute("href");

                    // only get hrefs that contain the base url
                    if (hrefFound.contains(baseurl)) {
                        hrefs.put(hrefFound, starturl);
                        System.out.println("WILL VISIT:" + hrefFound);
                    } else {
                        System.out
                                .println("SKIPPING: FOUND URL:" + hrefFound + " DOES NOT CONTAIN BASE URL:" + baseurl);
                    }

                }
            } else {
                System.out.println("WARNING: NO LINKS FOUND ON PAGE MATCHING XPATH:" + linksOnSpashPageXpath);
            }

        //VISIT HREFS
            // visit each href, report load time, and make sure the page has the logo
            int maxVisits = (aNumber!=null)?Integer.parseInt(aNumber):0; //check if the max number was specified
            int visitCount = 0;
            for (String href : hrefs.keySet()) {

                // go to the href
                fileWriteString = driverGetWithTime(href);

                // write stats to html report
                writer.println(fileWriteString);

                // write debug info to the html report
                if (browser.equals(BrowserType.CHROME) || 
                        browser.equals(BrowserType.CHROMELINUX) ||
                        browser.equals(BrowserType.CHROMEMAC) 
                        ||
                        browser.equals(BrowserType.FIREFOX) ||
                        browser.equals(BrowserType.FIREFOXLINUX) ||
                        browser.equals(BrowserType.FIREFOXMAC)
                        ) {
                    writer.println(ExtractJSLogs());

                }
        //REST REQUEST THE PAGE
                // save off the page for later analysis, don't die if this doesn't work though
                try{
                    RestRequest(href);
                }
                catch(Exception ex){
                    System.out.println("WARNING: REST REQUEST FAILED FOR:"+href);
                    writer.println("<span class='warning'>WARNING: REST REQUEST FAILED FOR:"+href+"</span>");
                }

                // if logo is not present, don't assert/fail, just add a verification error,
                // so all links get checked regardless of ones that fail

                // make sure we're on a real page, and not an image
                if (!href.endsWith(".jpg") && !href.endsWith(".gif") && !href.endsWith("rss2")) {
                    // verify logo
                    if (!IsElementPresent(By.xpath(logoxpath), 2000)) {
                        verificationErrors.append("URL:").append(href).append(" MISSING LOGO:").append(logoxpath)
                                .append("\n");

                        // write error to html report
                        fileWriteString = "<br />ISSUE:MISSING LOGO URL:<a href='" + href + "' target='_blank'>" + href
                                + "</a><br />";
                        writer.println(fileWriteString);

                    }

                    // check for 404 (monster-tamer)
                    if (href.contains(monsterTamerDomain) && IsElementPresent(By.xpath(monsterTamer404Xpath), 1000)) {
                        verificationErrors.append("URL:").append(href).append(" 404 PAGE:")
                                .append(monsterTamer404Xpath).append("\n");

                        // write error to html report
                        fileWriteString = "<br />ISSUE:404 URL:<a href='" + href + "' target='_blank'>" + href + "</a><br />";
                        writer.println(fileWriteString);
                    }

                }
                
                //check the desired image count, and break if it's been reached
                if((maxVisits>0) && (++visitCount>maxVisits)){
                    break;
                }

            }

        //COMPLETE WRITING REPORT WEB PAGE
            writer.println(HtmlReportFooter());

            System.out.println("INDEX FILE WRITTEN:" + jenkinsReportPath + fileName);

        } catch (Exception ex) {
            ScreenShot();
            CustomStackTrace("VerifyLogos EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        } finally {
            
            //WRITE THE FILE
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
            
            //check for required parameters
            if(input==null){
                throw new Exception("SEARCH STRING NOT SPECIFIED (-Dinput)");
            }
            
            if(userid==null){
                throw new Exception("USERID NOT SPECIFIED (-Duserid)");
            }
            
            if(password==null){
                throw new Exception("PASSWORD NOT SPECIFIED (-Dpassword)");
            }
            
            System.out.println(GetFirstEmailMessageForSearchTerm("imap.gmail.com", userid, password,
                        "Inbox", input, 30000));
            

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void MakeRestRequest() {
        try {
            // get start url
            String url = new String();
            if (input != null && !input.isEmpty()) {
                url = input;
            } else {
                throw new Exception("START URL NOT SPECIFIED (-Dinput)");
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
                System.out.println("\nLOGO VERIFICATION REPORT\n" + verificationErrors.toString());
            }
        } catch (Exception ex) {
            CustomStackTrace("AFTER EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        }
    }

    /**
     * get js log errors, if any
     * 
     * @return
     */
    private String ExtractJSLogs() {
        StringBuilder logString = new StringBuilder();
        logString.append("<table>").append("<tr><td>DATE</td><td>ERROR LEVEL</td><td>ERROR MESSAGE</td></tr>");
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        String errorLevel = "";
        for (LogEntry entry : logEntries) {
            errorLevel = entry.getLevel().toString();
            logString.append("<tr><td>").append(getDateStamp()).append("</td>");

            // error level color coding
            if (errorLevel.contains("SEVERE")) {
                logString.append("<td class='severe'><b>");
            } else if (errorLevel.contains("WARNING")) {
                logString.append("<td class='warning'><b>");
            } else if (errorLevel.contains("INFO")) {
                logString.append("<td class='info'><b>");
            } else if (errorLevel.contains("FINE")) {
                logString.append("<td class='info'><b>");
            } else {
                logString.append("<td><b>");
            }
            logString.append(errorLevel).append("</b></td>");
            logString.append("<td>").append(entry.getMessage()).append("</td></tr>");
        }
        logString.append("</table>");
        return logString.toString();
    }

}
