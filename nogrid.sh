mvn -Dtest=Scratch#VerifyLogos -Dinput=http://grindr.com -Duserid=grindr.com -DaString="//a[contains(@href,'grindr.com')] and //img" -Dbrowser=CHROME  -DaNumber=0 -Dreport=grindr -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid -DnoScroll -DnoScreenShots test
#INPUT MAPS TO STARTURL (eg http://grindr.com)
#USERID MAPS TO BASEURL OF LINKS TO FOLLOW (eg grindr.com)
#ASTRING IS XPATH OF PAGE OBJECTS TO COLLECT FROM EACH PAGE (eg xpath //a[contains(@href,'grindr.com')] and //img)