#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -DdefaultImplicitWaitSeconds=60 -Dbrowser=CHROMELINUX -Dinput="http://denmark.usembassy.gov/" -DaNumber=0 -Dreport=verifylogosgrindline -Duserid=usembassy -DaString="//img" -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid test