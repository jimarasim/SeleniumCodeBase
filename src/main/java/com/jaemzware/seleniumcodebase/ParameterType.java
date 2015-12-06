/*
 * To change this license header=""; choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaemzware.seleniumcodebase;

import static com.jaemzware.seleniumcodebase.CodeBase.waitAfterPageLoadMilliSeconds;
import java.util.HashMap;
import java.util.Map;

public class ParameterType {
    public static String aHubport="4723";
    public static String aHubServer="localhost";
    public static String aNumber="0";
    public static String appiumApp="/Users/jameskarasim/Library/Developer/Xcode/DerivedData/Scratch-cdvmqpqxkymrtecctsbjrwupqtya/Build/Products/Debug-iphoneos/Scratch.app";
    public static String appiumIosDeviceName="iPhone 6"; //ijaemzware
    public static String appiumIosTargetVersion="9.1";
    public static String appiumUdid="88ff683cec637c3f1279386620b5397d48bc8341";
    public static String aString="//p";
    public static String bodyTextXpath=null;
    public static String browser="APPIUMSAFARISIMULATOR"; //APPIUMAPPDEVICE
    public static int defaultImplicitWait=10;
    public static String environment=null;
    public static String imageXpath=null;
    public static String input="https://starbucks.com";
    public static String linksLoadedIndicatorXpath=null;
    public static String linkXpath=null;
    public static String logging=null;
    public static String nextLinkXpath=null;
    public static String nogrid=null;
    public static String noImages=null;
    public static String noScreenShots=null;
    public static String noScroll=null;
    public static String password=null;
    public static String report="starbucksreport";
    public static String titleTextXpath=null;
    public static String userid="starbucks.com";
    public static int waitAfterPageLoadMilliSeconds=0;
    
    public static void SetParameter(String parameter, String parameterValue) throws Exception{
        switch (parameter){
            case "aHubport":
                aHubport=parameterValue;
                break;
            case "aHubServer":
                aHubServer=parameterValue;
                break;
            case "aNumber":
                aNumber=parameterValue;
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
            case "bodyTextXpath":
                bodyTextXpath=parameterValue;
                break;
            case "browser":
                browser=parameterValue;
                break;
            case "environment":
                environment=parameterValue;
                break;
            case "imageXpath":
                imageXpath=parameterValue;
                break;
            case "input":
                input=parameterValue;
                break;
            case "linksLoadedIndicatorXpath":
                linksLoadedIndicatorXpath=parameterValue;
                break;
            case "linkXpath":
                linkXpath=parameterValue;
                break;
            case "logging":
                logging=parameterValue;
                break;
            case "nextLinkXpath":
                nextLinkXpath=parameterValue;
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
            case "report":
                report=parameterValue;
                break;
            case "titleTextXpath":
                titleTextXpath=parameterValue;
                break;
            case "userid":
                userid=parameterValue;
                break;
            case "waitAfterPageLoadMilliSeconds":
                try {
                    waitAfterPageLoadMilliSeconds = Integer.parseInt(parameter);
                } catch (NumberFormatException nfx) {
                    throw new Exception("-DwaitAfterPageLoadMilliSeconds:" + parameterValue+ " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)");
                }
                break;
            case "defaultImplicitWait":
                try {
                    defaultImplicitWait = Integer.parseInt(parameter);
                } catch (NumberFormatException nfx) {
                    throw new Exception("-DdefaultImplicitWait:" + parameterValue+ " SPECIFIED IS NOT A PARSEABLE INT. RETURNING THIS STRING TO INDICATE FAILURE (MAY BE IGNORED BY SOME TESTS)");
                }
                break;
            default:
                System.out.println("INVALID PARAMETER STRING:"+parameter+" WITH VALUE:"+parameterValue);
                break;
        }
        
        System.out.println("-D"+parameter+"="+parameterValue);
    }
}
