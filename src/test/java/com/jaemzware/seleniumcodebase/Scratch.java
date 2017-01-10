package com.jaemzware.seleniumcodebase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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

import static com.jaemzware.seleniumcodebase.ParameterType.*;

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

            // get the command line parameters that were specified
            String getParameterResult = GetParameters();
            // an error string will be returned if something went wrong
            if (!getParameterResult.isEmpty()) {
                System.out.println(getParameterResult);
                throw new InvalidParameterException();
            }else{
                // initialize verifification errors
                verificationErrors = new StringBuilder();
            }
        } catch (InvalidParameterException ipex) {
            Assert.fail("INVALID PARAMETERS FOUND:"+ipex.getMessage());
        } catch (FileNotFoundException fnfex) {
            Assert.fail(propertiesFile + " NOT FOUND"+fnfex.getMessage());
        } catch (IOException ioex) {
            Assert.fail(propertiesFile + " IO EXCEPTION"+ioex.getMessage());
        }
    }
    
    @Test
    public void VerifyLogos() {

        String fileName = "index"+report+".htm";
        PrintWriter writer = null;
        String fileWriteString;

        try {
            // open browser
            StartDriver();
            
            //check if driver setting was successful
            if(driver==null){
                throw new Exception("DRIVER WAS NOT SET; SUITABLE DRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
            
            //set defaultImplicitWaitSeconds IF NOT safari
            if(!browser.toString().contains("SAFARI") && !browser.toString().contains("APPIUM")){
                driver.manage().timeouts().implicitlyWait(defaultImplicitWaitSeconds, TimeUnit.SECONDS);
            }

            // get START url. first page to load
            String starturl = new String();
            if (input != null && !input.isEmpty()) {
                starturl = input;
            } else {
                throw new Exception("START URL NOT SPECIFIED (-Dinput)");
            }

            // get base url. url string that is contained by all urls to follow
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
            
            if (report == null || report.isEmpty()) {
                throw new Exception("REPORT NOT SPECIFIED -Dreport");
            }
            
            //CREATE A REPORT WEB PAGE
            writer = new PrintWriter(fileName, "UTF-8");

            // write the html header in the web page
            writer.println(HtmlReportHeader("VerifyLogos:<a href='"+starturl+"' target='_blank'>"+starturl+"</a><br />browser:"+browser.toString()+"<br />baseurl:"+baseurl+" <br />starturl:"+starturl+" <br />logoxpath:"+logoxpath));
            
            // NAVIGATE TO THE STARTING PAGE TO GET LINKS TO VISIT FROM
            System.out.println("STARTURL:"+starturl);
            fileWriteString = driverGetWithTime(starturl);
            if(fileWriteString.equals("ERROR")){
                throw new Exception("Scratch VerifyLogos DRIVERGETWITHTIME ERROR OCCURRED. LOOK ABOVE FOR EXCEPTION MESSAGE.");
            }

            //scroll the page (this can be overridden with -DnoScroll
            ScrollPage();
           
            // WRITE PAGE URL GET META INFORMATION (E.G. LENGTH OF LOAD TIME)
            if(fileWriteString.equals("ERROR")) {
                writer.println("THERE WAS AN ERROR LOADING "+starturl);
            }
            else{
                writer.println(fileWriteString);
            }

            //LOGGING
            if(System.getProperty("logging")==null){
                System.out.println("LOGGING DISABLED - USE -Dlogging TO SEE BROWSER ERRORS AND WARNINGS");
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
                            !hrefFound.toLowerCase().contains("javascript")) {
                        hrefs.put(hrefFound, starturl);
                        System.out.println("WILL VISIT:" + hrefFound);
                    } else {
                        System.out
                                .println("SKIPPING: URL:" + hrefFound+" ON:"+starturl + " IS NOT A WEB PAGE OR DOES NOT CONTAIN BASE URL:" + baseurl);
                    }

                }
            } else {
                System.out.println("WARNING: NO LINKS FOUND ON PAGE MATCHING XPATH:" + linksOnSplashPageXpath+" ON:"+starturl);
            }

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
                
                // go to the href
                fileWriteString = driverGetWithTime(href);
                if(fileWriteString.equals("ERROR")){
                    throw new Exception("Scratch VerifyLogos DRIVERGETWITHTIME ERROR OCCURRED. LOOK ABOVE FOR EXCEPTION MESSAGE.");
                }
                
                // write stats to html report
                writer.println(fileWriteString);

                //scroll the page (this can be overridden with -DnoScroll
                ScrollPage();
                
        //ERROR LOGGING - TAKES LONG - ADD CAPABILITY WHEN CREATING driver BEFORE USING
                if(System.getProperty("logging")==null){
                    System.out.println("USE -Dlogging FOR BROWSER ERROR LOGS ENCOUNTERED");
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
            System.out.println("open " + fileName);
            
            writer.println(HtmlReportFooter());
        } catch (Exception ex) {
            ScreenShot();
            System.out.println("VERIFY LOGOS EXCEPTION:"+ex.getMessage());
            ex.printStackTrace();
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
                System.out.println("QUIT DRIVER");
                QuitDriver();
            }

            // check if there were any verify errors, and fail whole test if so
            if (verificationErrors.length() > 0) {
                System.out.println("\nVERIFICATIONERRORS " + verificationErrors.toString());
            }
        } catch (Exception ex) {
            CustomStackTrace("AFTER EXCEPTION", ex);
            Assert.fail(ex.getMessage());
        }
    }
}
