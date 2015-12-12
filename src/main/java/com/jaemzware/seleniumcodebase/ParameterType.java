/*
 * To change this license header=""; choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaemzware.seleniumcodebase;

public class ParameterType {
    public static String aHubPort="4723";
    public static String aHubServer="localhost";
    public static int aNumber=0;
    public static String appiumApp="/Users/jameskarasim/Library/Developer/Xcode/DerivedData/Scratch-cdvmqpqxkymrtecctsbjrwupqtya/Build/Products/Debug-iphoneos/Scratch.app";
    public static String appiumIosDeviceName="iPhone 6"; //ijaemzware
    public static String appiumIosTargetVersion="9.1";
    public static String appiumUdid="88ff683cec637c3f1279386620b5397d48bc8341";
    public static String aString="//p";
    public static BrowserType browser=BrowserType.CHROMEMAC; //APPIUMAPPDEVICE
    public static int defaultImplicitWaitSeconds=30;
    public static EnvironmentType environment=null;
    public static String input="https://starbucks.com";
    public static String logging=null;
    public static String nogrid=null;
    public static String noImages=null;
    public static String noScreenShots=null;
    public static String noScroll=null;
    public static String password=null;
    public static String report=null;
    public static String userid="starbucks.com";
    public static String bodyTextXpath=null;
    public static int waitAfterPageLoadMilliSeconds=0;   
    public static int quickWaitMilliSeconds=5000;    
    public static final String jenkinsReportPath = "http://computer.local:8080/job/verifylogosappium/ws/";
    public static final String jenkinsReportPathInternal = "http://localhost:8080/job/verifylogosappium/ws/";
    public static final String jenkinsDeployDirectory = "job/verifylogos/ws/";
    
    //BOARDSCRUB ONLY USES THESE
    public static String contactButtonJQueryExcecute =null;
    public static String contactButtonXpath =null;
    public static String contactInfoAnchorXpaths =null;
    public static String contactInfoLiXpaths =null;
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
                    quickWaitMilliSeconds = Integer.parseInt(parameterValue);
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
