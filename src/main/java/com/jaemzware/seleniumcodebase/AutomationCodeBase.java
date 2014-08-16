package com.jaemzware.seleniumcodebase;

import java.io.BufferedReader;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * AutomationCodeBase
 * @author jaemzware@hotmail.com
 *
 */
public class AutomationCodeBase 
{
    //the one and only driver object
    protected static WebDriver driver=null;
 
    //selenium grid hub
    protected static final String seleniumGridHub = "localhost";
    
    //appium hub
    private static final String appiumHub = "localhost:4723";
    
    //recognized command line variables 
    protected static String userid = null; //for tests that need to authenticate
    protected static String password = null; //for tests that need to authenticate
    protected static String input = null; //for specifying input files (ReadTermResultFromInputXls) or sql statements (Sql.java)
    protected static String aNumber = null; //for specifying a generic number for usage or comparison (Sql.java), will fail if not integer parseable
    protected static String aString = null; //for specifiying a generic string for usage or comparison (Sql.java)
    protected static EnvironmentType environment = EnvironmentType.test;    
    protected static BrowserType browser=BrowserType.CHROME;    
    
    //default time IN SECONDS to wait when finding elements
    protected int defaultImplicitWait = 30;	
    
    //verification errors that can occur during a test
    protected StringBuilder verificationErrors = new StringBuilder();
    
    //save off main window handle, for when dealing with popups
    protected static String mainWindowHandle; 

    
     /**
     * This function gets the command line parameters.  Will be called by StartDriver to be backwards compatible.  
     * Separated out so that tests  can get the parameters without having to start the driver
     * @return boolean indicating whether GetParameters() succeeded in obtaining valid values.  Currently
     * false will be returned if -DaNumber cannot be parsed as an integer, -Denvironment is not supported,
     * or -Dbrowser is not supported
     */
    protected static boolean GetParameters()
    {
        System.out.println("COMMAND LINE PARAMETERS");
        
        //get userid specified on command line
        String useridParm = System.getProperty("userid");
        if(useridParm==null||useridParm.isEmpty())
        {
            System.out.println("-Duserid NOT SPECIFIED.");
        }
        else
        {
            userid = useridParm;
            
            System.out.println("-Duserid:"+userid);
        }
        
        //get password specified on command line
        String passwordParm = System.getProperty("password");
        if(passwordParm==null||passwordParm.isEmpty())
        {
            System.out.println("-Dpassword NOT SPECIFIED.");
        }
        else
        {
            password = passwordParm;
            
            System.out.println("-Dpassword:********");
        }
        
        //get password specified on command line
        String inputParm = System.getProperty("input");
        if(inputParm==null||inputParm.isEmpty())
        {
            System.out.println("-Dinput NOT SPECIFIED.");
        }
        else
        {
            input = inputParm;
            
            System.out.println("-Dinput:"+input);
        }
        
        
        //get aNumber specified on command line
        String aNumberParm = System.getProperty("aNumber");
        if(aNumberParm==null||aNumberParm.isEmpty())
        {
            System.out.println("-DaNumber NOT SPECIFIED.");
        }
        else
        {
            //make sure aNumber is parseable
            try
            {
                Integer.parseInt(aNumberParm);
                
                aNumber = aNumberParm;
            
                System.out.println("-DaNumber:"+aNumber);
            }
            catch(NumberFormatException nfx)
            {
                System.out.println("-DaNumber:"+aNumberParm+" SPECIFIED IS NOT A PARSEABLE INT. RETURNING FALSE (MAY BE IGNORED BY SOME TESTS)");
                return false;
            }
             
        }
        
        //get aString specified on command line
        String aStringParm = System.getProperty("aString");
        if(aStringParm==null||aStringParm.isEmpty())
        {
            System.out.println("-DaString NOT SPECIFIED.");
        }
        else
        {
            aString = aStringParm;
            
            System.out.println("-DaString:"+aString);
        }
        
        //get browser type specified on command line
        String browserParm = System.getProperty("browser");

        if(browserParm==null||browserParm.isEmpty())
        {
            //if browser is not specified, output which one is being used by default
            System.out.println("-Dbrowser NOT SPECIFIED. USING DEFAULT:"+browser);
        }
        else
        {
            try
            {
                browser = BrowserType.valueOf(browserParm);
                
                System.out.println("-Dbrowser:"+browser);
            }
            catch(IllegalArgumentException ex)
            {                
                System.out.println("AN INVALID -Dbrowser ("+browserParm+") WAS SPECIFIED. WILL USE DEFAULT IF THIS IS IGNORED:"+browser + " VALID: CHROME, FIREFOX, SAFARI, IE8, IE9, IE10, IE11 APPIUM (NON-GRID ONLY:IPHONE6,IPAD4,ANDROID402)");
                return false;
            }            
        }
        
        //get environment type specified on command line
        String environmentParm = System.getProperty("environment");

        if(environmentParm==null)
        {
            //if environment is not specified, output which one is being used by default
            System.out.println("-Denvironment NOT SPECIFIED. USING DEFAULT:"+environment);
        }
        else
        {
            try
            {
                environment = EnvironmentType.valueOf(environmentParm);
                
                System.out.println("-Denvironment:"+environment);
            }
            catch(IllegalArgumentException ex)
            {
                System.out.println("AN INVALID -Denvironment ("+environmentParm+") WAS SPECIFIED. WILL USE DEFAULT IF THIS IS IGNORED:"+environment+" VALID: dev, test, stage, prod (WebProperties) PRD,STG,TST,DV1,DV2,DV3,DV4,DV5,DV6,DV7 (OracleEBS)");
                return false;
            }            
        }
        
        return true;
    }
    
    /**
     * This function starts the desired web browser.
     * @throws Exception 
     */
    protected static void StartDriver() throws Exception
    {
        
        StartDriver("../");
    }
    /**
     * This function starts the desired web browser.
     * @throws Exception 
     */
    /**
     * This function starts the desired web browser
     * @param relativePathToDrivers - this allows the calling test to specify the relative path to chromedriver and/or iedriverserver in the project 
     * directory, as that's where they will be in each instance.  This was done because an original 
     * test was moved to a subfolder folder, thus giving it a relative path of ../../ instead of just ../, like all the other projects.
     * NOTE: Chromedriver and iedriverserver are not checked in, because there are different versions for different platforms, 
     * and they're named 
     * the same thing: e.g. iedriverserver.exe (32bit) and iedriverserver.exe (64bit)
     * @assumptions it is the responsibility of the extended test scrip to call GetParameters() before calling StartDriver()
     * @throws Exception 
     */
    protected static void StartDriver(String relativePathToDrivers) throws Exception
    {
        
        //LAUNCH APPIUM BROWSER, IF AVAILABLE
        //check if the request is for appium
        if(driver==null && browser==BrowserType.APPIUM)
        {
            try
            {
                //set desired capabilites for running safari on iphone simulator through appium
                DesiredCapabilities cap = new DesiredCapabilities();
                cap.setCapability("device", "iPhone Simulator");
                //cap.setCapability("device", "iPad Simulator");
                cap.setCapability("app", "safari");                
                driver = new RemoteWebDriver(new URL("http://"+appiumHub+"/wd/hub"), cap);
                
                //augment the driver so that screenshots can be taken
                driver = new Augmenter().augment(driver);
                
                System.out.println("SUCCESSFULLY LAUNCHED APPIUM");
            }
            catch(MalformedURLException ex)
            {
                throw new Exception("COULD NOT LAUNCH APPIUM:"+ex.getMessage());
            }
        }
                
        //LAUNCH GRID BROWSER
        //try to get the browser on selenium grid
        //don't do this if -Dnogrid is specified though
        if(driver==null && System.getProperty("nogrid")==null)
        {
            try 
            {

                //get the desired capabilities
                DesiredCapabilities cap;

                //desired browser
                switch(browser)
                {
                    case CHROME:
                    case CHROMELINUX:
                        cap = DesiredCapabilities.chrome();
                        break;
                    case FIREFOX:
                        cap = DesiredCapabilities.firefox();
                        break;
                    case SAFARI:
                        cap = DesiredCapabilities.safari();
                        break;
                    case IE8:
                    case IE9:
                    case IE10:
                    case IE11:
                        cap = DesiredCapabilities.internetExplorer();
                        break;
                    default:
                        throw new Exception("UNSUPPORTED -Dbrowser:"+browser+" VALID BROWSERS (GRID):FIREFOX,CHROME,SAFARI,IE8,IE9,IE10,IE11");
                }

                //accept all ssl certificates by default
                cap.setCapability("acceptSslCerts", true);
                
                //set desired platform (as specified by grid node)
                cap.setPlatform(browser.platform);
                
                //desired browser name (as set by grid node)
                cap.setBrowserName(browser.browserName);
                
                //desired version (as set by grid node)
                cap.setVersion(browser.version);
                
                System.out.println("ATTEMPTING TO LAUNCH SELENIUM GRID NODE FOR:"+browser.browserName+" VERSION:"+browser.version+" PLATFORM:"+browser.platform.toString());
                System.out.println("USE -Dnogrid TO SKIP AND LAUNCH NATIVE (NON-GRID)");

                //get the grid node
                driver = new RemoteWebDriver(new URL("http://"+seleniumGridHub+":4444/wd/hub"), cap);
                
                //augment the driver so that screenshots can be taken
                driver = new Augmenter().augment(driver);
                
                System.out.println("SUCCESSFULLY LAUNCHED SELENIUM GRID NODE FOR:"+browser.browserName+" VERSION:"+browser.version+" PLATFORM:"+browser.platform.toString());
            } 
            catch(Exception ex)
            {
                if(ex.getMessage().contains("Error forwarding"))
                {
                    System.out.println("SELENIUM GRID NODE 'browserName="+browser.browserName+",version="+browser.version+"' NOT LAUNCHED EXCEPTION:"+ex.getMessage());
                }
                else if(ex.getMessage().contains("Could not start a new session"))
                {
                    System.out.println("SELENIUM GRID HUB NOT LAUNCHED EXCEPTION:"+ex.getMessage());
                }
                else
                {
                    System.out.println("SELENIUM GRID CONNECTION EXCEPTION:"+ex.getMessage());
                }

                driver = null;
            }
        }
        
        //LAUNCH LOCAL BROWSER: if grid didn't work out, start the desired browser on the native os
        if(driver==null)
        {
            System.out.println("ATTEMPTING TO LAUNCH NON-GRID BROWSER FOR:"+browser+" ON:"+GetOsType());
            
            switch (browser)
            {
                case CHROMELINUX:
                case CHROME:
                case IPHONE6:
                case IPAD4:
                case ANDROID402:

                    //chrome uses a different driver binary depending on what os we're on
                    switch(GetOsType())
                    {                    
                        case WINDOWS:
                            System.setProperty("webdriver.chrome.driver", relativePathToDrivers+"chromedriver.exe"); //FOR WINDOWS
                            break;
                        case MAC:
                            System.setProperty("webdriver.chrome.driver", relativePathToDrivers+"chromedriver"); //FOR MAC
                            break;
                        case UNIX:
                            System.setProperty("webdriver.chrome.driver", relativePathToDrivers+"chromedriver"); //FOR unix
                            break;
                        default:
                            throw new Exception("-Dbrowser="+browser+" IS UNSUPPORTED NATIVELY ON THIS OS:"+GetOsType());
                    }
                    
                    //USE CHROME OPTIONS TO SET THE USER AGENT IF REQUESTED (e.g. IPHONE6)
                    if(browser.equals(BrowserType.IPHONE6))
                    {
                        ChromeOptions options = new ChromeOptions();
                    
                        //iphone ios6
                        options.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25");
                    
                        driver = new ChromeDriver(options);
                    }
                    else if(browser.equals(BrowserType.IPAD4))
                    {
                        ChromeOptions options = new ChromeOptions();
                        
                        //ipad ios4
                        options.addArguments("--user-agent=Mozilla/5.0 (iPad; CPU OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5");
                    
                        driver = new ChromeDriver(options);
                    }
                    else if(browser.equals(BrowserType.ANDROID402))
                    {
                        ChromeOptions options = new ChromeOptions();
                        
                        //android 4.0.2
                        options.addArguments("--user-agent=Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
                    
                        driver = new ChromeDriver(options);
                    }
                    else
                    {
                        //get the chrome driver/start regular chrome
                        driver = new ChromeDriver();
                    }

                    break;
                case FIREFOX:

                    //create a firefox profile for dealing with untrusted certificates
                    FirefoxProfile fp = new FirefoxProfile();

                    fp.setAcceptUntrustedCertificates(true);
                    fp.setAssumeUntrustedCertificateIssuer(false);

                    //get the firefox driver/start firefox
                    driver = new FirefoxDriver(fp);

                    break;  
                case SAFARI:

                    if(GetOsType().equals(OsType.MAC))
                    {
                        //get the safari driver/start safari
                        driver = new SafariDriver();
                    }                    
                    else
                    {       
                        throw new Exception("SAFARI IS UNSUPPORTED NATIVELY ON THIS OS:"+GetOsType());             
                    }

                    break;
                case IE8:
                case IE9:
                case IE10:
                case IE11:
                    if(GetOsType().equals(OsType.WINDOWS))
                    {
                        //if we're on windows, just look for the windows driver regardless of version
                        System.setProperty("webdriver.ie.driver",relativePathToDrivers+"IEDriverServer.exe");

                        //get the ie driver/start ie
                        driver = new InternetExplorerDriver();
                    }                    
                    else
                    {       
                        throw new Exception("IE8, IE9, IE10, IE11 IS UNSUPPORTED NATIVELY ON THIS OS:"+GetOsType());             
                    }
                    break;
                default:
                    throw new Exception("UNSUPPORTED -Dbrowser:"+browser+" VALID BROWSERS (NOGRID):FIREFOX,CHROME,SAFARI,IE8,IE9,IE10,IE11");
            }
            
            System.out.println("SUCCESSFULLY LAUNCHED NATIVE BROWSER FOR:"+browser+" ON:"+GetOsType());
        }
        
        //start with no cookies
        
        //safari 7.0.1 doesn't like this for some reason
        if(browser!=BrowserType.SAFARI)
        {
            driver.manage().deleteAllCookies();
        }
        
        //maximize browser (not supported by appium)
        if(browser!=BrowserType.APPIUM)
        {
            //maximize the window
            driver.manage().window().maximize();
            
            //maximize the window back up approach - TESTING
//            driver.manage().window().setPosition(new Point(0,0));
//            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//            org.openqa.selenium.Dimension maxWindowSize = new org.openqa.selenium.Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight());
//            driver.manage().window().setSize(maxWindowSize);
            
            
            //full screen window, if this is not mac, try to get full screen mode (WORKS ON WINDOWS AND LINUX).  
            //verified with firefox and ie, but not chrome
//            if(browser.platform!=Platform.MAC)
//            {
//                driver.get("http://seleniumhq.org");
//                driver.findElement(By.tagName("body")).sendKeys(Keys.F11);
//            }
        }
        
        //set the main window handle
        mainWindowHandle = driver.getWindowHandle();
        
    }
    
    protected static void QuitDriver()
    {
        driver.quit(); 
        driver = null;
    }
    
    private static OsType GetOsType() throws Exception
    {
        //get the os
        String os = System.getProperty("os.name").toLowerCase();

        //set the system property for chromedriver depending on the os
        if(os.contains("win"))
        {
            return OsType.WINDOWS;
        }
        else if(os.contains("mac"))
        {
            return OsType.MAC;
        }
        else if(os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0 )
        {
            return OsType.UNIX;
        }
        else
        {
            throw new Exception("UNSUPPORTED OPERATING SYSTEM:"+os);
        }

    }
    
    /**
     * IsElementPresent stub with default waitTime of 10 seconds (when no wait time specified)
     * @param locatorKey
     * @return 
     */
    protected boolean IsElementPresent(By locatorKey)
    {
	return IsElementPresent(locatorKey,10000);	
    }
    
    /**
     * IsElementPresent method, that allows one to specify how long to try finding the element
     * @param locatorKey
     * @param waitTime
     * @return 
     */
    protected boolean IsElementPresent(By locatorKey,int waitTimeMillis)
    {
	try 
        {
            //throttle wait time when looking for elements that should already be on the page
            driver.manage().timeouts().implicitlyWait(waitTimeMillis, TimeUnit.MILLISECONDS);
			
            //look for elements
            if(driver.findElements(locatorKey).size()>0)
            {
                return true;
            }
            else
            {
                return false;
            }
        } 
        catch (Exception ex) 
        {
            return false;
        }
        finally
        {
            //throttle implicit wait time back up
            driver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
        }
	
    }
       
    /**
     * This method takes a screenshot, and puts it in the current working directory
     * Made static so screenshot can be taken from StartDriver
     * @throws Exception 
     */
    protected static void ScreenShot()
    {
        String fileName="";
        
        try
        {
            //take the screen shot
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            //get path to the working directory
            String workingDir = System.getProperty("user.dir");

            //generate a unique file name
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = new Date();
            String dateStamp = dateFormat.format(date);
            //String fileName = GetOsType().equals(OsType.WINDOWS)?workingDir.replace("\\","\\\\")+ "\\screenshot"+dateStamp+".png":workingDir + "/screenshot"+dateStamp+".png";
            fileName = GetOsType().equals(OsType.WINDOWS)?workingDir+ "\\screenshot"+dateStamp+".png":workingDir + "/screenshot"+dateStamp+".png";

            //save the file        
            FileUtils.copyFile(scrFile, new File(fileName));
        }
        catch(Exception ex)
        {
            System.out.println("COMMON.SCREENSHOT FAILED:"+ex.getMessage());
        }
            
            


        
        System.out.println("SCREENSHOT:"+fileName);
    }
    
    /**
 * This method is used to print custom, stack traces that will show a custom message, and can be formatted
 * Unlike Exception printStackTrace(), it will not throw an exception.
 * @param customMessage
 * @param ex 
 */
    protected void CustomStackTrace(String customMessage, Exception ex)
    {
        //write out stack trace to info
        StackTraceElement[] stack = ex.getStackTrace();

        System.out.println("MESSAGE:"+customMessage+" STACK TRACE:");
        for(StackTraceElement line:stack)
        {
            System.out.println("FILE:"+line.getFileName()+" METHOD:"+line.getMethodName()+" LINE:"+line.getLineNumber());				
        }
    }
    
    /**
     * This method makes an http get request to the provided url, and returns the reponse as a String
     * @param url
     * @return 
     */
    protected String HttpGetReturnResponse(String url) throws Exception
    {
        //HTTPCLIENT/REST EXAMPLES
            //http://www.mkyong.com/java/apache-httpclient-examples/
            //http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-apache-httpclient/
            
        //make the request
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

//            System.out.println("Response Code : " 
//                    + response.getStatusLine().getStatusCode());

        //read the reponse
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
                result.append(line);
        }

        //System.out.println(result);
        
        return result.toString();
    }
  
    
   
}
