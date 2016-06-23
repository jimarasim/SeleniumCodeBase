#!/usr/bin/env bash
# -Dinput page to start at
# -Duserid string contained in links of href to follow
# -DaString xpath of logo to verify
mvn -Dtest=Scratch#VerifyLogos -Dbrowser=CHROME -Dinput="http://brownpapertickets.com" -DaNumber=3 -Dreport=verifylogosBPT -Duserid=brownpapertickets -DaString="/html/body/div[1]/table[1]//img" -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid test