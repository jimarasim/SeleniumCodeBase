package com.jaemzware.seleniumcodebase;

import static com.jaemzware.seleniumcodebase.AutomationCodeBase.QuitDriver;
import static com.jaemzware.seleniumcodebase.AutomationCodeBase.driver;
import static com.jaemzware.seleniumcodebase.AutomationCodeBase.environment;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 *@author jaemzware@hotmail.com
 * 
 */
public class Scratch extends AutomationCodeBase
{
    static Properties properties = new Properties();
    StringBuilder verificationErrors;
    
    //monster tamer
    private final String monsterTamerDomain = "monster-tamer.com";
    private final String monsterTamer404Xpath = "//h1[contains(text(),'404 Page')]";
    
    @Before
    public void BeforeTest()
    {
        try
        {  
            //properties file is in same directory as pom.xml
            properties.load(new FileInputStream("src/test/java/com/jaemzware/seleniumcodebase/selenium.properties"));
            
            //initialize verifification errors
            verificationErrors = new StringBuilder();
            
            //get input parameters HERE
            GetParameters();
        }
        catch(Exception ex)
        {
            CustomStackTrace("BEFORE TESTS EXCEPTION",ex);
            Assert.fail("BEFORE TESTS EXCEPTION:"+ex.getMessage());
        }
    }
    
    @Test
    public void VerifyLogos()
    {
        
        //build web page
        String fileName = "index-VerifyLogos-"+getDateStamp()+".htm";
        PrintWriter writer=null;
        String fileWriteString = "";
        
        try
        {
            writer = new PrintWriter(fileName, "UTF-8");
            
            writer.println("<html><head><title>boardscrub</title></head><body><h1>boardscrub</h1>");
            
            //open browser
            StartDriver();
            
            //get base url
            String url = new String();
            if(input!=null){
                url=input;
            }
            else{
                url= properties.getProperty(environment.toString()+".url");
            }
            
            if(url==null){
                throw new Exception("URL NOT SPECIFIED NOR FOUND IN PROPERTIES FILE");
            }
            
            //get xpath to look for
            String logoxpath=new String();
            if(aString != null){
                logoxpath=aString;
            }
            else{
                logoxpath = properties.getProperty(environment.toString()+".logoxpath");
            }
            
            if(logoxpath==null){
                throw new Exception("LOGOXPATH NOT SPECIFIED NOR FOUND IN PROPERTIES FILE");
            }
                
            //navigate to the starting page
            fileWriteString = driverGetWithTime(url);
            
            //write stats to html report
            writer.println(fileWriteString);
           
            //get all non-empty/non-javascript href on the page
            Map<String,String> hrefs = new HashMap<String,String>();
            String hrefFound;
            List<WebElement> internalAnchors = driver.findElements(By.xpath("//a[@href and not(@href='') and not(contains(@href,'javascript:'))]"));
            int exceptionCount=0;
            for(WebElement we:internalAnchors){
                //TODO FIND OUT WHY THIS THROWS AN EXCEPTION SOMETIMES
                try{System.out.println(we.getAttribute("href"));}catch(Exception ex){System.out.println("EXCEPTION GETTING HREF FROM LIST. COUNT:"+exceptionCount++);continue;}
                hrefFound=we.getAttribute("href");
                
                if(hrefFound.contains(url.substring(url.indexOf("/",0)))){
                    hrefs.put(hrefFound,url);
                }
            }
            
            //visit each href, report load time, and make sure the page has the logo
            for(String href:hrefs.keySet()){
                
                driverGetWithTime(href);
                
                //if logo is not present, don't assert/fail, just add a verification error,
                //so all links get checked regardless of ones that fail
                
                //make sure we're on a real page, and not an image
                if(!href.endsWith(".jpg") && !href.endsWith(".gif"))
                {
                    //verify logo
                    if(!IsElementPresent(By.xpath(logoxpath),1000)){
                        verificationErrors.append("URL:")
                                .append(href)
                                .append(" MISSING LOGO:")
                                .append(logoxpath)
                                .append("\n");
                        
                        //write error to html report
                        fileWriteString = "<br />ISSUE:MISSING LOGO URL:<a href='"+href+"' target='_blank'>"+href+"</a>";
                        writer.println(fileWriteString);
                        
                    }
                    
                    //check for 404 (monster-tamer)
                    if(href.contains(monsterTamerDomain) && IsElementPresent(By.xpath(monsterTamer404Xpath),1000)){
                        verificationErrors.append("URL:")
                                .append(href)
                                .append(" 404 PAGE:")
                                .append(monsterTamer404Xpath)
                                .append("\n");
                        
                        //write error to html report
                        fileWriteString = "<br />ISSUE:404 URL:<a href='"+href+"' target='_blank'>"+href+"</a>";
                        writer.println(fileWriteString);
                    }
                    
                }
                 
            }
            
                        
            writer.println("</body></html>");
            
            System.out.println("INDEX FILE WRITTEN:"+fileName);
            
        }
        catch(Exception ex)
        {
            ScreenShot();
            CustomStackTrace("VerifyLogos EXCEPTION",ex);
            Assert.fail(ex.getMessage());
        }
        finally
        {
            //write the file if it was created
            if(writer!=null){
                writer.flush();
                writer.close();
            }
        }
    }
    
    @Test
    public void CheckGmail(){
        try{
            System.out.println(GetFirstEmailMessageForSearchTerm("imap.gmail.com", 
                    "jaemzware", 
                    "8googlegoogle", 
                    "Inbox",
                    "Metabliss", 
                    30000));
            
        }
        catch(Exception ex){
            System.out.println("COULD NOT GET EMAIL:"+ex.getMessage());
        }
    }
            
    
    @Test
    public void RestRequest()
    {
        try{
            final String url = properties.getProperty(environment.toString()+".url");
            
            String response = HttpGetReturnResponse(url);
            
            //recreate web page
            String fileName = "index-RestRequest-"+getDateStamp()+".htm";
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.println(response);
            writer.flush();
            writer.close();
            System.out.println("WROTE RESPONSE TO:"+fileName);
        }
        catch(Exception ex){
            CustomStackTrace("RestRequest EXCEPTION",ex);
            Assert.fail(ex.getMessage());
        }
    }
    
    @After
    public void AfterTest()
    {
        try
        {
            if(driver!=null){
                QuitDriver();
            }
            
            //check if there were any verify errors, and fail whole test if so
            if(verificationErrors.length()>0)
            {
                Assert.fail("\nVERIFICATION ERRORS\n"+verificationErrors.toString());
            }
        }
        catch(Exception ex)
        {
            CustomStackTrace("AFTER EXCEPTION",ex);
            Assert.fail(ex.getMessage());
        }
    }
}
