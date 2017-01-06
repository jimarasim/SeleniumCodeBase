#!/usr/bin/env bash
#[SOURCE OF TRUTH FOR Scratch#VerifyLogos GRID]
mvn -Dbrowser=CHROMEMAC -Dinput=http://jaemzware.com/ -Dreport=VerifyLogosReport -Dlogging -Duserid=".com" -DaString="//a" -DaHubServer=localhost -DaHubPort=4444 -Dtest=Scratch#VerifyLogos test
#=====================================
#COMMAND LINE SWITCHES FOR Scratch#VerifyLogos
#-DaString IS XPATH OF PAGE OBJECTS TO COLLECT FROM EACH PAGE
#   eg if xpath //a OR //img
#   if //img, the report will show the images where src is a link to the image (does not download them)
#   if //a, the report will link the href
#-Dinput MAPS TO STARTURL (eg http://jaemzware.com)
#-Dreport appended to index in report name index___.htm
#-Duserid MAPS TO BASEURL OF LINKS TO FOLLOW (eg http OR .com OR jaemzware OR jaemzware.org)
#COMMAND LINE SWITCHES FOR VERIFYLOGOS
#=====================================
#CODEBASE HUB SWITCHES
#-DaHubServer="localhost"; //where to look for selenium grid. server name only. default behavior is to look for one.
#-DaHubPort="4444"; //port to use for selenium grid, if looking for one.
#-Dnogrid=null; //dont use selenium grid. default behavior is to look for grid on aHubPort aHubServer
#=====================================
#CODEBASE AND TEST TIME SWITCHES
#-DdefaultImplicitWaitSeconds=15; //implicit wait time for finding elements on a page, where the page being loaded is a factor (selenium)
#-DhardCodedSleepMilliSeconds=5000; //facebookcrawlalllinks is the only test that uses this
#-DthrottleDownWaitTimeMilliSeconds=500; //how long to wait when throttling down to look for elements after a page is known to be loaded (boardscrub craigslist codebase iselementpresent default)
#-DwaitAfterPageLoadMilliSeconds=0; //how long to wait for thread.sleep OR after a page loads from a link click (see protected String driverGetWithTime)
#-DwaitForPageChangeMilliSeconds=60000; //how long to wait for a page to change from an old url to a new one (see protected void WaitForPageChange)
#=====================================
#OTHER TEST SWITCHES
#-DaNumber=0; //verifylogos and boardscrub use it to signify how many links to visit. =<0 == visit all
#-DaString=null; //verifylogos uses it to signify what element to look for as its "logo"
#-Dinput=null; //verifylogos and boardscrub use it specify the first page to load
#-DnoImages=null; //boardscrub uses this to omit images found in its report.
#-Dpassword=null; //usage depends on test; virtually no known usage
#-Dreport=null; //verifylogos and boardscrub use this to name their reports: index{report}.htm
#-Duserid=null;//usage depends on test; virtually no known usage
#=====================================
#OTHER CODEBASE SWITCHES
#-Dlogging=null; //turn on logging to report browser errors, currently used only with CHROME
#-DnoScreenShots=null; //codebase drivergetwithtime uses this to decide whether to save screenshots
#-DnoScroll=null; //codebase ScrollPage() will refrain from scrolling if this is specified
#=====================================
#BROWSER ENUMERATIONS
#-Dbrowser
#NOTE: VERSION AND PLATFORM ENUMERATION VARS ONLY USED BY GRID
#NOTE: CHROMELINUX32 SPECIAL FOR RASPBERRY PI
#NOTE: FIREFOX NO LONGER SUPPORTED FOR LOGGING
#NOTE: FIREFOX SCREENSHOTS SHOW THE WHOLE PAGE
#NOTE: SAFARI NO LONGER SUPPORTED FOR LOGGING
#NOTE: SAFARI MUST NOT ALREADY BE RUNNING WHEN RUNNING A SAFARI AUTOMATION
#NOTE: SAFARI MUST enable the 'Allow Remote Automation' option in Safari's Develop menu to control Safari via WebDriver
#NOTE: SAFARI IS FAST BUT DOESN'T WORK CONSISTENTLY LIKE CHROME, AND FIREFOX; IE REQUIRES WORKAROUND FOR ISDISPLAYED
#CHROME("chrome","",Platform.WINDOWS),
#CHROMELINUX("chrome","",Platform.LINUX),
#CHROMELINUX32("chrome","",Platform.LINUX),
#CHROMEMAC ("chrome","",Platform.MAC),
#FIREFOX("firefox","",Platform.WINDOWS),
#FIREFOXLINUX("firefox","",Platform.LINUX),
#FIREFOXMAC("firefox","",Platform.MAC),
#SAFARI("safari","10",Platform.MAC),
#IE8("InternetExplorer","8",Platform.WINDOWS),
#IE9("InternetExplorer","9",Platform.WINDOWS),
#IE10("InternetExplorer","10",Platform.WINDOWS),
#IE11("InternetExplorer","11",Platform.WINDOWS),
#CHROMEIPHONE6("IPHONE","6",Platform.MAC),
#CHROMEIPAD4("IPAD","4",Platform.MAC),
#CHROMEANDROID402("ANDROID","4.0.2",Platform.WINDOWS),
#APPIUMSAFARISIMULATOR("","",Platform.MAC),
#APPIUMAPPSIMULATOR("","",Platform.MAC),
#APPIUMAPPDEVICE("","",Platform.MAC),
#APPIUMSAFARIDEVICE("","",Platform.MAC);