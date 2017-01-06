/*
 * jaemzware
 */
package com.jaemzware.seleniumcodebase;

//ALL PARAMETERS DEFINED HERE ARE OVERRIDABLE FROM THE MAVEN COMMAND LINE WITH -D{parametertype} (e.g. -DaNumber=-1)
//**********WHEN ADDING VARIABLES HERE YOU MUST IMPLEMENT THEM BELOW IN SetParameter TOO!!!!!!!****************/
public class ParameterType {
    public static BrowserType browser=BrowserType.CHROME;

    public static String nogrid=null; //dont use selenium grid. default behavior is to look for grid on aHubPort aHubServer
    public static String aHubServer="localhost"; //where to look for selenium grid. server name only. default behavior is to look for one.
    public static String aHubPort="4444"; //port to use for selenium grid, if looking for one.

    public static String appiumApp=null;//"/Users/jameskarasim/Library/Developer/Xcode/DerivedData/Scratch-cdvmqpqxkymrtecctsbjrwupqtya/Build/Products/Debug-iphoneos/Scratch.app";
    public static String appiumIosDeviceName=null;//"iPhone 6"; //ijaemzware
    public static String appiumIosTargetVersion=null;//"9.1";
    public static String appiumUdid=null;//"88ff683cec637c3f1279386620b5397d48bc8341";
    public static String appiumBinaryJSPath="/Users/jameskarasim/Downloads/installed/repositories/appium/bin/appium.js";
    public static String appiumBinaryNodeJSPath="/usr/local/bin/node";
    
    public static int defaultImplicitWaitSeconds=15; //implicit wait time for finding elements on a page, where the page being loaded is a factor (selenium)
    public static int hardCodedSleepMilliSeconds=5000; //facebookcrawlalllinks is the only test that uses this
    public static int throttleDownWaitTimeMilliSeconds=500; //how long to wait when throttling down to look for elements after a page is known to be loaded (boardscrub craigslist codebase iselementpresent default)
    public static int waitAfterPageLoadMilliSeconds=0; //how long to wait for thread.sleep OR after a page loads from a link click (see protected String driverGetWithTime)
    public static int waitForPageChangeMilliSeconds=60000; //how long to wait for a page to change from an old url to a new one (see protected void WaitForPageChange)

    public static EnvironmentType environment=null; //not used; pending deprecation
    public static int aNumber=0; //verifylogos and boardscrub use it to signify how many links to visit. =<0 == visit all
    public static String aString=null; //verifylogos uses it to signify what element to look for as its "logo"
    public static String input=null; //verifylogos and boardscrub use it specify the first page to load
    public static String logging=null; //turn on logging to report browser errors, currently used only with CHROME
    public static String noImages=null; //boardscrub uses this to omit images found in its report.
    public static String noScreenShots=null; //codebase drivergetwithtime uses this to decide whether to save screenshots
    public static String noScroll=null; //codebase ScrollPage() will refrain from scrolling if this is specified
    public static String password=null; //usage depends on test
    public static String report=null; //verifylogos and boardscrub use this to name their reports: index{report}.htm
    public static String userid=null;//usage depends on test

    public static final String jenkinsReportPath = null;//"http://computer.local:8080/job/verifylogosappium/ws/";
    public static final String jenkinsReportPathInternal = null;//"http://localhost:8080/job/verifylogosappium/ws/";
    public static final String jenkinsDeployDirectory = null;//"job/verifylogos/ws/";
    
    //BOARDSCRUB ONLY USES THESE
    public static String bodyTextXpath=null;
    public static String imageXpath =null;
    public static String linksLoadedIndicatorXpath =null;
    public static String linkXpath =null;
    public static String nextLinkXpath =null;
    public static String titleTextXpath=null;
    public static String numResultsRESTParm=null;
    public static String startRESTParm=null;
    
    public static void SetParameter(String parameter, String parameterValue) throws Exception{
        
        switch (parameter){
            case "aHubPort":
                aHubPort=parameterValue;
                break;
            case "aHubServer":
                aHubServer=parameterValue;
                break;
            case "aNumber":
                try {
                    aNumber = Integer.parseInt(parameterValue);
                } catch (NumberFormatException nfx) {
                    throw new Exception("-DaNumber:" + parameterValue+ " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)");
                }
                break;
            case "appiumApp":
                appiumApp=parameterValue;
                break;
            case "appiumIosDeviceName":
                appiumIosDeviceName=parameterValue;
                break;
            case "appiumIosTargetVersion":
                appiumIosTargetVersion=parameterValue;
                break;
            case "appiumUdid":
                appiumUdid=parameterValue;
                break;
            case "appiumBinaryJSPath":
                appiumBinaryJSPath=parameterValue;
            case "appiumBinaryNodeJSPath":
                appiumBinaryNodeJSPath=parameterValue;
            case "aString":
                aString=parameterValue;
                break;
            case "browser":
                try {
                    browser = BrowserType.valueOf(parameterValue);
                } catch (IllegalArgumentException ex) {
                    StringBuilder invalidBrowserMessage = new StringBuilder();

                    invalidBrowserMessage.append("INVALID BROWSER (-Dbrowser) SPECIFIED:");
                    invalidBrowserMessage.append(parameterValue);
                    invalidBrowserMessage.append(" VALID VALUES:");

                    BrowserType allBrowsers[] = BrowserType.values();
                    for (BrowserType validBrowser : allBrowsers) {
                        invalidBrowserMessage.append(validBrowser);
                        invalidBrowserMessage.append(" ");
                    }
                    System.out.println(invalidBrowserMessage.toString());
                }
                
                break;
            case "defaultImplicitWaitSeconds":
                try {
                    defaultImplicitWaitSeconds = Integer.parseInt(parameterValue);
                } catch (NumberFormatException nfx) {
                    throw new Exception("-DdefaultImplicitWaitSeconds:" + parameterValue+ " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)");
                }
                break;
            case "environment":
                try {
                    environment = EnvironmentType.valueOf(parameterValue);
                } catch (IllegalArgumentException ex) {
                    StringBuilder invalidEnvironmentMessage = new StringBuilder();

                    invalidEnvironmentMessage.append("INVALID ENVIRONMENT (-Denvironment) SPECIFIED:");
                    invalidEnvironmentMessage.append(parameterValue);
                    invalidEnvironmentMessage.append(" VALID VALUES:");

                    EnvironmentType allEnvironments[] = EnvironmentType.values();
                    for (EnvironmentType validEnvironment : allEnvironments) {
                        invalidEnvironmentMessage.append(validEnvironment);
                        invalidEnvironmentMessage.append(" ");
                    }
                    System.out.println(invalidEnvironmentMessage.toString());
                }                
                break;
            case "input":
                input=parameterValue;
                break;
            case "logging":
                logging=parameterValue;
                break;
            case "nogrid":
                nogrid=parameterValue;
                break;
            case "noImages":
                noImages=parameterValue;
                break;
            case "noScreenShots":
                noScreenShots=parameterValue;
                break;
            case "noScroll":
                noScroll=parameterValue;
                break;
            case "password":
                password=parameterValue;
                break;
            case "quickWaitMilliseconds":
                try {
                    hardCodedSleepMilliSeconds = Integer.parseInt(parameterValue);
                } catch (NumberFormatException nfx) {
                    throw new Exception("-DquickWaitMilliseconds:" + parameterValue+ " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)");
                }
                break;
            case "report":
                report=parameterValue;
                break;
            case "userid":
                userid=parameterValue;
                break;
            case "waitAfterPageLoadMilliSeconds":
                try {
                    waitAfterPageLoadMilliSeconds = Integer.parseInt(parameterValue);
                } catch (NumberFormatException nfx) {
                    throw new Exception("-DwaitAfterPageLoadMilliSeconds:" + parameterValue+ " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)");
                }
                break;
            case "throttleDownWaitTimeMilliSeconds":
                try {
                    throttleDownWaitTimeMilliSeconds = Integer.parseInt(parameterValue);
                } catch (NumberFormatException nfx) {
                    throw new Exception("-DthrottleDownWaitTimeMilliSeconds:" + parameterValue+ " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)");
                }
                break;
            case "waitForPageChangeMilliSeconds":
                try {
                    waitForPageChangeMilliSeconds = Integer.parseInt(parameterValue);
                } catch (NumberFormatException nfx) {
                    throw new Exception("-DwaitForPageChangeMilliSeconds:" + parameterValue+ " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)");
                }
                break;
            //FOR BOARDSCRUB.JAVA
            case "linkXpath":
                linkXpath=parameterValue;
                break;
            case "linksLoadedIndicatorXpath":
                linksLoadedIndicatorXpath=parameterValue;
                break;
            case "bodyTextXpath":
                bodyTextXpath=parameterValue;
                break;
            case "titleTextXpath":
                titleTextXpath=parameterValue;
                break;
            case "imageXpath":
                imageXpath=parameterValue;
                break;
            case "nextLinkXpath":
                nextLinkXpath=parameterValue;
                break;   
            case "startRESTParm":
                startRESTParm=parameterValue;
                break;
            case "numResultsRESTParm":
                numResultsRESTParm=parameterValue;
                break;
            default:
                System.out.println("INVALID PARAMETER STRING:"+parameter+" WITH VALUE:"+parameterValue);
                break;
        }
        
        System.out.println("-D"+parameter+"="+parameterValue);
    }
}
