mvn -Dtest=Scratch#VerifyLogosAppium -DaNumber=0 -Dbrowser=APPIUMSAFARISIMULATOR -Dinput=https://seattlerules.com -Duserid=seattlerules.com -DaString=//a[@title='Seattle Rules'] -DaHubPort=4723 -DaHubServer=localhost -DappiumIosTargetVersion=8.3 -DappiumIosDeviceName="iPhone 6" test