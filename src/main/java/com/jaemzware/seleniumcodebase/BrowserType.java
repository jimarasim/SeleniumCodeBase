/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.Platform;

/**
 *
 * @author arasim
 */

////////////////////////////////////////////////////////////////////////////////////////////////////
//SELENIUM GRID (http://docs.seleniumhq.org/download/) NOTES
//https://itsvn.f5net.com/svn/qa_tools/Documents/ EXAMPLES
//https://docs.f5net.com/display/ITQA/Selenium+Grid DOCUMENTATION
////////////////////////////////////////////////////////////////////////////////////////////////////


public enum BrowserType {
    CHROME("chrome","",Platform.WINDOWS), 
    CHROMELINUX("chrome","",Platform.LINUX),
    FIREFOX("firefox","",Platform.WINDOWS),    
    SAFARI("safari","7",Platform.MAC),
    IE8("InternetExplorer","8",Platform.WINDOWS), 
    IE9("InternetExplorer","9",Platform.WINDOWS), 
    IE10("InternetExplorer","10",Platform.WINDOWS),
    IE11("InternetExplorer","11",Platform.WINDOWS),
    IPHONE6("IPHONE","6",Platform.WINDOWS),
    IPAD4("IPAD","4",Platform.WINDOWS),
    ANDROID402("ANDROID","4.0.2",Platform.WINDOWS),
    APPIUM("","",Platform.MAC);
    
    public final String browserName;
    public final String version;
    public final Platform platform;
    
    BrowserType(final String browserName, final String version, final Platform platform)
    {
        this.browserName = browserName;
        this.version = version;
        this.platform = platform;
    }

}
