package com.jaemzware.seleniumcodebase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.BodyTerm;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * CodeBase
 * 
 * @author jaemzware@hotmail.com
 */
public class CodeBase {
    
    // the one and only driver object
    protected static WebDriver driver = null;

     // recognized command line variables
    protected static EnvironmentType environment = EnvironmentType.craigslist;
    protected static BrowserType browser = null;
    protected static String userid = null; // for tests that need to authenticate
    protected static String password = null; // for tests that need to authenticate
    protected static String input = null; // for specifying input files (ReadTermResultFromInputXls) or sql statements
    protected static String aNumber = null; // for specifying a generic number, will fail if not integer parseable
    protected static String aString = null; // for specifiying a generic string for usage or comparison (Sql.java)
    protected static String report = null; // for specifiying a string unique for the report title
    protected static String aHubServer = null;
    protected static String aHubPort = null;
    protected static String appiumApp = null;
    protected static String appiumUdid = null;
    protected static String appiumIosTargetVersion = null;
    protected static String appiumIosDeviceName = null;
    
    /**
     * TODO: VERIFY IMPLEMENTATION
     */
    //command line variables for BoardScrub#BuildPageOfFoundLinks specifically
    protected static String linksLoadedIndicatorXpath = null;
    protected static String linkXpath = null;
    protected static String imageXpath = null;
    protected static String titleTextXpath = null;
    protected static String bodyTextXpath = null;
    protected static String nextLinkXpath = null;
    
    /**
     * TODO: VERIFY IMPLEMENTATION
     */
    //command line variables for BoardScrub#BuildPageOfFoundLinks and Scratch#VerifyLogos
    protected static String noImages = null; //dont save images  TODO: verify works for not saving screenshots during verifylogs AND not grabbing board pictures AND not grabbing if xpath for verifylogos logo is an "img" tag
    protected static String noScreenShots = null;
    protected static String logging = null;
    protected static String noScroll = null;
    
    // jenkins report folder url
    protected static final String jenkinsReportPath = "http://jaemzware.org/";
    protected static final String jenkinsReportPathInternal = "http://10.1.10.156/";
    protected static final String jenkinsDeployDirectory = "jaemzwareArtifacts";
    
    // default time IN SECONDS to wait when finding elements
    protected static int defaultImplicitWait = 60;
    protected static final int quickWaitMilliSeconds = 5000;  //TODO: PHASING OUT IN PREFERENCE OF COMMAND OVERRIDEABLE waitAfterPageLoadMilliSeconds
    protected static int waitAfterPageLoadMilliSeconds = 0;  //can be overridden from comand line -DwaitAfterPageLoadMilliSeconds=10000

    // verification errors that can occur during a test
    protected StringBuilder verificationErrors = new StringBuilder();

    // save off main window handle, for when dealing with popups
    protected static String mainWindowHandle;
    
        /**
     * compose and return an html string for an html page to the body opener
     * 
     * @param titleHeaderString
     *            - custom title / h1
     * @return
     */
    protected String HtmlReportHeader(String titleHeaderString) {
        StringBuilder returnString = new StringBuilder();

        String jQueryInclude = "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script>";
        
        // standard header
        returnString.append("<html><head>");
        returnString.append(jQueryInclude);
        returnString.append("<title>");
        returnString.append(titleHeaderString);
        returnString.append("</title>");
        returnString.append("<style>");
        returnString.append("table td, table th {border: 1px solid black;text-align:left;vertical-align:top;}");
        returnString.append(".warning {background-color:#C0C0C0;color:#FFFF00;}");
        returnString.append(".severe {background-color:#C0C0C0;color:#FF0000;}");
        returnString.append(".info {background-color:#C0C0C0;color:#000000;}").append("</style>").append("</head>");
        returnString.append("<body><h1>");
        returnString.append(titleHeaderString);
        returnString.append("</h1>");

        return (returnString.toString());
    }

    /**
     * compose and return an html string for an html page from the body closer
     * 
     * @return
     */
    protected String HtmlReportFooter() {
        StringBuilder returnString = new StringBuilder();
        
        returnString.append("<hr>");

        return (returnString.toString());
    }


    /**
     * This function gets the command line parameters.
     * Separated out so that tests can get the parameters without having to start the driver
     * 
     * @return String indicating whether GetParameters() succeeded in obtaining valid values. 
     * An empty string will be returned if there were no errors
     */
    protected static String GetParameters() {
        
        String aHubServerParm = System.getProperty("aHubServer");
        if (aHubServerParm != null && !aHubServerParm.isEmpty()) {
            aHubServer = aHubServerParm;
        }
        System.out.println("-DaHubServer:" + aHubServer);

        String aHubPortParm = System.getProperty("aHubPort");
        if (aHubPortParm != null && !aHubPortParm.isEmpty()) {
            aHubPort = aHubPortParm;
        }
        System.out.println("-DaHubPort:" + aHubPort);

        String browserParm = System.getProperty("browser");
        if (browserParm != null && !browserParm.isEmpty()) {
            try {
                browser = BrowserType.valueOf(browserParm);
            } catch (IllegalArgumentException ex) {
                StringBuilder invalidBrowserMessage = new StringBuilder();

                invalidBrowserMessage.append("INVALID BROWSER (-Dbrowser) SPECIFIED:");
                invalidBrowserMessage.append(browserParm);
                invalidBrowserMessage.append(" VALID VALUES:");

                BrowserType allBrowsers[] = BrowserType.values();
                for (BrowserType validBrowser : allBrowsers) {
                    invalidBrowserMessage.append(validBrowser);
                    invalidBrowserMessage.append(" ");
                }
                return invalidBrowserMessage.toString();
            }
        }
        System.out.println("-Dbrowser:" + browserParm);

        String appiumAppParm = System.getProperty("appiumApp");
        if (appiumAppParm != null && !appiumAppParm.isEmpty()) {
            appiumApp = appiumAppParm;
        }
        System.out.println("-DappiumApp:" + appiumApp);

        
        String appiumUdidParm = System.getProperty("appiumUdid");
        if (appiumUdidParm != null && !appiumUdidParm.isEmpty()) {
            appiumUdid = appiumUdidParm;
        }
        System.out.println("-DappiumUdid:" + appiumUdid);

        
        String appiumIosTargetVersionParm = System.getProperty("appiumIosTargetVersion");
        if (appiumIosTargetVersionParm != null && !appiumIosTargetVersionParm.isEmpty()) {
            appiumIosTargetVersion = appiumIosTargetVersionParm;
        }
        System.out.println("-DappiumIosTargetVersion:" + appiumIosTargetVersion);
        
        String appiumIosDeviceNameParm = System.getProperty("appiumIosDeviceName");
        if (appiumIosDeviceNameParm != null && !appiumIosDeviceNameParm.isEmpty()) {
            appiumIosDeviceName = appiumIosDeviceNameParm;
        }
        System.out.println("-DappiumIosDeviceName:" + appiumIosDeviceName);

        String environmentParm = System.getProperty("environment");
        if (environmentParm != null && !environmentParm.isEmpty()) {
            try {
                environment = EnvironmentType.valueOf(environmentParm);
            } catch (IllegalArgumentException ex) {
                StringBuilder invalidEnvironmentMessage = new StringBuilder();

                invalidEnvironmentMessage.append("INVALID ENVIRONMENT (-Denvironment) SPECIFIED:");
                invalidEnvironmentMessage.append(environmentParm);
                invalidEnvironmentMessage.append(" VALID VALUES:");

                EnvironmentType allEnvironments[] = EnvironmentType.values();
                for (EnvironmentType validEnvironment : allEnvironments) {
                    invalidEnvironmentMessage.append(validEnvironment);
                    invalidEnvironmentMessage.append(" ");
                }
                return invalidEnvironmentMessage.toString();
            }
        }
        System.out.println("-Denvironment:" + environmentParm);

        String waitAfterPageLoadMilliSecondsParm = System.getProperty("waitAfterPageLoadMilliSeconds");
        if (waitAfterPageLoadMilliSecondsParm != null && !waitAfterPageLoadMilliSecondsParm.isEmpty()) {
            try {
                waitAfterPageLoadMilliSeconds = Integer.parseInt(waitAfterPageLoadMilliSecondsParm);
            } catch (NumberFormatException nfx) {
                return "-DwaitAfterPageLoadMilliSecondsParm:" + waitAfterPageLoadMilliSecondsParm
                        + " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)";

            }
        }
        System.out.println("-DwaitAfterPageLoadMilliSeconds:" + waitAfterPageLoadMilliSeconds);
        
        String defaultImplicitWaitParm = System.getProperty("defaultImplicitWait");
        if (defaultImplicitWaitParm != null && !defaultImplicitWaitParm.isEmpty()) {
            try {
                defaultImplicitWait = Integer.parseInt(defaultImplicitWaitParm);
            } catch (NumberFormatException nfx) {
                return "-DdefaultImplicitWaitParm:" + defaultImplicitWaitParm
                        + " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)";

            }
        }
        System.out.println("-DdefaultImplicitWait:" + defaultImplicitWait);
        
        String useridParm = System.getProperty("userid");
        if (useridParm != null && !useridParm.isEmpty()) {
            userid = useridParm;
        }
        System.out.println("-Duserid:" + userid);

        String passwordParm = System.getProperty("password");
        if (passwordParm != null && !passwordParm.isEmpty()) {
            password = passwordParm;
        }
        System.out.println("-Dpassword:" + password);

        String inputParm = System.getProperty("input");
        if (inputParm != null && !inputParm.isEmpty()) {
            input = inputParm;
        }
        System.out.println("-Dinput:" + input);

        String aNumberParm = System.getProperty("aNumber");
        if (aNumberParm != null && !aNumberParm.isEmpty()) {
            try {
                //VERIFY IT'S A NUMBER
                Integer.parseInt(aNumberParm);
                aNumber = aNumberParm;
            } catch (NumberFormatException nfx) {
                return "-DaNumber:" + aNumberParm + " MUST BE A NUMBER";
            }
        }
        System.out.println("-DaNumber:" + aNumber);

        String aStringParm = System.getProperty("aString");
        if (aStringParm != null && !aStringParm.isEmpty()) {
            aString = aStringParm;
        }
        System.out.println("-DaString:" + aString);
        
        String reportParm = System.getProperty("report");
        if (reportParm != null && !reportParm.isEmpty()) {
            report = reportParm;
        }
        System.out.println("-Dreport:" + report);
        
        noImages = System.getProperty("noImages");
        System.out.println("-DnoImages:" + noImages);
        
        logging = System.getProperty("logging");
        System.out.println("-Dlogging:" + logging);
        
        noScroll = System.getProperty("noScroll");
        System.out.println("-DnoScroll:" + noScroll);
        
        noScreenShots = System.getProperty("noScreenShots");
        System.out.println("-DnoScreenshots:" + noScreenShots);
        
        
    /**
     * command line variables for BoardScrub#BuildPageOfFoundLinks specifically
     */
        String linksLoadedIndicatorXpathParm = System.getProperty("linksLoadedIndicatorXpath");
        if (linksLoadedIndicatorXpathParm != null && !linksLoadedIndicatorXpathParm.isEmpty()) {
            linksLoadedIndicatorXpath = linksLoadedIndicatorXpathParm;
        }
        System.out.println("-DlinksLoadedIndicatorXpath:" + linksLoadedIndicatorXpath);
        
        
        String linkXpathParm = System.getProperty("linkXpath");
        if (linkXpathParm != null && !linkXpathParm.isEmpty()) {
            linkXpath = linkXpathParm;
        }
        System.out.println("-DlinkXpath:" + linkXpath);
        
        
        String imageXpathParm = System.getProperty("imageXpath");
        if (imageXpathParm != null && !imageXpathParm.isEmpty()) {
            imageXpath = imageXpathParm;
        }
        System.out.println("-DimageXpath:" + imageXpath);
        
        
        String titleTextXpathParm = System.getProperty("titleTextXpath");
        if (titleTextXpathParm != null && !titleTextXpathParm.isEmpty()) {
            titleTextXpath = titleTextXpathParm;
        }
        System.out.println("-DtitleTextXpath:" + titleTextXpath);
        
        
        String bodyTextXpathParm = System.getProperty("bodyTextXpath");
        if (bodyTextXpathParm != null && !bodyTextXpathParm.isEmpty()) {
            bodyTextXpath = bodyTextXpathParm;
        }
        System.out.println("-DbodyTextXpath:" + bodyTextXpath);
        
        
        String nextLinkXpathParm = System.getProperty("nextLinkXpath");
        if (nextLinkXpathParm != null && !nextLinkXpathParm.isEmpty()) {
            nextLinkXpath = nextLinkXpathParm;
        }
        System.out.println("-DnextLinkXpath:" + nextLinkXpath);

        return "";
    }

    /**
     * This function starts the desired web browser.
     * 
     * @throws Exception
     */
    protected void StartDriver() throws Exception {

        // StartDriver("../");
        StartDriver("SeleniumGrid/");
    }

    /**
     * This function starts the desired web browser
     * 
     * @param relativePathToDrivers
     *            - this allows the calling test to specify the relative path to chromedriver and/or iedriverserver in
     *            the project directory, as that's where they will be in each instance. This was done because an
     *            original test was moved to a subfolder folder, thus giving it a relative path of ../../ instead of
     *            just ../, like all the other projects. NOTE: Chromedriver and iedriverserver are not checked in,
     *            because there are different versions for different platforms, and they're named the same thing: e.g.
     *            iedriverserver.exe (32bit) and iedriverserver.exe (64bit)
     * @assumptions it is the responsibility of the extended test scrip to call GetParameters() before calling
     *              StartDriver()
     * @throws Exception
     */
    protected static void StartDriver(String relativePathToDrivers) throws Exception {

        //make sure a browser was specified
        if (browser == null) {
            throw new Exception("BROWSER (-Dbrowser) NOT SPECIFIED");
        }
        
        //make sure browser specified wasn't for appium
        if(browser.toString().contains("APPIUM")){
            throw new Exception("APPIUM BROWSER TYPES (YOU SPECIFIED:"+browser.toString()+" ARE NOT SUPPORTED BY StartDriver. CALL StartAppiumDriver instead");
        }

        /**
         * LAUNCH GRID BROWSER IF AVAILABLE, AND DRIVER HASN'T BEEN SET
         */
        if (driver == null && 
                System.getProperty("nogrid") == null) {
            try {

                // get the desired capabilities
                DesiredCapabilities cap;

                /**
                 * SET BROWSER SPECIFIC CAPABILITIES
                 */
                // desired browser
                switch (browser) {
                    case CHROME:
                    case CHROMELINUX:
                    case CHROMEMAC:
                        cap = DesiredCapabilities.chrome();
                        break;
                    case FIREFOX:
                    case FIREFOXLINUX:
                    case FIREFOXMAC:
                        cap = DesiredCapabilities.firefox();
                        break;
                    case SAFARI:
                        cap = DesiredCapabilities.safari();

                        // start safari clean (delete all cookies doesn't work)
                        SafariOptions safariOptions = new SafariOptions();
                        safariOptions.setUseCleanSession(true);
                        cap.setCapability(SafariOptions.CAPABILITY, safariOptions);

                        break;
                    case IE8:
                    case IE9:
                    case IE10:
                    case IE11:
                        cap = DesiredCapabilities.internetExplorer();
                        break;
                    default:
                        throw new Exception("NOT CONFIGURED TO LAUNCH THIS BROWSER ON GRID, USING StartDriver.");
                }

                /**
                 * SET LOGGING CAPABILITES
                 */
                // turn on debug logging if debug is specified. this takes longer
                if (logging == null){
                    System.out.println("-Dlogging NOT SPECIFIED");
                } else {
                    LoggingPreferences loggingprefs = new LoggingPreferences();
                    loggingprefs.enable(LogType.BROWSER, Level.ALL);
                    loggingprefs.enable(LogType.CLIENT, Level.ALL);
                    loggingprefs.enable(LogType.DRIVER, Level.ALL);
                    cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

                    System.out.println("-Dlogging SPECIFIED");
                }

                /**
                 * SET COMMON CAPABILITIES
                 */
                cap.setCapability("acceptSslCerts", true);// accept all ssl certificates by default
                cap.setPlatform(browser.platform);// set desired platform (as specified by grid node)
                cap.setBrowserName(browser.browserName);// desired browser name (as set by grid node)
                cap.setVersion(browser.version);// desired version (as set by grid node)

                System.out.println("FINDING SELENIUM GRID NODE:" + browser.browserName + " VERSION:" + browser.version
                        + " PLATFORM:" + browser.platform.toString());

                // get the grid node
                String gridHubFullPath = "http://" + aHubServer + ":" + aHubPort + "/wd/hub";
                System.out.println("CONTACTING SELENIUM GRID [USE -Dnogrid TO SKIP AND LAUNCH NATIVE (NON-GRID)] ");
                driver = new RemoteWebDriver(new URL(gridHubFullPath), cap);

                // augment the driver so that screenshots can be taken
                driver = new Augmenter().augment(driver);

                System.out.println("SUCCESSFULLY GRID NODE FOR:" + browser.browserName + " VERSION:" + browser.version
                        + " PLATFORM:" + browser.platform.toString());
            } catch (Exception ex) {
                if (ex.getMessage().contains("Error forwarding")) {
                    System.out.println("SELENIUM GRID NODE 'browserName=" + browser.browserName + ",version="
                            + browser.version + "' NOT LAUNCHED EXCEPTION:" + ex.getMessage());
                } else if (ex.getMessage().contains("COULD NOT START A NEW SESSION")) {
                    System.out.println("SELENIUM GRID HUB NOT LAUNCHED EXCEPTION:" + ex.getMessage());
                } else {
                    System.out.println("SELENIUM GRID HUB CONNECTION EXCEPTION. VERIFY ONE IS STARTED AT SERVER:"+aHubServer+" PORT:"+aHubPort+" MESSAGE:" + ex.getMessage() );
                }

                driver = null;
            }
        }
        // LAUNCH LOCAL BROWSER, IF DRIVER HASN'T BEEN SET
        else {
            System.out.println("ATTEMPTING TO LAUNCH LOCAL BROWSER:" + browser + " ON:" + GetOsType());

            switch (browser) {
            // CHROME VARIATIONS.
            case CHROMELINUX:
            case CHROMEMAC:
            case CHROME:
            case CHROMEIPHONE6: // CHROME EMULATOR
            case CHROMEIPAD4: // CHROME EMULATOR
            case CHROMEANDROID402: // CHROME EMULATOR

                // chrome uses a different driver binary depending on what os we're on
                switch (GetOsType()) {
                case WINDOWS:
                    System.setProperty("webdriver.chrome.driver", relativePathToDrivers + "chromedriver.exe"); // FOR
                    break;
                case MAC:
                    System.setProperty("webdriver.chrome.driver", relativePathToDrivers + "chromedrivermac"); // FOR MAC
                    break;
                case UNIX:
                    System.setProperty("webdriver.chrome.driver", relativePathToDrivers + "chromedriverlinux64"); // FOR
                                                                                                                  // unix
                    break;
                default:
                    throw new Exception("-Dbrowser=" + browser + " IS UNSUPPORTED NATIVELY ON THIS OS:" + GetOsType());
                }

                // USE CHROME OPTIONS TO SET THE USER AGENT IF REQUESTED (e.g. CHROMEIPHONE6)
                if (browser.equals(BrowserType.CHROMEIPHONE6)) {
                    ChromeOptions options = new ChromeOptions();

                    // iphone ios6
                    options.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25");

                    driver = new ChromeDriver(options);
                } else if (browser.equals(BrowserType.CHROMEIPAD4)) {
                    ChromeOptions options = new ChromeOptions();

                    // ipad ios4
                    options.addArguments("--user-agent=Mozilla/5.0 (iPad; CPU OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5");

                    driver = new ChromeDriver(options);
                } else if (browser.equals(BrowserType.CHROMEANDROID402)) {
                    ChromeOptions options = new ChromeOptions();

                    // android 4.0.2
                    options.addArguments("--user-agent=Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");

                    driver = new ChromeDriver(options);
                } else {
                    // get the chrome driver/start regular chrome
                    // get the desired capabilities
                    DesiredCapabilities cap = DesiredCapabilities.chrome();

                    // turn on debug logging if debug is specified. this takes longer
                    if (logging != null) {
                        //chrome doesnt support this logging type CLIENT
                        LoggingPreferences loggingprefs = new LoggingPreferences();
                        loggingprefs.enable(LogType.BROWSER, Level.ALL);
                        loggingprefs.enable(LogType.DRIVER, Level.ALL);
                        cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
                        
                        System.out.println("-Dlogging SPECIFIED");

                        driver = new ChromeDriver(cap);
                    } else {
                        System.out.println("-Dlogging NOT SPECIFIED");
                        
                        driver = new ChromeDriver(cap);
                    }

                }

                break;
            case FIREFOX:
            case FIREFOXLINUX:
            case FIREFOXMAC:
                if (logging != null) {
                    // get the desired capabilities
                    DesiredCapabilities cap = DesiredCapabilities.firefox();

                    LoggingPreferences loggingprefs = new LoggingPreferences();
                    loggingprefs.enable(LogType.BROWSER, Level.ALL);
                    loggingprefs.enable(LogType.CLIENT, Level.ALL);
                    loggingprefs.enable(LogType.DRIVER, Level.ALL);
                    cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
                    
                    System.out.println("-Dlogging SPECIFIED");

                    driver = new FirefoxDriver(cap);
                } else {
                    System.out.println("-Dlogging NOT SPECIFIED");
                    
                    driver = new FirefoxDriver();
                }
                break;
            case SAFARI:

                if (GetOsType().equals(OsType.MAC)) {
                    DesiredCapabilities cap = DesiredCapabilities.safari();
                    cap.setPlatform(Platform.MAC);

                    // start safari clean (delete all cookies doesn't work)
                    SafariOptions safariOptions = new SafariOptions();
                    safariOptions.setUseCleanSession(true);
                    cap.setCapability(SafariOptions.CAPABILITY, safariOptions);

                    if (logging != null) {
                        LoggingPreferences loggingprefs = new LoggingPreferences();
                        loggingprefs.enable(LogType.BROWSER, Level.ALL);
                        loggingprefs.enable(LogType.CLIENT, Level.ALL);
                        loggingprefs.enable(LogType.DRIVER, Level.ALL);
                        cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
                        
                        System.out.println("-Dlogging SPECIFIED");
                    }
                    else{
                        System.out.println("-Dlogging NOT SPECIFIED");
                    }

                    driver = new SafariDriver(cap);

                } else {
                    throw new Exception("SAFARI IS UNSUPPORTED NATIVELY ON THIS OS:" + GetOsType());
                }

                break;
            case IE8:
            case IE9:
            case IE10:
            case IE11:
                if (GetOsType().equals(OsType.WINDOWS)) {
                    // if we're on windows, just look for the windows driver regardless of version
                    System.setProperty("webdriver.ie.driver", relativePathToDrivers + "IEDriverServer.exe");

                    if (logging != null) {
                        // get the desired capabilities
                        DesiredCapabilities cap = DesiredCapabilities.firefox();

                        LoggingPreferences loggingprefs = new LoggingPreferences();
                        loggingprefs.enable(LogType.BROWSER, Level.ALL);
                        loggingprefs.enable(LogType.CLIENT, Level.ALL);
                        loggingprefs.enable(LogType.DRIVER, Level.ALL);
                        cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
                        
                        System.out.println("-Dlogging SPECIFIED");

                        driver = new InternetExplorerDriver(cap);
                    } else {
                        
                        System.out.println("-Dlogging NOT SPECIFIED");
                        
                        driver = new InternetExplorerDriver();
                    }

                } else {
                    throw new Exception("IE IS UNSUPPORTED NATIVELY ON THIS OS:" + GetOsType());
                }
                break;
            default:
                throw new Exception("NOT CONFIGURED TO LAUNCH THIS BROWSER LOCALLY. MUST USE GRID -Dbrowser:" + browser);
            }

            System.out.println("SUCCESSFULLY LAUNCHED LOCAL BROWSER FOR:" + browser + " ON:" + GetOsType());
        }

        if(driver!=null){

            // safari 7.0.1 doesn't like this for some reason
            if (browser != BrowserType.SAFARI && 
                    !browser.toString().contains("APPIUM")) {
                driver.manage().deleteAllCookies();
            }

            // maximize the window
            driver.manage().window().maximize();

            // set the main window handle
            mainWindowHandle = driver.getWindowHandle();
        }


    }
    
    /** This function starts an appium driver
     * 
     * @throws java.lang.Exception
     */
    protected static void StartAppiumDriver() throws Exception{
        
        /**
         * VERIFY REQUIRED PARAMETERS
         */
        //make sure a browser was specified
        if (browser == null) {
            throw new Exception("BROWSER (-Dbrowser) NOT SPECIFIED");
        }
        
        //make sure a target version was specified, and set it
        if(appiumIosTargetVersion==null){
            throw new Exception("MUST SPECIFY APP -DappiumIosTargetVersion");
        }
        
        //make sure a device name was specified, and set it
        if(appiumIosDeviceName==null){
            throw new Exception("MUST SPECIFY APP -DappiumIosDeviceName");
        }
        
        /**
         * SET THE DESIRED CAPABILITIES
         */
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformVersion", appiumIosTargetVersion);
        cap.setCapability("automationName", "Appium"); // or Selendroid
        cap.setCapability("platformName", "iOS"); // or Android, or FirefoxOS
        cap.setCapability("deviceName", appiumIosDeviceName); 

        // set browser: in the SeleniumCodeBase parameter sense; i.e. app name for devices
        switch (browser) {

            case APPIUMSAFARISIMULATOR: 
                
                //set capability for a safari browser running on an ios simulator
                cap.setCapability("browserName", "Safari");
                break;
            case APPIUMSAFARIDEVICE:
                
                //set capability for a safari browser running on a connected device
                cap.setCapability("browserName", "Safari");
                cap.setCapability("udid",appiumUdid); //get this udid for phone from itunes, click device, then click serial number
                break;
            case APPIUMAPPSIMULATOR: 
                //MAKE SURE APP IS SPECIFIED
                if(appiumApp==null){
                    throw new Exception("MUST SPECIFY APP -DappiumIosTargetVersion -DappiumApp -DappiumIosTargetVersionWHEN USING APPIUMAPPSIMULATOR");
                }
                
                cap.setCapability("app", appiumApp); 

                break;
            case APPIUMAPPDEVICE: 
                //MAKE SURE APP AND DEVICE UDID WERE SPECIFIED
                if(appiumApp==null ){
                    throw new Exception("MUST SPECIFY APP -DappiumApp WHEN USING APPIUMAPPDEVICE");
                }
                
                if(appiumUdid==null){
                    throw new Exception("MUST SPECIFY APP DEVICE -DappiumUdid WHEN USING APPIUMAPPDEVICE");
                }
                
                cap.setCapability("app", appiumApp);
                cap.setCapability("udid",appiumUdid); //get this udid for phone from itunes, click device, then click serial number
                break;
            
            default:
                throw new Exception("NOT CONFIGURED TO LAUNCH THIS BROWSER ON APPIUM StartAppiumDriver");
        }
        
        /**
         * START THE DRIVER ON THE CORRECT APPIUM HUB
         */
        System.out.println("FINDING APPIUM HUB:" + 
                browser.browserName + 
                " VERSION:" + 
                browser.version + 
                " PLATFORM:" + browser.platform.toString());

        // get the APPIUM HUB node
        String gridHubFullPath = "http://" + aHubServer + ":" + aHubPort + "/wd/hub";
        System.out.println("CONTACTING APPIUM HUB");
        
        try{
           
            driver = new RemoteWebDriver(new URL(gridHubFullPath), cap);
            // augment the driver so that screenshots can be taken
            driver = new Augmenter().augment(driver);
            System.out.println("SUCCESSFULLY APPIUM HUB FOR:" + browser.browserName + " VERSION:" + browser.version
                    + " PLATFORM:" + browser.platform.toString()); 
        }
        catch (Exception ex) {
                if (ex.getMessage().contains("Error forwarding")) {
                    System.out.println("APPIUM HUB 'browserName=" + browser.browserName + ",version="
                            + browser.version + "' NOT LAUNCHED EXCEPTION:" + ex.getMessage());
                } else if (ex.getMessage().contains("COULD NOT START A NEW APPIUM HUB SESSION")) {
                    System.out.println("APPIUM HUB NOT LAUNCHED EXCEPTION:" + ex.getMessage());
                } else {
                    System.out.println("APPIUM HUB CONNECTION EXCEPTION. VERIFY ONE IS STARTED AT SERVER:"+aHubServer+" PORT:"+aHubPort+" MESSAGE:" + ex.getMessage() );
                }

                driver = null;
            }
    }
    
    /**
     * This method quits (and closes) the browser. It also sets it to null, in case the same test calls StartDriver
     * twice, for two different browsers.
     */
    protected static void QuitDriver() {
        driver.quit();
        driver = null;
    }

    /** This method gets the current running operating system, for running local browsers -DnoGrid 
     * 
     * @return
     * @throws Exception 
     */
    private static OsType GetOsType() throws Exception {
        // get the os
        String os = System.getProperty("os.name").toLowerCase();

        // set the system property for chromedriver depending on the os
        if (os.contains("win")) {
            return OsType.WINDOWS;
        } else if (os.contains("mac")) {
            return OsType.MAC;
        } else if (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0) {
            return OsType.UNIX;
        } else {
            throw new Exception("UNSUPPORTED OPERATING SYSTEM:" + os);
        }

    }

    /**
     * IsElementPresent stub with default waitTime of 10 seconds (when no wait time specified)
     * 
     * @param locatorKey
     * @return
     */
    protected boolean IsElementPresent(By locatorKey) {
        return IsElementPresent(locatorKey, 10000);
    }

    /**
     * IsElementPresent method, that allows one to specify how long to try finding the element
     * 
     * @param locatorKey
     * @param waitTimeMillis
     * @return
     */
    protected boolean IsElementPresent(By locatorKey, int waitTimeMillis) {
        try {
            //implictlywait cant' work with appium
            if(!browser.toString().contains("APPIUM")){
                // throttle wait time when looking for elements that should already be on the page
                driver.manage().timeouts().implicitlyWait(waitTimeMillis, TimeUnit.MILLISECONDS);
                System.out.println("IsElementPresent set defaultImplicitWait to waitTimeMillis:"+waitTimeMillis);

            }
            
            // look for elements
            return driver.findElements(locatorKey).size() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            if(!browser.toString().contains("APPIUM")){
                // throttle implicit wait time back up
                driver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
                System.out.println("IsElementPresent set defaultImplicitWait back to default seconds:"+defaultImplicitWait);
            }
        }

    }

    /**
     * This method takes a screenshot, and puts it in the current working directory Made static so screenshot can be
     * taken from StartDriver
     * @return file name of the screenshot
     */
    protected static String ScreenShot() {
        String fileName = "";

        try {
            // take the screen shot
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // get path to the working directory
            String workingDir = System.getProperty("user.dir");

            // generate a unique file name
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Date date = new Date();
            String dateStamp = dateFormat.format(date);
            // String fileName = GetOsType().equals(OsType.WINDOWS)?workingDir.replace("\\","\\\\")+
            // "\\screenshot"+dateStamp+".png":workingDir + "/screenshot"+dateStamp+".png";
            fileName = GetOsType().equals(OsType.WINDOWS) ? workingDir + "\\screenshot" + dateStamp + ".png"
                    : workingDir + "/screenshot" + dateStamp + ".png";

            // save the file
            FileUtils.copyFile(scrFile, new File(fileName));
        } catch (Exception ex) {
            System.out.println("COMMON.SCREENSHOT FAILED:" + ex.getMessage());
        }

        System.out.println("SCREENSHOT:" + fileName);
        
        return fileName;
    }

    /**
     * This method is used to print custom, stack traces that will show a custom message, and can be formatted Unlike
     * Exception printStackTrace(), it will not throw an exception.
     * 
     * @param customMessage
     * @param ex
     */
    protected void CustomStackTrace(String customMessage, Exception ex) {
        // write out stack trace to info
        StackTraceElement[] stack = ex.getStackTrace();

        System.out.println("MESSAGE:" + customMessage + " STACK TRACE:");
        for (StackTraceElement line : stack) {
            System.out.println("FILE:" + line.getFileName() + " METHOD:" + line.getMethodName() + " LINE:"
                    + line.getLineNumber());
        }
    }

    /**
     * This method makes an http get request to the provided url, and returns the reponse as a String
     * 
     * @param url
     * @return
     * @throws java.lang.Exception
     */
    protected String HttpGetReturnResponse(String url) throws Exception {
        // HTTPCLIENT/REST EXAMPLES
        // http://www.mkyong.com/java/apache-httpclient-examples/
        // http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-apache-httpclient/

        // make the request
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        // System.out.println("Response Code : "
        // + response.getStatusLine().getStatusCode());

        // read the reponse
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        // System.out.println(result);

        return result.toString();
    }

    /**
     * This method makes a connection to an email server, and returns the first message that matches a search term
     * provided.
     * 
     * @param mailServer
     *            - the mail server to connect to
     * @param user
     *            - the user name to login to the mail server as
     * @param password
     *            - the password for the user
     * @param folderName
     *            - the folder to search for messages in (e.g. "Inbox")
     * @param bodySearchTerm
     *            - the search term for matching messages to be returned
     * @param millisToWait
     * @return
     * @throws java.lang.Exception
     */
    protected String GetFirstEmailMessageForSearchTerm(String mailServer, String user, String password,
            String folderName, String bodySearchTerm, int millisToWait) throws Exception {
        String firstMessage = "";

        // get all messages that match the search term
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        System.out.println("MAILSERVER:" + mailServer + " USER:" + user + " FOLDER:" + folderName + " SEARCHTERM:"
                + bodySearchTerm + "--" + dateFormat.format(date));

        // setup session instance properties
        Properties properties = System.getProperties();

        // need this to access a shared mailbox
        properties.put("mail.imaps.auth.plain.disable", "true");
        properties.put("mail.imaps.auth.ntlm.disable", "true");
        properties.put("mail.imaps.auth.gssapi.disable", "true");

        // connect to the server
        Session session = Session.getInstance(properties);
        Store store = session.getStore("imaps");
        store.connect(mailServer, user, password);

        // open a folder
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_ONLY);

        // mark time to wait for email
        long endTimeMillis = System.currentTimeMillis() + millisToWait;

        Message[] messages = folder.search(new BodyTerm(bodySearchTerm));

        // wait a few minutes for messages if none are found right away
        if (messages.length <= 0) {
            // while waitTime is elapsing
            while (System.currentTimeMillis() < endTimeMillis) {

                // reopen the folder to get around exchange issue
                folder.close(false);
                folder.open(Folder.READ_ONLY);

                // get an array of messages with a keyword in the body
                messages = folder.search(new BodyTerm(bodySearchTerm));

                // fail if no messages are found
                if (messages.length <= 0) {
                    date = new Date();
                    System.out.println("NO MESSAGES FOUND " + dateFormat.format(date));
                    Thread.sleep(10000);
                } else {
                    break;
                }

            }

            if (messages.length <= 0) {
                throw new Exception("NO MESSAGES FOUND AFTER WAITING 300000 ms");
            }
        }

        // print out the content
        // MIME
        if (messages[0].getContent().toString().contains("javax.mail.internet.MimeMultipart")) {

            System.out.println("Message: Multipart");

            Multipart mp = (Multipart) messages[0].getContent();
            for (int j = 0; j < mp.getCount(); j++) {
                if (mp.getBodyPart(j).isMimeType("text/plain")) {
                    System.out.println("text/plain found");
                    firstMessage = (String) mp.getBodyPart(j).getContent();
                } else if (mp.getBodyPart(j).isMimeType("text/html")) {
                    System.out.println("RESEARCH-TEXT/HTML FOUND. COUNT:" + j);
                    firstMessage = (String) mp.getBodyPart(j).getContent();
                    System.out.println("RESEARCH-FIRSTMESSAGE:" + firstMessage);
                    String indexFile = WriteHtmlContentToFile(firstMessage);

                    if (indexFile != null) {
                        return indexFile;
                    } else {
                        throw new Exception("COULD NOT WRITE EMAIL INDEX FILE");
                    }

                } else {
                    System.out.println("CONTENT TYPE:" + mp.getBodyPart(j).getContentType());
                }
            }
        }
        // NON-MIME
        else {
            firstMessage = (String) messages[0].getContent();
        }

        store.close();

        return firstMessage;
    }

    /**
     * This method writes html content to a file, so it can be viewed later
     * 
     * @param htmlContent
     * @return
     */
    protected static String WriteHtmlContentToFile(String htmlContent) {

        PrintWriter writer = null;
        String fileName = null;

        try {
            fileName = "Index-WriteHtmlContent" + getDateStamp() + ".htm";

            writer = new PrintWriter(fileName, "UTF-8");

            writer.println(htmlContent);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("WARNING: COULD NOT WRITE EMAIL RESPONSE IN HTM FILE:" + ex.getMessage());
        } finally {
            // WRITE THE FILE
            // write the file if it was created
            if (writer != null) {
                writer.flush();
                writer.close();

                System.out.println("WROTE RESPONSE TO:" + fileName);
            }
        }

        return fileName;
    }

    /**
     * get a unique datestamp string
     * @return 
     */
    protected static String getDateStamp() {
        // generate a unique file name
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String dateStamp = dateFormat.format(date);

        return dateStamp;
    }

    /**
     * get a url and print out the load time
     * 
     * @param href
     * @throws Exception when driver can't get
     * @return html formatted output
     */
    protected String driverGetWithTime(String href) throws Exception{
        return driverGetWithTime(href,0);
    }
    
    /**
     * get a url and print out the load time
     * 
     * @param href
     * @param randomPauseMaxSeconds
     * @throws Exception when driver can't get
     * @return html formatted output
     */
    protected String driverGetWithTime(String href,int randomPauseMaxSeconds) throws Exception{
        long startTime;

        //this is for the APPIUM WORKaround, as the driver get appears to return before the page is loaded
        final String oldUrl = driver.getCurrentUrl(); 

        System.out.println("=>:" + href);
        
        /////////////
        //random pause
        if(randomPauseMaxSeconds>0){
            Random randomGenerator = new Random();
            int randomPauseSeconds = randomGenerator.nextInt(randomPauseMaxSeconds);
            System.out.println("RANDOM PAUSE:"+randomPauseSeconds*1000);
            Thread.sleep(randomPauseSeconds*1000);
        }

        // mark start time to report how long it takes to load the page
        startTime = System.currentTimeMillis();

        // LOAD THE URL
        try{
            //this sets the timeout for get. implicitly wait is just for findelements. DON'T DO FOR APPIM
            //APPIUM DOESNT LIKE THIS CALL
            //SAFARI DOESNT LIKE THIS CALL
            if(!browser.toString().contains("APPIUM") &&
                    !browser.toString().contains("SAFARI")){
                driver.manage().timeouts().pageLoadTimeout(defaultImplicitWait, TimeUnit.SECONDS);
                System.out.println("SET PAGELOADTIMEOUT TIME TO WAIT FOR DRIVER.GET:"+defaultImplicitWait+" SECONDS");
            }
            
            driver.get(href);
            
            //i think get is returning in appium before the page is loaded on appium, so wait expclicitly for page to change
            (new WebDriverWait(driver, defaultImplicitWait)).until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver d) {
                    return !driver.getCurrentUrl().equals(oldUrl);
                }
            }); 
        }
        catch(Exception ex){
            throw new Exception("DRIVER.GET FAILED. TRY SPECIFYING A LONGER -DdefaultImplicitWait, WHICH IS SET TO "+defaultImplicitWait+" SECONDS FOR THIS RUN. EXCEPTION:"+ex.getMessage());
        }

        // PRINT OUT LOAD TIME
        String loadTimeStatement = Long.toString(System.currentTimeMillis() - startTime);
        System.out.println(loadTimeStatement);
        
        //format an html report response for this driver get call
        String htmlOutput = "";
        htmlOutput += "<hr>";
        htmlOutput += "<table style='width:90%;border:1px solid black;'>";
        htmlOutput += "<tr><th>LOADED</th><th>MILLISECONDS</th></tr>";
        htmlOutput += "<tr><td style='border:1px solid black;'><a href='" + href + "' target='_blank'>" + href + "</a></td><td style='border:1px solid black;'>" + loadTimeStatement + "</td></tr>";
        htmlOutput += "</table>";
        htmlOutput += "<hr>";
        
        if(noScreenShots==null){
            // TAKE A SCREENSHOT
            String screenshotFilePath= ScreenShot();
            String screenshotFilename = screenshotFilePath.substring(screenshotFilePath.lastIndexOf("/")+1);
            htmlOutput +=  "SCREENSHOT OF <a href='"+href+"' target='_blank'><H3>"+href+"</H3></a> TAKEN:"+screenshotFilename+"<br />";
            htmlOutput += "INTERNET DEPLOY REFERENCE (FOR EMAIL/WAN):<br /><img src='"+jenkinsReportPath+jenkinsDeployDirectory+"/"+screenshotFilename+"' /><br />";
            htmlOutput += "INTRANET DEPLOY REFERENCE (FOR LAN):<br /><img src='"+jenkinsReportPathInternal+jenkinsDeployDirectory+"/"+screenshotFilename+"' /><br />";
            htmlOutput += "LOCAL REFERENCE (FOR LOCALHOST):<br /><img src='"+screenshotFilename+"' /><br />";

        }
        
        //OVERRIDEABLE SLEEP
        System.out.println("VARIABLE SLEEP: -DwaitAfterPageLoadMilliSeconds:"+waitAfterPageLoadMilliSeconds+"ms");
        Thread.sleep(waitAfterPageLoadMilliSeconds);
        
        //SWITCH BACK TO MAIN WINDOW IN CASE THERE'S A POPUP
        //DON'T DO THIS IF APPIUM THOUGH
        if(!browser.toString().contains("APPIUM")){
            driver.switchTo().window(mainWindowHandle);
        }

        return (htmlOutput);

    }

    /**
     * save off the rest request response of a given url
     * 
     * @param url
     * @throws Exception
     */
    protected void RestRequest(String url) throws Exception {
        try {

            String response = HttpGetReturnResponse(url);

            CodeBase.WriteHtmlContentToFile(response);
        } catch (Exception ex) {
            CustomStackTrace("RestRequest EXCEPTION", ex);
            throw new Exception(ex);
        }
    }


    /**
     * this method just scrolls the page down a  times
     */
    @SuppressWarnings("SleepWhileInLoop")
    protected void ScrollPage(){
        
        /**
         * Scroll page, unless specificallu told not to
         */
        if(noScroll==null){
            try{

                //get the total height of the web page
                Object documentHeight = ((JavascriptExecutor)driver).executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight )");

                //not sure why, but this get null sometimes
                if(documentHeight==null){
                    System.out.println("JAVASCRIPT TO RETRIEVE documentHeight RETURNED A NULL VALUE");
                    return;
                }
                else{
                    System.out.println("documentHeight:"+documentHeight.toString());
                }

                //get the y distance that is scrolled down
                Object pageYOffset = ((JavascriptExecutor)driver).executeScript("return window.pageYOffset");
                System.out.println("pageYOffset:"+pageYOffset.toString());

                //get the height of the visible web page
                Object innerHeight = ((JavascriptExecutor)driver).executeScript("return window.innerHeight");
                System.out.println("innerHeight:"+innerHeight.toString());

                //scroll down till at the bottom
                while(Integer.parseInt(pageYOffset.toString()) < 
                        (Integer.parseInt(documentHeight.toString())-Integer.parseInt(innerHeight.toString())-1)){

                    ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+innerHeight.toString()+")");

                    //make sure the page scrolled.  there was one case in fark where it didn't, so just break if that happens
                    Object newPageYOffset = ((JavascriptExecutor)driver).executeScript("return window.pageYOffset");
                    if(newPageYOffset.toString().equals(pageYOffset.toString())){
                        System.out.println("NEW PAGE Y OFFSET IS THE SAME, BREAKING SCROLL");
                        break;
                    }
                    else{
                        pageYOffset=newPageYOffset;
                        System.out.println("pageYOffset:"+pageYOffset.toString());
                    }

                    innerHeight = ((JavascriptExecutor)driver).executeScript("return window.innerHeight");
                    System.out.println("innerHeight:"+innerHeight.toString());

                    //sleep for a sec
                    Thread.sleep(waitAfterPageLoadMilliSeconds);
                }

            }
            catch(NumberFormatException | InterruptedException ex){
                CustomStackTrace("SCROLLING EXCEPTION",ex);
            }
        }
        else{
            System.out.println("-DnoScroll SPECIFIED. REMOVE FROM COMMAND LINE TO ENABLE PAGE SCROLLING HERE. ");
        }
    }
    
    /**
     * This method waits for the page to change, when paging through results
     * 
     * @param oldUrl
     *            - old value of what should be at resultStatsTextXpath
     * @param urlWithParms
     *            - informational only just used to print out to console, what page is being loaded
     * @throws Exception
     */
    protected void WaitForPageChange(String oldUrl)  {
        try{
        final String waitTillUrlIsNot = oldUrl; // string to wait for to change when the page is loaded

        System.out.println("WAITING FOR PAGE TO CHANGE FROM:"+oldUrl);
        
        // wait for links to be loaded by waiting for the resultStatsText to change
        (new WebDriverWait(driver, waitAfterPageLoadMilliSeconds)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return !driver.getCurrentUrl().equals(waitTillUrlIsNot);
            }
        });
        
        System.out.println("PAGE CHANGED FROM:"+oldUrl+" TO:"+driver.getCurrentUrl());
        }
        catch(Exception ex){
            ScreenShot();
            CustomStackTrace("WAITFORPAGECHANGE EXCEPTION", ex);
            System.out.println("WARNING: WAITFORPAGE CHANGE FAILED, MOVING ON. EXCEPTION:"+ex.getMessage());
        }

    }

    // ERROR LOGGING - TAKES LONG - ADD CAPABILITY WHEN CREATING driver BEFORE USING
    protected String ExtractJSLogs() {
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
    protected String WriteLogEntryRows(LogEntries entries) {
        StringBuilder logEntryRows = new StringBuilder();

        String errorLevel;
        for (LogEntry entry : entries) {
            errorLevel = entry.getLevel().toString();
            logEntryRows.append("<tr>");

            // error level color coding
            if (errorLevel.contains("SEVERE")) {
                logEntryRows.append("<td class='severe'><b>");
                logEntryRows.append(errorLevel).append("</b></td>");
                logEntryRows.append("<td>").append(entry.getMessage()).append("</td></tr>");
            } 
//            else if (errorLevel.contains("WARNING")) {
//                logEntryRows.append("<td class='warning'><b>");
            
//                logEntryRows.append(errorLevel).append("</b></td>");
//                logEntryRows.append("<td>").append(entry.getMessage()).append("</td></tr>");
//            } else if (errorLevel.contains("INFO")) {
//                logEntryRows.append("<td class='info'><b>");
                
//                logEntryRows.append(errorLevel).append("</b></td>");
//                logEntryRows.append("<td>").append(entry.getMessage()).append("</td></tr>");
//            } else if (errorLevel.contains("FINE")) {
//                logEntryRows.append("<td class='info'><b>");
                
//                logEntryRows.append(errorLevel).append("</b></td>");
//                logEntryRows.append("<td>").append(entry.getMessage()).append("</td></tr>");
//            } else {
//                logEntryRows.append("<td><b>");
                
//                logEntryRows.append(errorLevel).append("</b></td>");
//                logEntryRows.append("<td>").append(entry.getMessage()).append("</td></tr>");
//                
//            }
            
            
        }

        return logEntryRows.toString();
    }
    
    /**
     * This method verifies logoxpath is on the currentpage
     * 
     * @param xpathToVerify
     */
    protected String VerifyXpathOnCurrentPage(String xpathToVerify) {

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


    
}
