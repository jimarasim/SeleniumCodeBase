# -Dinput page to start at
# -Duserid link href to follow
# -DaString xpath of logo to verify
mvn -Dtest=Scratch#VerifyLogos -DaHubServer=localhost -DaHubPort=4444 -Dbrowser=CHROME -Dinput="http://grindline.com" -DaNumber=0 -Dreport=verifylogosgrindline -Duserid=grindline -DaString="//div[@id='parkInfo]" -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid test