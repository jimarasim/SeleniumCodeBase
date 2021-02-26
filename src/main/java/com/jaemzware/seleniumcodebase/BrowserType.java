package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.Platform;

/**
 *
 * @author jaemzware.org
 */
public enum BrowserType {
    //GRID: PLATFORM AND VERSION ARE ONLY USED WHEN USING GRID OR A CHROME EMULATION
    //NON-GRID: FOR NON-GRID, JUST SPECIFYING CHROME, FIREFOX, OR SAFARI WORKS ON MAC
    //FIREFOX: ONLY WORKS WITH GECKODRIVER NOW
    CHROME("chrome","",Platform.WINDOWS),  //REQUIRES CHROMEDRIVER
    CHROMELINUX("chrome","",Platform.LINUX),
    CHROMELINUX32("chrome","",Platform.LINUX),
    CHROMEMAC ("chrome","",Platform.MAC),
    HTMLUNITMAC ("htmlunit","",Platform.MAC),
    FIREFOX("firefox","",Platform.WINDOWS), //REQUIRES GECKODRIVER
    FIREFOXLINUX("firefox","",Platform.LINUX),
    FIREFOXMAC("firefox","",Platform.MAC),
    //APPIUM MUST BE RUNNING AS THE SELENIUM GRID FOR THESE
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
