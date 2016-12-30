#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -Dinput=http://jaemzware.com -Duserid=.com -DaString="//img" -Dbrowser=CHROME  -DaNumber=0 -Dreport=jaemzware -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid -DnoScroll test
#INPUT MAPS TO STARTURL (eg http://jaemzware.com)
#USERID MAPS TO BASEURL OF LINKS TO FOLLOW (eg grindr.com)
#ASTRING IS XPATH OF PAGE OBJECTS TO COLLECT FROM EACH PAGE (eg xpath //a[contains(@href,'grindr.com')] and //img)
