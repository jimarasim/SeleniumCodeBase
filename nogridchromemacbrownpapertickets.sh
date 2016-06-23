# -Dinput page to start at
# -Duserid string contained in links of href to follow
# -DaString xpath of logo to verify
mvn -Dtest=Scratch#VerifyLogos -DaHubServer=localhost -DaHubPort=4444 -Dbrowser=CHROME -Dinput="http://brownpapertickets.com" -DaNumber=0 -Dreport=verifylogosBPT -Duserid=brownpapertickets -DaString="/html/body/div[1]/table[1]/tbody/tr[2]/td/table/tbody/tr[1]/td[1]/a/img" -DwaitAfterPageLoadMilliSeconds=0 -Dlogging -Dnogrid test