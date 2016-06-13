package com.jaemzware.seleniumcodebase;

import static com.jaemzware.seleniumcodebase.CodeBase.GetParameters;
import static com.jaemzware.seleniumcodebase.CodeBase.getDateStamp;
import static com.jaemzware.seleniumcodebase.ParameterType.aNumber;
import static com.jaemzware.seleniumcodebase.ParameterType.aString;
import static com.jaemzware.seleniumcodebase.ParameterType.browser;
import static com.jaemzware.seleniumcodebase.ParameterType.input;
import static com.jaemzware.seleniumcodebase.ParameterType.report;
import static com.jaemzware.seleniumcodebase.ParameterType.userid;
import io.appium.java_client.MobileElement;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author jaemzware.com
 */
public class VerifyLogosAppium extends CodeBase {
    private final String linksOnSplashPageXpath = "//a[@href and not(@href='') and not(contains(@href,'javascript:')) and not(contains(@href,'mailto:'))]";
    @BeforeClass
    public static void BeforeClass(){
        try {
            // get specified command line parameters
            String getParameterResult = GetParameters();
            // check for errors
            if (!getParameterResult.isEmpty()) {
                System.out.println(getParameterResult);
                throw new InvalidParameterException();
            }else{
                // initialize verifification errors
                verificationErrors = new StringBuilder();
            }
        } catch (Exception ex) {
            Assert.fail("INVALID PARAMETERS FOUND:"+ex.getMessage());
        }
    }

    @Test
    public void VerifyLogosAppium() {

        // create a file for the web page log
        String fileName = "verifylogosappium"+getDateStamp()+"-"+report==null?"":report + ".htm";
        PrintWriter writer = null;
        String fileWriteString;

        try {
            // open browser
            StartAppiumDriver();
            
            if(iosDriver==null){
                throw new Exception("APPIUM IOSDRIVER WAS NOT SET; SUITABLE APPIUM IOSDRIVER WAS NOT FOUND.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
            }
            else if(!browser.toString().contains("APPIUM")){
                throw new Exception("APPIUM IOSDRIVER WAS NOT SET; NON-APPIUM BROWSER SPECIFIED.  LOOK ABOVE FOR ISSUES REPORTED BY StartDrvier()");
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
            writer.println(HtmlReportHeader("verifylogos [baseurl:"+baseurl+" starturl:"+starturl+" logoxpath:"+logoxpath+"]"));
            
            // NAVIGATE TO THE STARTING PAGE
            System.out.println("STARTURL:"+starturl);
            fileWriteString = iosDriverGetWithTime(starturl);
           
            // write stats to html report
            writer.println(fileWriteString);

            //LOGGING
            if(System.getProperty("logging")!=null){
                System.out.println("LOGGING DISABLED BECAUSE ITS NOT SUPPORTED FOR APPIUM");
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
                List<WebElement> internalAnchors = iosDriver.findElements(By.xpath(linksOnSplashPageXpath));
                
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
            int maxVisits = aNumber; //check if the max number was specified
            int visitCount = 0;
            
            System.out.println("VISITING HREFS AT XPATH:"+linksOnSplashPageXpath+" ON:"+starturl);
            for (String href : hrefs.keySet()) {
                
                // go to the href
                fileWriteString = iosDriverGetWithTime(href);
                
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
            System.out.println("=================================================");
            

        } catch (Exception ex) {
            ScreenShot();
            System.out.println("VERIFY LOGOS EXCEPTION:"+ex.getMessage());
        } finally {
            
            //WRITE THE FILE
            // write the file if it was created
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
    
    @After
    public void AfterTest() {
        try {
            if (iosDriver != null) {
                System.out.println("QUIT IOSDRIVER");
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
