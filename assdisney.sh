mvn -Dtest=Scratch#VerifyLogosAppium -DaNumber=0 -Dbrowser=APPIUMSAFARISIMULATOR -Dinput=http://disney.com -Duserid=disney.com -DaString=//*[@id='nav-logo'] -DaHubPort=4723 -DaHubServer=localhost -DappiumIosTargetVersion=8.4 -DappiumIosDeviceName="iPhone 6" -DdefaultImplicitWait=200 test