mvn -Dtest=Scratch#VerifyLogosAppium -DaNumber=3 -Dbrowser=APPIUMSAFARISIMULATOR -Dinput=https://starbucks.com -Duserid=starbucks -DaString=//p -DappiumIosTargetVersion=9.2 -DappiumIosDeviceName="iPhone 6" -Dreport=starbucks -Dtest=VerifyLogosAppium clean install test
