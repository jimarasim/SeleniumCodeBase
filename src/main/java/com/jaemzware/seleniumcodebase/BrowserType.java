package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.Platform;

/**
 *
 * @author jaemzware.org
 */
public enum BrowserType {
    //PLATFORM IS ONLY USED WHEN USING GRID. FOR NON-GRID, JUST SPECIFYING CHROME
    //FIREFOX ONLY WORKS WITH GECKODRIVER AND GRID NOW (TESTING AGAIN)
    //MAKE SURE THESE ARE ALL HANDLED IN THE GRID AND NON-GRID CASES
    CHROME("chrome","",Platform.WINDOWS),  //REQUIRES CHROMEDRIVER
    CHROMELINUX("chrome","",Platform.LINUX),
    CHROMELINUX32("chrome","",Platform.LINUX),
    CHROMEMAC ("chrome","",Platform.MAC),
    FIREFOX("firefox","",Platform.WINDOWS),
    FIREFOXLINUX("firefox","",Platform.LINUX),
    FIREFOXMAC("firefox","",Platform.MAC),
    SAFARI("safari","",Platform.MAC),  //REQUIRES SAFARI DRIVER
    IE8("InternetExplorer","8",Platform.WINDOWS), //REQUIRES IEDRIVERSERVER
    IE9("InternetExplorer","9",Platform.WINDOWS), 
    IE10("InternetExplorer","10",Platform.WINDOWS),
    IE11("InternetExplorer","11",Platform.WINDOWS),
    CHROMEIPHONE6("IPHONE","6",Platform.MAC),
    CHROMEIPAD4("IPAD","4",Platform.MAC),
    CHROMEANDROID402("ANDROID","4.0.2",Platform.WINDOWS),
    APPIUMSAFARISIMULATOR("","",Platform.MAC),
    APPIUMAPPSIMULATOR("","",Platform.MAC),
    APPIUMAPPDEVICE("","",Platform.MAC),
    APPIUMSAFARIDEVICE("","",Platform.MAC);
    
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
