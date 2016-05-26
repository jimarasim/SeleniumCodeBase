#!/usr/bin/env bash
mvn -Dtest=JaemzwareSiteValidation -DdefaultImplicitWaitSeconds=30 -Dbrowser=CHROME -Dnogrid -DnoScreenShots test