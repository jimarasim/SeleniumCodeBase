#!/usr/bin/env bash
mvn -Dtest=JaemzwareSiteValidation#SkatecreteordieUsingPageObject -DdefaultImplicitWaitSeconds=30 -Dbrowser=CHROME -Dnogrid -DnoScreenShots test