#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -Dinput=http://jaemzware.com -Duserid=http -DaString="//a" -Dbrowser=SAFARI -DaNumber=0 -Dreport=jaemzware -DwaitAfterPageLoadMilliSeconds=0 -Dnogrid -DnoScroll test
#COMMAND LINE SWITCHES FOR VERIFYLOGOS
#-DINPUT MAPS TO STARTURL (eg http://jaemzware.com)
#-DUSERID MAPS TO BASEURL OF LINKS TO FOLLOW (eg grindr.com)
#-DASTRING IS XPATH OF PAGE OBJECTS TO COLLECT FROM EACH PAGE (eg xpath //a[contains(@href,'grindr.com')] and //img)
#-Dlogging CHROME AND IE ONLY SHOWS BROWSER ERRORS IF ANY
#-DnoScroll DOESN'T SCROLL THE SCREEN FOR VIEWING (DEFAULT ON)
#-DaNumber
#-Dreport
#-DwaitAfterPageLoadMilliSeconds PAUSE EXECUTION AFTER PAGE LOAD AND EACH SCROLL (DEFAULT 0 MS)
#-DdefaultImplicitWaitSeconds HOW LONG TO WAIT FOR ELEMENTS BEFORE TIMING OUT (DEFAULT 10 S)
#-DnoScreenShots DONT TAKE SCREENSHOTS OF EACH PAGE. DEFAULT ON.
#-DnoImages
#-DBROWSER
#NOTE: VERSION AND PLATFORM ENUMERATION VARS ONLY USED BY GRID
#NOTE: CHROMELINUX32 SPECIAL FOR RASPBERRY PI
#NOTE: FIREFOX IS NO LONGER SUPPORTED RUNNING LOCALLY AS OF WEBDRIVER 3.0
#NOTE: SAFARI MUST NOT ALREADY BE RUNNING WHEN RUNNING A SAFARI AUTOMATION
#NOTE: SAFARI MUST enable the 'Allow Remote Automation' option in Safari's Develop menu to control Safari via WebDriver
#CHROME("chrome","",Platform.WINDOWS),
#CHROMELINUX32("chrome","",Platform.LINUX),
#SAFARI("safari","7",Platform.MAC),
#IE8("InternetExplorer","8",Platform.WINDOWS),
#IE9("InternetExplorer","9",Platform.WINDOWS),
#IE10("InternetExplorer","10",Platform.WINDOWS),
#IE11("InternetExplorer","11",Platform.WINDOWS),
