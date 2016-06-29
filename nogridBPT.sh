#!/usr/bin/env bash
mvn -Dtest=JaemzwareSiteValidation -DwaitAfterPageLoadMilliSeconds=1000 -DdefaultImplicitWaitSeconds=60 -Dbrowser=FIREFOXMAC -Dnogrid test