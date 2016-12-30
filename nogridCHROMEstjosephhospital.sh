#hey angela, check this shit out. powered by ladies of the night.
#!/usr/bin/env bash
mvn -Dtest=Scratch#VerifyLogos -DdefaultImplicitWaitSeconds=60 -Dbrowser=CHROME -Dinput="https://www.chifranciscan.org/" -DaNumber=0 -Dreport=verifylogostjoseph -Duserid=chifranciscan -DaString="//p" -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid test
