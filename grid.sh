#!/usr/bin/env bash
#[SOURCE OF TRUTH FOR Scratch#VerifyLogos GRID]
mvn -Dbrowser=CHROMEMAC -Dinput=http://jaemzware.com/ -Dreport=VerifyLogosReport -DaNumber=0 -Dlogging -DwaitAfterPageLoadMilliSeconds=0 -Duserid=".com" -DaString="//a" -DaHubServer=localhost -DaHubPort=4444 -Dtest=Scratch#VerifyLogos test
#COMMAND LINE SWITCHES FOR Scratch#VerifyLogos
#-DaString IS XPATH OF PAGE OBJECTS TO COLLECT FROM EACH PAGE
#   eg if xpath //a OR //img
#   if //img, the report will show the images where src is a link to the image (does not download them)
#   if //a, the report will link the href
#-Dinput MAPS TO STARTURL (eg http://jaemzware.com)
#-Dreport appended to index in report name index___.htm
#-Duserid MAPS TO BASEURL OF LINKS TO FOLLOW (eg http OR .com OR jaemzware OR jaemzware.org)
#COMMAND LINE SWITCHES FOR CODEBASE
#-DaHubPort=4444 - port of selenium grid to use when -Dnogrid is absent
#-DaHubServer=localhost - server of selenium grid to use when -Dnogrid is absent
#-DaNumber - stop after visiting this many pages (default is 0 or negative, don't stop)
#-DdefaultImplicitWaitSeconds HOW LONG TO WAIT FOR ELEMENTS BEFORE TIMING OUT (DEFAULT 10 S)
#-Dlogging CHROME AND IE ONLY SHOWS BROWSER AND CLIENT ERRORS IF ANY. NOT SUPPORTED BY FIREFOX NOR SAFARI
#-Dnogrid LAUNCH LOCALLY, AND DON'T USE SELENIUM GRID
#-DnoImages - if DaString is //img, don't display images in the report
#-DnoScreenShots DONT TAKE SCREENSHOTS OF EACH PAGE. DEFAULT ON.
#-DnoScroll DOESN'T SCROLL THE SCREEN FOR VIEWING (DEFAULT ON)
#-DwaitAfterPageLoadMilliSeconds PAUSE EXECUTION AFTER PAGE LOAD AND EACH SCROLL (DEFAULT 0 MS)
#-Dbrowser
#NOTE: VERSION AND PLATFORM ENUMERATION VARS ONLY USED BY GRID
#NOTE: CHROMELINUX32 SPECIAL FOR RASPBERRY PI
#NOTE: FIREFOX IS NO LONGER SUPPORTED RUNNING LOCALLY AS OF WEBDRIVER 3.0
#NOTE: SAFARI MUST NOT ALREADY BE RUNNING WHEN RUNNING A SAFARI AUTOMATION
#NOTE: SAFARI MUST enable the 'Allow Remote Automation' option in Safari's Develop menu to control Safari via WebDriver
#NOTE: SAFARI IS FAST BUT DOESN'T WORK CONSISTENTLY LIKE CHROME, AND FIREFOX
#CHROME("chrome","",Platform.WINDOWS),
#CHROMELINUX("chrome","",Platform.LINUX),
#CHROMELINUX32("chrome","",Platform.LINUX),
#CHROMEMAC ("chrome","",Platform.MAC),
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