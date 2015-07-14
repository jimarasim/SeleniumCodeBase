package com.jaemzware.seleniumcodebase;

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
 * @author jaemzware@hotmail.com
 */
public class Scratch extends CodeBase {
    static final String propertiesFile = 
            "src/test/java/com/jaemzware/seleniumcodebase/selenium.properties";
    static Properties properties = new Properties();
    private final String linksOnSplashPageXpath = 
            "//a[@href and not(@href='') and not(contains(@href,'javascript:')) and not(contains(@href,'mailto:'))]";


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
            Assert.fail("INVALID PARAMETERS FOUND");
        } catch (FileNotFoundException fnfex) {
            Assert.fail(propertiesFile + " NOT FOUND");
        } catch (IOException ioex) {
            Assert.fail(propertiesFile + " IO EXCEPTION");
        }
    }

    /**
     * This test just demonstrates what a failed teset looks like when run in jenkins.  there is good information provided, just because of the test framework
     * being used here (junit or testng)
     */
    @Test
    public void DemonstrateJunitFailureInJenkins(){
        Assert.fail("THIS TEST FAILED");
    }
    
    /**
     * This is a proof of concept test for testing real applicaions through appium
     */
    @Test 
    public void IosScratchAppClickButton(){
        try{
            
            StartAppiumDriver();
            
            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; A SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
            
            //this output elements in the app, but only saw it work once
//            List<WebElement> elements = driver.findElements(By.xpath("//*"));
//            for(WebElement web:elements){
//                System.out.println("TAG:"+web.getTagName()+" TEXT:"+web.getText());
//            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @Test
    public void VerifyLogos() {

        String fileName = "index-jaemzware-VerifyLogos-"+getDateStamp()+"-"+report==null?"":report + ".htm";
        PrintWriter writer = null;
        String fileWriteString;

        try {
            // open browser
            StartDriver();
            
            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
            
            if(!browser.toString().contains("APPIUM")){
                //set implicit wait
                driver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
            }

            //VERIFY REQUIRED PARAMETERS WERE SET
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
            writer.println(HtmlReportHeader("VerifyLogos:<a href='"+starturl+"' target='_blank'>"+starturl+"</a> [baseurl:"+baseurl+" starturl:"+starturl+" logoxpath:"+logoxpath+"]"));
            
        // NAVIGATE TO THE STARTING PAGE
            System.out.println("STARTURL:"+starturl);
            fileWriteString = driverGetWithTime(starturl);
           
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
            // get all non-empty/non-javascript href on the page that contain the baseurl
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

                    // only get hrefs that contain the base url, and html like pages
                    if (hrefFound.toLowerCase().contains(baseurl) && 
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
                                .println("SKIPPING: URL:" + hrefFound+" ON:"+starturl + " DOES NOT CONTAIN BASE URL:" + baseurl);
                    }

                }
            } else {
                System.out.println("WARNING: NO LINKS FOUND ON PAGE MATCHING XPATH:" + linksOnSplashPageXpath+" ON:"+starturl);
            }

        //VISIT HREFS
            // visit each href, report load time, and make sure the page has the logo
            int maxVisits = (aNumber!=null||!aNumber.isEmpty())?Integer.parseInt(aNumber):0; //check if the max number was specified
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
            
                // go to the href
                fileWriteString = driverGetWithTime(href);
                
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
            System.out.println("INDEX FILE WRITTEN:" + fileName);
            System.out.println("INTERNAL COPY: http://10.1.10.156/jaemzwareArtifacts/"+ fileName);
            System.out.println("EXTERNAL COPY: http://jaemzware.com/jaemzwareArtifacts/"+fileName);
            
            writer.println(HtmlReportFooter());
        } catch (Exception ex) {
            ScreenShot();
            System.out.println("VERIFY LOGOS EXCEPTION:"+ex.getMessage());
        } finally {
            //WRITE THE FILE IF CREATED
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    @Test
    public void VerifyLogosAppium() {

        // create a file for the web page log
        String fileName = "index-jaemzware-VerifyLogosAppium-"+getDateStamp()+"-"+report==null?"":report + ".htm";
        PrintWriter writer = null;
        String fileWriteString;

        try {
            // open browser
            StartAppiumDriver();
            
            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
            
            if(!browser.toString().contains("APPIUM")){
                //set implicit wait
                driver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
            }

            //VERIFY REQUIRED PARAMETERS WERE SET
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
            
            // NAVIGATE TO THE STARTING PAGE
            System.out.println("STARTURL:"+starturl);
            fileWriteString = driverGetWithTime(starturl);
           
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
            // get all non-empty/non-javascript href on the page that contain the baseurl
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

                    // only get hrefs that contain the base url, and html like pages
                    if (hrefFound.toLowerCase().contains(baseurl) && 
                            !hrefFound.toLowerCase().contains("feed") &&
                            !hrefFound.toLowerCase().contains("rss") &&
                            !hrefFound.toLowerCase().contains("javascript") &&
                            !hrefFound.toLowerCase().contains(".rss2") &&
                            !hrefFound.toLowerCase().contains(".jpg") &&
                            !hrefFound.toLowerCase().contains(".jpeg") &&
                            !hrefFound.toLowerCase().contains(".png") &&
                            !hrefFound.toLowerCase().contains(".gif")) {
                        hrefs.put(hrefFound, starturl);
                        System.out.println("WILL VISIT:" + hrefFound);
                    } else {
                        System.out
                                .println("SKIPPING: URL:" + hrefFound+" ON:"+starturl + " DOES NOT CONTAIN BASE URL:" + baseurl);
                    }

                }
            } else {
                System.out.println("WARNING: NO LINKS FOUND ON PAGE MATCHING XPATH:" + linksOnSplashPageXpath+" ON:"+starturl);
            }

            //VISIT HREFS
            // visit each href, report load time, and make sure the page has the logo
            int maxVisits = (aNumber!=null||!aNumber.isEmpty())?Integer.parseInt(aNumber):0; //check if the max number was specified
            int visitCount = 0;
            
            System.out.println("VISITING HREFS AT XPATH:"+linksOnSplashPageXpath+" ON:"+starturl);
            for (String href : hrefs.keySet()) {
                
                String oldUrl=driver.getCurrentUrl();
            
                // go to the href
                fileWriteString = driverGetWithTime(href);
                
                // write stats to html report
                writer.println(fileWriteString);
                
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

        
            /**
             * COMPLETE WRITING REPORT WEB PAGE
             */
            
            writer.println(HtmlReportFooter());
            
            System.out.println("=================================================");
            System.out.println("INDEX FILE:" + fileName);
            System.out.println("INTERNAL COPY: "+jenkinsReportPathInternal+fileName);
            System.out.println("EXTERNAL COPY: "+jenkinsReportPath+fileName);
            System.out.println("=================================================");
            

        } catch (Exception ex) {
            ScreenShot();
            System.out.println("VERIFY LOGOS EXCEPTION:"+ex.getMessage());
//            CustomStackTrace("VerifyLogos EXCEPTION", ex);
//            Assert.fail(ex.getMessage());
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

            // check for required parameters
            if (input == null) {
                throw new Exception("SEARCH STRING NOT SPECIFIED (-Dinput)");
            }

            if (userid == null) {
                throw new Exception("USERID NOT SPECIFIED (-Duserid)");
            }

            if (password == null) {
                throw new Exception("PASSWORD NOT SPECIFIED (-Dpassword)");
            }

            System.out.println(GetFirstEmailMessageForSearchTerm("imap.gmail.com", userid, password, "Inbox", input,
                    30000));

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
//            CustomStackTrace("RestRequest EXCEPTION", ex);
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
     * This method verifies logoxpath is on the currentpage
     * 
     * @param xpathToVerify
     */
    private String VerifyXpathOnCurrentPage(String xpathToVerify) {

        StringBuilder outputString = new StringBuilder();

        String href = driver.getCurrentUrl();

        // if logo is not present, don't assert/fail, just add a verification error,
        // so all links get checked regardless of ones that fail
        if (!IsElementPresent(By.xpath(xpathToVerify), 2000)) {
            verificationErrors.append("URL:").append(href).append(" MISSING LOGO:").append(xpathToVerify).append("\n");

            // write error to html report
            outputString.append("<br /><p class='severe'>PAGE MISSING LOGO XPATH:");
            outputString.append(xpathToVerify);
            outputString.append(" URL :</p><a href='");
            outputString.append(href);
            outputString.append("' target='_blank'>");
            outputString.append(href);
            outputString.append("</a><br />");

        } else {
            outputString.append("<table><th>XPATH MATCHES FOR:").append(xpathToVerify).append("</th>");
            String tagString;
            String imageSrc;

            List<WebElement> xpathElementMatches = driver.findElements(By.xpath(xpathToVerify));
            // List<WebElement> xpathElementMatches = driver.findElements(By.xpath("name("+xpathToVerify+")"));

            for (WebElement we : xpathElementMatches) {
                try {
                    tagString = we.getTagName();
                } catch (Exception ex) {
                    System.out.println("WARNING: EXCEPTION GETTING TAG STRING SRC FROM XPATH ELEMENT."
                            + ex.getMessage());
                    outputString
                            .append("<span class='warning'>WARNING: EXCEPTION GETTING TAG STRING FROM XPATH ELEMENT:")
                            .append(ex.getMessage()).append("</span>");
                    break;
                }

                outputString.append("<tr>");
                outputString.append("<td>");
                if (tagString.toLowerCase().equals("img")) {

                    try {
                        imageSrc = we.getAttribute("src");
                    } catch (Exception ex) {
                        System.out
                                .println("WARNING: EXCEPTION GETTING IMAGE SRC FROM XPATH ELEMENT." + ex.getMessage());
                        outputString
                                .append("<span class='warning'>WARNING: EXCEPTION GETTING IMAGE SRC FROM XPATH ELEMENT:")
                                .append(ex.getMessage()).append("</span>");
                        break;
                    }

                    if (imageSrc != null && !imageSrc.isEmpty()) {
                        outputString.append("<img src='").append(imageSrc).append("' />");
                    } else {
                        outputString.append("<p class='warning'>WARNING: IMAGE SRC IS EMPTY</p>");
                    }
                } else {
                    try {
                        outputString.append(we.getText());
                    } catch (Exception ex) {
                        System.out.println("WARNING: EXCEPTION GETTING TEXT FROM XPATH ELEMENT:" + ex.getMessage());
                        outputString
                                .append("<span class='warning'>WARNING: EXCEPTION GETTING TEXT FROM XPATH ELEMENT:")
                                .append(ex.getMessage()).append("</span>");
                        break;
                    }
                }

                outputString.append("</td></tr>");
            }
            outputString.append("</table>");

        }

        return outputString.toString();
    }

    // ERROR LOGGING - TAKES LONG - ADD CAPABILITY WHEN CREATING driver BEFORE USING
    private String ExtractJSLogs() {
        StringBuilder logString = new StringBuilder();
        logString.append("<table>");

        LogEntries browserLog = driver.manage().logs().get(LogType.BROWSER);
        if (browserLog.getAll().size() > 0) {
            logString.append("<tr><td colspan=2><h3>BROWSER</h3></td></tr>");
            logString.append("<tr><td>LEVEL</td><td>MESSAGE</td></tr>");
            logString.append(WriteLogEntryRows(browserLog));
        } else {
            logString.append("<tr><td colspan=2>No BROWSER log entries found.</td></tr>");
        }

        LogEntries clientLog = driver.manage().logs().get(LogType.CLIENT);
        if (clientLog.getAll().size() > 0) {
            logString.append("<tr><td colspan=2><h3>CLIENT</h3></td></tr>");
            logString.append("<tr><td>LEVEL</td><td>MESSAGE</td></tr>");
            logString.append(WriteLogEntryRows(clientLog));
        } else {
            logString.append("<tr><td colspan=2>No CLIENT log entries found.</td></tr>");
        }
        //TODO: TRY THIS AGAIN
        // LogEntries driverLog = driver.manage().logs().get(LogType.DRIVER);
        // if(driverLog.getAll().size()>0){
        // logString.append("<tr><td colspan=2><h3>DRIVER</h3></td></tr>");
        // logString.append("<tr><td>LEVEL</td><td>MESSAGE</td></tr>");
        // logString.append(WriteLogEntryRows(driverLog));
        // }
        // else{
        // logString.append("<tr><td colspan=2>No DRIVER log entries found.</td></tr>");
        // }
        //

        logString.append("</table>");
        return logString.toString();
    }

    // ERROR LOGGING - TAKES LONG - ADD CAPABILITY WHEN CREATING driver BEFORE USING
    private String WriteLogEntryRows(LogEntries entries) {
        StringBuilder logEntryRows = new StringBuilder();

        String errorLevel;
        for (LogEntry entry : entries) {
            errorLevel = entry.getLevel().toString();
            logEntryRows.append("<tr>");

            // error level color coding
            if (errorLevel.contains("SEVERE")) {
                logEntryRows.append("<td class='severe'><b>");
            } else if (errorLevel.contains("WARNING")) {
                logEntryRows.append("<td class='warning'><b>");
            } else if (errorLevel.contains("INFO")) {
                logEntryRows.append("<td class='info'><b>");
            } else if (errorLevel.contains("FINE")) {
                logEntryRows.append("<td class='info'><b>");
            } else {
                logEntryRows.append("<td><b>");
            }
            logEntryRows.append(errorLevel).append("</b></td>");
            logEntryRows.append("<td>").append(entry.getMessage()).append("</td></tr>");
        }

        return logEntryRows.toString();
    }


}
