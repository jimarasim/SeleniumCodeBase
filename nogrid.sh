#VERIFYLOGOS-REQUIRED PARMETERS:
#-Dtest
#-Dbrowser
#-Dinput        web page to start with, to gather links from (e.g. http://seattlerules.com)
#-Duserid       baseurl of pages to follow, found on -Dinput (e.g. seattlerules.com OR newspage.htm)
#-DaString      xpath of logo to check presence of (e.g //img[@id='companylogo'])
#VERIFYLOGOS-OPTIONAL PARAMETERS:
#[TODO REFERENCE BOARD] -DaNumber maximum of pages to cycle through while paging (e.g. 3). 0 (DEFAULT) == ALL
#-Dlogging       log browser and other warnings (takes longer) UNSPECIFIED == NO LOGGING
#-Dnogrid        launch browser locally, and not through selenium grid. if not specified, will try selenium grid first
#-DaHubPort      port to use for selenium grid server, when not 4444, like 4723 for appium
#-DaHubServer    port to use for selenium grid server or appium, when not localhost
#-DwaitAfterPageLoadMilliSeconds   time to wait after a page has loaded.
#-DappiumApp     path to ios app ipa file, if using appium to test a native app
#-DappiumUdid    path to ios device udid, if using appium ot test a device instead of a simulator
#-DappiumIosTargetVersion    version of ios to use
#-DappiumIosDeviceName  name of  of ios to use



mvn -Dtest=Scratch#VerifyLogos -Dbrowser=CHROME -Dinput=http://disney.com/ -DaNumber=0 -Duserid=disney.com -DaString="//*[contains(@id,'logo')]" -DwaitAfterPageLoadMilliSeconds=10000 -Dlogging -Dnogrid test