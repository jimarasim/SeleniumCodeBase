#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -Dinput=http://nbc.com -Duserid=nbc.com -DaString="//a[contains(@href,'nbc.com')] and //img" -Dbrowser=FIREFOX  -DaNumber=0 -Dreport=nbc -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid -DnoScroll test
#INPUT MAPS TO STARTURL (eg http://grindr.com)
#USERID MAPS TO BASEURL OF LINKS TO FOLLOW (eg grindr.com)
#ASTRING IS XPATH OF PAGE OBJECTS TO COLLECT FROM EACH PAGE (eg xpath //a[contains(@href,'grindr.com')] and //img)
