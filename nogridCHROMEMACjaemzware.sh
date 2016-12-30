#!/usr/bin/env bash
# -Dinput page to start at
# -Duserid string contained in links of href to follow
# -DaString xpath of logo to verify
# -DaNumber maxiumum number of pages to visit. set to 0 for all
# -DdefaultImplicitWaitSeconds how long to wait while trying to locate elements
mvn -Dtest=Scratch -Dbrowser=CHROMEMAC -Dinput="http://jaemzware.com" -DaNumber=0 -Dreport=jaemzware -Duserid=".com" -DaString="//a" -DwaitAfterPageLoadMilliSeconds=0 -DdefaultImplicitWaitSeconds=60 -Dlogging -Dnogrid test
