#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -DdefaultImplicitWaitSeconds=60 -Dbrowser=CHROMELINUX -Dinput="http://brownpapertickets.com/" -DaNumber=0 -Dreport=verifylogosgrindline -Duserid=brownpapertickets -DaString="//p" -DwaitAfterPageLoadMilliSeconds=1000 -Dlogging -Dnogrid test