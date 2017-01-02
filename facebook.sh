#!/usr/bin/env bash
mvn -Dtest=Facebook#FaceCrawlAllLinks -Dnogrid -Dbrowser=CHROME -DaNumber=3 -Dreport=facebook -DaString=//p -DdefaultImplicitWaitSeconds=5 -Dinput='https://www.facebook.com' test



