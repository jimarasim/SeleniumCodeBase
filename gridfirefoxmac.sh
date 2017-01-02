#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -DaHubServer=localhost -DaHubPort=4444 -Dbrowser=FIREFOXMAC -Dinput=http://jaemzware.com/ -DaNumber=0 -Dreport=jaemzware -Duserid=.com -DaString="//a" -DwaitAfterPageLoadMilliSeconds=0 test
#COMMAND LINE SWITCHES FOR VERIFYLOGOS
#-DINPUT MAPS TO STARTURL (eg http://jaemzware.com)
#-DUSERID MAPS TO BASEURL OF LINKS TO FOLLOW (eg grindr.com)
#-DASTRING IS XPATH OF PAGE OBJECTS TO COLLECT FROM EACH PAGE
#   eg xpath //a and //img
#   if //img, the report will show the images where src is a link to the image (does not download them)
#   if //a, the report will link the href
#-Dlogging CHROME AND IE ONLY SHOWS BROWSER AND CLIENT ERRORS IF ANY
#-DnoScroll DOESN'T SCROLL THE SCREEN FOR VIEWING (DEFAULT ON)
#-DaNumber - stop after visiting this many pages (default is 0, don't stop)
#-Dreport appended to index in report name index___.htm
#-DwaitAfterPageLoadMilliSeconds PAUSE EXECUTION AFTER PAGE LOAD AND EACH SCROLL (DEFAULT 0 MS)
#-DdefaultImplicitWaitSeconds HOW LONG TO WAIT FOR ELEMENTS BEFORE TIMING OUT (DEFAULT 10 S)
#-DnoScreenShots DONT TAKE SCREENSHOTS OF EACH PAGE. DEFAULT ON.
#-DnoImages - if DaString is //img, don't display images in the report
#-DBROWSER
#NOTE: VERSION AND PLATFORM ENUMERATION VARS ONLY USED BY GRID
#NOTE: CHROMELINUX32 SPECIAL FOR RASPBERRY PI
#NOTE: FIREFOX IS NO LONGER SUPPORTED RUNNING LOCALLY AS OF WEBDRIVER 3.0
#NOTE: SAFARI MUST NOT ALREADY BE RUNNING WHEN RUNNING A SAFARI AUTOMATION
#NOTE: SAFARI MUST enable the 'Allow Remote Automation' option in Safari's Develop menu to control Safari via WebDriver
#CHROME("chrome","",Platform.WINDOWS),
#CHROMELINUX("chrome","",Platform.LINUX),
#CHROMELINUX32("chrome","",Platform.LINUX),
#CHROMEMAC ("chrome","",Platform.MAC),
#FIREFOX("firefox","",Platform.WINDOWS),
#FIREFOXLINUX("firefox","",Platform.LINUX),
#FIREFOXLINUXBPT("firefox","",Platform.LINUX),
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