#!/usr/bin/env bash
# -Dinput page to start at
# -Duserid string contained in links of href to follow
# -DaString xpath of logo to verify
# -DaNumber maxiumum number of pages to visit. set to 0 for all
# -DdefaultImplicitWaitSeconds how long to wait while trying to locate elements
mvn -Dtest=Scratch#VerifyLogos -Dbrowser=CHROME -Dinput="http://brownpapertickets.com" -DaNumber=3 -Dreport=verifylogosBPT -Duserid=brownpapertickets -DaString="/html/body/div[1]/table[1]//img" -DwaitAfterPageLoadMilliSeconds=0 -DdefaultImplicitWaitSeconds=60 -Dlogging -Dnogrid test