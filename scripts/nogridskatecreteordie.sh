#!/usr/bin/env bash
mvn -Dtest=SkateCreteOrDieTest -DdefaultImplicitWaitSeconds=30 -Dbrowser=CHROME -Dnogrid -DnoScreenShots test