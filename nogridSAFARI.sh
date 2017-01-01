#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -Dinput=http://jaemzware.com -Duserid=.com -DaString="//img" -Dbrowser=SAFARI -DaNumber=0 -Dreport=jaemzware -DwaitAfterPageLoadMilliSeconds=0 -Dnogrid -DnoScroll -Dlogging test
#-DINPUT MAPS TO STARTURL (eg http://jaemzware.com)
#-DUSERID MAPS TO BASEURL OF LINKS TO FOLLOW (eg grindr.com)
#-DASTRING IS XPATH OF PAGE OBJECTS TO COLLECT FROM EACH PAGE (eg xpath //a[contains(@href,'grindr.com')] and //img)
#-DBROWSER
#NOTE: VERSION AND PLATFORM ENUMERATION VARS ONLY USED BY GRID
#CHROME("chrome","",Platform.WINDOWS),
#SAFARI("safari","7",Platform.MAC),
#IE8("InternetExplorer","8",Platform.WINDOWS),
#IE9("InternetExplorer","9",Platform.WINDOWS),
#IE10("InternetExplorer","10",Platform.WINDOWS),
#IE11("InternetExplorer","11",Platform.WINDOWS),
