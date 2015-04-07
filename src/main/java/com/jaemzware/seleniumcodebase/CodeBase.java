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
import java.util.Properties;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

/**
 * CodeBase
 * 
 * @author jaemzware@hotmail.com
 */
public class CodeBase {
    // the one and only driver object
    protected static WebDriver driver = null;

    // selenium grid hub defaults
    protected static String aHubServer = "localhost";
    protected static String aHubPort = "4444";

    // appium app and udid defaults
    protected static String appiumApp = null;
    protected static String appiumUdid = null;
    
    // jenkins report folder url
    protected static final String jenkinsReportHeader = "";
    protected static final String jenkinsReportFooter = "";
    protected static final String jenkinsReportPath = "http://jaemzware.com:8080/";
    protected static final String jenkinsReportPathInternal = "http://10.1.10.156:8080/";

    // recognized command line variables
    protected static String userid = null; // for tests that need to authenticate
    protected static String password = null; // for tests that need to authenticate
    protected static String input = null; // for specifying input files (ReadTermResultFromInputXls) or sql statements
                                          // (Sql.java)
    protected static String aNumber = null; // for specifying a generic number for usage or comparison (Sql.java), will
                                            // fail if not integer parseable
    protected static String aString = null; // for specifiying a generic string for usage or comparison (Sql.java)

    protected static EnvironmentType environment = null;
    protected static BrowserType browser = null;

    // default time IN SECONDS to wait when finding elements
    protected int defaultImplicitWait = 60;
    protected static final int quickWaitMilliSeconds = 5000;  //TODO: PHASING OUT IN PREFERENCE OF COMMAND OVERRIDEABLE waitAfterPageLoadMilliSeconds
    protected static int waitAfterPageLoadMilliSeconds = 0;  //can be overridden from comand line

    // verification errors that can occur during a test
    protected StringBuilder verificationErrors = new StringBuilder();

    // save off main window handle, for when dealing with popups
    protected static String mainWindowHandle;

    /**
     * This function gets the command line parameters.
     * Separated out so that tests can get the parameters without having to start the driver
     * 
     * @return String indicating whether GetParameters() succeeded in obtaining valid values. An empty string will be
     *         returned if there were no errors
     */
    protected static String GetParameters() {
        System.out.println("COMMAND LINE PARAMETERS");

        // WAITAFTERPAGELOADMILLISECONDS
        // get waitAfterPageLoadMilliSeconds specified on command line
        String waitAfterPageLoadMilliSecondsParm = System.getProperty("waitAfterPageLoadMilliSeconds");

        if (waitAfterPageLoadMilliSecondsParm == null || waitAfterPageLoadMilliSecondsParm.isEmpty()) {
            System.out.println("-DwaitAfterPageLoadMilliSeconds NOT SPECIFIED.");
        } else {
            // make sure aNumber is parseable
            try {
                waitAfterPageLoadMilliSeconds = Integer.parseInt(waitAfterPageLoadMilliSecondsParm);

                System.out.println("-DwaitAfterPageLoadMilliSeconds:" + waitAfterPageLoadMilliSeconds);
            } catch (NumberFormatException nfx) {
                return "-DwaitAfterPageLoadMilliSecondsParm:" + waitAfterPageLoadMilliSecondsParm
                        + " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)";

            }
        }
        
        // APPIUMAPPPARM
        // get appiumApp specified on command line
        String appiumAppParm = System.getProperty("appiumApp");

        if (appiumAppParm == null || appiumAppParm.isEmpty()) {
            System.out.println("-DappiumApp NOT SPECIFIED.");
        } else {
            appiumApp = appiumAppParm;

            System.out.println("-DappiumApp:" + appiumApp);
        }
        
        
        // APPIUMUDID
        // get appiumUdid specified on command line
        String appiumUdidParm = System.getProperty("appiumUdid");

        if (appiumUdidParm == null || appiumUdidParm.isEmpty()) {
            System.out.println("-DappiumUdid NOT SPECIFIED.");
        } else {
            appiumUdid = appiumUdidParm;

            System.out.println("-DappiumUdid:" + appiumUdid);
        }
        
        // USERID
        // get userid specified on command line
        String useridParm = System.getProperty("userid");

        if (useridParm == null || useridParm.isEmpty()) {
            System.out.println("-Duserid NOT SPECIFIED.");
        } else {
            userid = useridParm;

            System.out.println("-Duserid:" + userid);
        }

        // PASSWORD
        // get password specified on command line
        String passwordParm = System.getProperty("password");

        if (passwordParm == null || passwordParm.isEmpty()) {
            System.out.println("-Dpassword NOT SPECIFIED.");
        } else {
            password = passwordParm;

            System.out.println("-Dpassword:********");
        }

        // INPUT
        // get input specified on command line
        String inputParm = System.getProperty("input");

        if (inputParm == null || inputParm.isEmpty()) {
            System.out.println("-Dinput NOT SPECIFIED.");
        } else {
            input = inputParm;

            System.out.println("-Dinput:" + input);
        }

        // ANUMBER
        // get aNumber specified on command line
        String aNumberParm = System.getProperty("aNumber");

        if (aNumberParm == null || aNumberParm.isEmpty()) {
            System.out.println("-DaNumber NOT SPECIFIED.");
        } else {
            // make sure aNumber is parseable
            try {
                Integer.parseInt(aNumberParm);

                aNumber = aNumberParm;

                System.out.println("-DaNumber:" + aNumber);
            } catch (NumberFormatException nfx) {
                return "-DaNumber:" + aNumberParm
                        + " SPECIFIED IS NOT A PARSEABLE INT. RETURNING FALSE (MAY BE IGNORED BY SOME TESTS)";

            }

        }

        // ASTRING
        // get aString specified on command line
        String aStringParm = System.getProperty("aString");

        if (aStringParm == null || aStringParm.isEmpty()) {
            System.out.println("-DaString NOT SPECIFIED.");
        } else {
            aString = aStringParm;

            System.out.println("-DaString:" + aString);
        }

        // AHUBSERVER
        // get aHubServer specified on command line (for selenium grid hub), FOR WHEN IT'S DIFFERENT THAN LOCALHOST
        String aHubServerParm = System.getProperty("aHubServer");

        if (aHubServerParm == null || aHubServerParm.isEmpty()) {
            System.out.println("-DaHubServer NOT SPECIFIED. USING DEFAULT aHubServer:"+aHubServer);
        } else {
            aHubServer = aHubServerParm;
            System.out.println("-DaHubServer SPECIFIED. aHubServer:" + aHubServer);
        }
        
        // AHUBPORT
        // get aHubPort specified on command line (for selenium grid hub), FOR WHEN IT'S DIFFERENT THAN 4444
        String aHubPortParm = System.getProperty("aHubPort");

        if (aHubPortParm == null || aHubPortParm.isEmpty()) {
            System.out.println("-DaHubPort NOT SPECIFIED. USING DEFAULT aHubPort:"+aHubPort);
        } else {
            aHubPort = aHubPortParm;
            System.out.println("-DaHubPort SPECIFIED. aHubPort:" + aHubPort);
        }

        // BROWSER
        // get browser type specified on command line
        String browserParm = System.getProperty("browser");

        if (browserParm == null || browserParm.isEmpty()) {
            // if browser is not specified, output which one is being used by default
            System.out.println("-Dbrowser NOT SPECIFIED.");
        } else {
            try {
                browser = BrowserType.valueOf(browserParm);

                System.out.println("-Dbrowser:" + browserParm);
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

        // ENVIRONMENT
        // get environment type specified on command line
        String environmentParm = System.getProperty("environment");

        if (environmentParm == null || environmentParm.isEmpty()) {
            // if environment is not specified, output which one is being used by default
            System.out.println("-Denvironment NOT SPECIFIED. USING DEFAULT:" + environment);
        } else {
            try {
                environment = EnvironmentType.valueOf(environmentParm);

                System.out.println("-Denvironment:" + environmentParm);
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
     * This function starts the desired web browser.
     * 
     * @throws Exception
     */
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

        // LAUNCH GRID BROWSER IF AVAILABLE, AND DRIVER HASN'T BEEN SET
        // try to get the browser on selenium grid
        // don't do this if -Dnogrid is specified though
        if (driver == null && 
                System.getProperty("nogrid") == null) {
            try {

                // get the desired capabilities
                DesiredCapabilities cap;

                // desired browser
                switch (browser) {
                    
                    case APPIUMSIMULATORAPPSAFARI: //SAFARI APP IN SIMULATOR
                        cap = new DesiredCapabilities();
                        cap.setCapability("automationName", "Appium"); // or Selendroid
                        cap.setCapability("platformName", "iOS"); // or Android, or FirefoxOS
                        cap.setCapability("platformVersion", "8.2");
                        cap.setCapability("browserName", "Safari");
                        cap.setCapability("deviceName", "iPhone Simulator"); //"iPad Simulator"
                        System.out.println("ASSUMING APPIUM IS STARTED.  IF THIS FAILS, IT MIGHT NOT BE.");
                        break;
                    case APPIUMSIMULATORAPPSCRATCH: //NATIVE APP IN SIMULATOR
                        //MAKE SURE APP IS SPECIFIED
                        if(appiumApp==null){
                            throw new Exception("MUST SPECIFY APP -DappiumApp WHEN USING APPIUMSIMULATORAPPSCRATCH");
                        }
                        cap = new DesiredCapabilities();
                        cap.setCapability("automationName", "Appium"); // or Selendroid
                        cap.setCapability("platformName", "iOS"); // or Android, or FirefoxOS
                        cap.setCapability("platformVersion", "8.2");
                        cap.setCapability("app", appiumApp); 
                        cap.setCapability("deviceName", "iPhone Simulator"); //"iPad Simulator"
                        
                        System.out.println("ASSUMING APPIUM IS STARTED.  IF THIS FAILS, IT MIGHT NOT BE.");
                        break;
                    case APPIUMDEVICEAPPSCRATCH: //NATIVE APP ON REAL DEVICE
                        //MAKE SURE APP AND DEVICE UDID WERE SPECIFIED
                        if(appiumApp==null || appiumUdid==null){
                            throw new Exception("MUST SPECIFY APP -DappiumApp AND DEVICE -DappiumUdid WHEN USING APPIUMDEVICEAPPSCRATCH");
                        }
                        cap = new DesiredCapabilities();
                        cap.setCapability("automationName", "Appium"); // or Selendroid
                        cap.setCapability("platformName", "iOS"); // or Android, or FirefoxOS
                        cap.setCapability("platformVersion", "8.2");
                        cap.setCapability("app", appiumApp);
                        cap.setCapability("udid",appiumUdid); //get this udid for phone from itunes, click device, then click serial number
                        cap.setCapability("deviceName", "iJaemzware"); 
                        System.out.println("ASSUMING APPIUM IS STARTED.  IF THIS FAILS, IT MIGHT NOT BE.");
                        break;
                   case APPIUMGROCERYSHOPPINGTIMEDEVICE:
                       //MAKE SURE APP AND DEVICE UDID WERE SPECIFIED
                        if(appiumApp==null || appiumUdid==null){
                            throw new Exception("MUST SPECIFY APP -DappiumApp AND DEVICE -DappiumUdid WHEN USING APPIUMDEVICEAPPSCRATCH");
                        }
                        cap = new DesiredCapabilities();
                        cap.setCapability("automationName", "Appium"); // or Selendroid
                        cap.setCapability("platformName", "iOS"); // or Android, or FirefoxOS
                        cap.setCapability("platformVersion", "8.2");
                        cap.setCapability("app", appiumApp);
                        cap.setCapability("udid",appiumUdid); //get this udid for phone from itunes, click device, then click serial number
                        cap.setCapability("deviceName", "iJaemzware"); 
                        System.out.println("ASSUMING APPIUM IS STARTED.  IF THIS FAILS, IT MIGHT NOT BE.");
                        break;
                   case APPIUMGROCERYSHOPPINGTIMESIMULATOR:
                        //MAKE SURE APP IS SPECIFIED
                        if(appiumApp==null){
                            throw new Exception("MUST SPECIFY APP -DappiumApp WHEN USING APPIUMSIMULATORAPPSCRATCH");
                        }
                        cap = new DesiredCapabilities();
                        cap.setCapability("automationName", "Appium"); // or Selendroid
                        cap.setCapability("platformName", "iOS"); // or Android, or FirefoxOS
                        cap.setCapability("platformVersion", "8.2");
                        cap.setCapability("app", appiumApp); 
                        cap.setCapability("deviceName", "iPhone Simulator"); //"iPad Simulator"
                        
                        //TODO - TRYING STUFF - CODE REVIEW
                        cap.setCapability("fullReset", true); 
                        
                        System.out.println("ASSUMING APPIUM IS STARTED.  IF THIS FAILS, IT MIGHT NOT BE.");
                        break;
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
                        throw new Exception("NOT CONFIGURED TO LAUNCH THIS BROWSER ON GRID. -Dbrowser:\" + browser");
                }

                //don't do any this for appium
                if(!browser.toString().contains("APPIUM")){
                    // turn on debug logging if debug is specified. this takes longer
                    if (System.getProperty("logging") == null || browser.toString().contains("APPIUM")) {
                    } else {
                        LoggingPreferences loggingprefs = new LoggingPreferences();
                        loggingprefs.enable(LogType.BROWSER, Level.ALL);
                        loggingprefs.enable(LogType.CLIENT, Level.ALL);
                        loggingprefs.enable(LogType.DRIVER, Level.ALL);
                        cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
                    }

                    // accept all ssl certificates by default
                    cap.setCapability("acceptSslCerts", true);

                    // set desired platform (as specified by grid node)
                    cap.setPlatform(browser.platform);

                    // desired browser name (as set by grid node)
                    cap.setBrowserName(browser.browserName);

                    // desired version (as set by grid node)
                    cap.setVersion(browser.version);
                }

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
                    System.out.println("SELENIUM GRID CONNECTION EXCEPTION. VERIFY ONE IS STARTED AT SERVER:"+aHubServer+" PORT:"+aHubPort+" MESSAGE:" + ex.getMessage() );
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
                                                                                                               // WINDOWS
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
                    if (System.getProperty("logging") != null) {
                        LoggingPreferences loggingprefs = new LoggingPreferences();
                        loggingprefs.enable(LogType.BROWSER, Level.ALL);
                        // loggingprefs.enable(LogType.CLIENT, Level.ALL); //chrome doesnt support this logging type
                        loggingprefs.enable(LogType.DRIVER, Level.ALL);
                        cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

                        driver = new ChromeDriver(cap);
                    } else {
                        driver = new ChromeDriver(cap);
                    }

                }

                break;
            case FIREFOX:
            case FIREFOXLINUX:
            case FIREFOXMAC:
                if (System.getProperty("logging") != null) {
                    // get the desired capabilities
                    DesiredCapabilities cap = DesiredCapabilities.firefox();

                    LoggingPreferences loggingprefs = new LoggingPreferences();
                    loggingprefs.enable(LogType.BROWSER, Level.ALL);
                    loggingprefs.enable(LogType.CLIENT, Level.ALL);
                    loggingprefs.enable(LogType.DRIVER, Level.ALL);
                    cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

                    driver = new FirefoxDriver(cap);
                } else {
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

                    if (System.getProperty("logging") != null) {
                        LoggingPreferences loggingprefs = new LoggingPreferences();
                        loggingprefs.enable(LogType.BROWSER, Level.ALL);
                        loggingprefs.enable(LogType.CLIENT, Level.ALL);
                        loggingprefs.enable(LogType.DRIVER, Level.ALL);
                        cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
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

                    if (System.getProperty("logging") != null) {
                        // get the desired capabilities
                        DesiredCapabilities cap = DesiredCapabilities.firefox();

                        LoggingPreferences loggingprefs = new LoggingPreferences();
                        loggingprefs.enable(LogType.BROWSER, Level.ALL);
                        loggingprefs.enable(LogType.CLIENT, Level.ALL);
                        loggingprefs.enable(LogType.DRIVER, Level.ALL);
                        cap.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

                        driver = new InternetExplorerDriver(cap);
                    } else {
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

        // start with no cookies

        // safari 7.0.1 doesn't like this for some reason
        if (browser != BrowserType.SAFARI && 
                !browser.toString().contains("APPIUM")) {
            driver.manage().deleteAllCookies();
        }

        // maximize browser (not supported by appium)
        if (!browser.toString().contains("APPIUM")) {
            // maximize the window
            driver.manage().window().maximize();
            
            // set the main window handle
            mainWindowHandle = driver.getWindowHandle();
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
            // throttle wait time when looking for elements that should already be on the page
            driver.manage().timeouts().implicitlyWait(waitTimeMillis, TimeUnit.MILLISECONDS);

            // look for elements
            return driver.findElements(locatorKey).size() > 0;
        } catch (Exception ex) {
            return false;
        } finally {
            // throttle implicit wait time back up
            driver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
        }

    }

    /**
     * This method takes a screenshot, and puts it in the current working directory Made static so screenshot can be
     * taken from StartDriver
     */
    protected static void ScreenShot() {
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

    // get a unique datestamp string
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
        long startTime;

        String htmlOutput = "";

        System.out.println("GETTING:" + href);
        htmlOutput += "<br /><h1>GETTING:<a href='" + href + "' target='_blank'>" + href + "</a></h1>";

        // mark start time to report how long it takes to load the page
        startTime = System.currentTimeMillis();

        // LOAD THE URL
        try{
            //this sets the timeout for get. implicitly wait is just for findelements
            driver.manage().timeouts().pageLoadTimeout(defaultImplicitWait, TimeUnit.SECONDS);
            driver.get(href);
        }
        catch(Exception ex){
            throw new Exception("DRIVER.GET FAILED. EXCEPTION:"+ex.getMessage());
        }

        // PRINT OUT LOAD TIME
        String loadTimeStatement = "LOADTIME(ms):" + (System.currentTimeMillis() - startTime);
        System.out.println(loadTimeStatement);
        htmlOutput += "<br />" + loadTimeStatement;
        
        //OVERRIDEABLE SLEEP
        System.out.println("SLEEP FROM COMMAND LINE PARAMETER: -DwaitAfterPageLoadMilliSeconds (IF SPECIFIED; OTHERWISE SOME DEFAULT)"+waitAfterPageLoadMilliSeconds+"ms");
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
     * compose and return an html string for an html page to the body opener
     * 
     * @param titleHeaderString
     *            - custom title / h1
     * @return
     */
    protected String HtmlReportHeader(String titleHeaderString) {
        StringBuilder returnString = new StringBuilder();

        // standard header
        returnString.append("<html><head><title>").append(titleHeaderString).append("</title>").append("<style>")
                .append("table td, table th {border: 1px solid black;text-align:left;vertical-align:top;}")
                .append(".warning {background-color:#C0C0C0;color:#FFFF00;}")
                .append(".severe {background-color:#C0C0C0;color:#FF0000;}")
                .append(".info {background-color:#C0C0C0;color:#000000;}").append("</style>").append("</head>")
                .append("<body><h1>").append(titleHeaderString).append("</h1>");
        // paypal
        returnString.append("<form action='https://www.paypal.com/cgi-bin/webscr' method='post' target='_top'>");
        returnString.append("<input type='hidden' name='cmd' value='_s-xclick'>");
        returnString.append("<input type='hidden' name='hosted_button_id' value='NHYPV5J79879N'>");
        returnString
                .append("<input type='image' src='https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif' border='0' name='submit' alt='PayPal - The safer, easier way to pay online!'>");
        returnString
                .append("<img alt='' border='0' src='https://www.paypalobjects.com/en_US/i/scr/pixel.gif' width='1' height='1'>");
        returnString.append("</form>");

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

        returnString
                .append("<a href='mailto:jaemzware@hotmail.com' target='_blank'>jaemzware@hotmail.com</a><br /><a href='https://www.linkedin.com/pub/james-arasim/15/991/424'>LinkedIn</a>");

        // paypal
        returnString.append("<form action='https://www.paypal.com/cgi-bin/webscr' method='post' target='_top'>");
        returnString.append("<input type='hidden' name='cmd' value='_s-xclick'>");
        returnString.append("<input type='hidden' name='hosted_button_id' value='NHYPV5J79879N'>");
        returnString
                .append("<input type='image' src='https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif' border='0' name='submit' alt='PayPal - The safer, easier way to pay online!'>");
        returnString
                .append("<img alt='' border='0' src='https://www.paypalobjects.com/en_US/i/scr/pixel.gif' width='1' height='1'>");
        returnString.append("</form>");

        return (returnString.toString());
    }

    /**
     * this method just scrolls the page down a  times
     */
    protected void ScrollPage(){
        
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
        catch(Exception ex){
            CustomStackTrace("SCROLLING EXCEPTION",ex);
        }
    }
}
