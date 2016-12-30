#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -DdefaultImplicitWaitSeconds=60 -Dbrowser=CHROMELINUX64 -Dinput="http://tnaboard.com/" -DaNumber=0 -Dreport=verifylogostnaboard -Duserid=tnaboard -DaString="//p" -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid test
