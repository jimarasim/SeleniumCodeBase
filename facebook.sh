#!/usr/bin/env bash

cd ../ && mvn -Dtest=Facebook#FaceCrawlAllLinks -Dnogrid -Dbrowser=CHROMEMAC -DaNumber=5 -DaString=//p -DdefaultImplicitWaitSeconds=120 -Dinput='https://www.facebook.com/james.arasim' test
#mvn -Dtest=Facebook#FaceCrawlAllLinks -Dnogrid -Dbrowser=CHROMEMAC -DaNumber=5 -DaString=//p -DdefaultImplicitWaitSeconds=120 -Dinput='https://www.facebook.com/angela.chandler.370' test


