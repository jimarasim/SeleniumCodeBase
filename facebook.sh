#!/usr/bin/env bash
mvn -Dtest=Facebook#FaceCrawlAllLinks -Dnogrid -Dbrowser=CHROMEMAC -DaNumber=5 -DaString=//p -DdefaultImplicitWaitSeconds=120 -Dinput='https://www.facebook.com/Michael.Hunt' test


