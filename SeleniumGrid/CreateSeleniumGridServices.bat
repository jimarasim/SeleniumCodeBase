REM https://code.google.com/p/simple-service-wrapper/
REM Note: SELENIUMIE9 and SELENIUMSAFARI services must be subsequently specified to use a users credentials (specifically, the user that installed safari)

sc.exe create SELENIUMHUB obj= "olympus\itqaautotest01" password= "x@@786Z5p" binPath= "C:\SeleniumGrid\service.exe \"java -jar C:\SeleniumGrid\selenium-server-standalone-2.40.0.jar -role hub\"" type= own start= auto error= ignore DisplayName= SELENIUMHUB
sc.exe create SELENIUMCHROME obj= "olympus\itqaautotest01" password= "x@@786Z5p" binPath= "C:\SeleniumGrid\service.exe \"java -Dwebdriver.chrome.driver=C:\SeleniumGrid\chromedriver.exe -jar C:\SeleniumGrid\selenium-server-standalone-2.40.0.jar -role node -browser browserName=chrome,maxInstances=3 -hub http://SEAOTMAPP02.olympus.F5NET.com:4444/grid/register -port 5559\"" type= own start= auto error= ignore DisplayName= SELENIUMCHROME
sc.exe create SELENIUMIE9 obj= "olympus\itqaautotest01" password= "x@@786Z5p" binPath= "C:\SeleniumGrid\service.exe \"java -Dwebdriver.ie.driver=C:\SeleniumGrid\IEDriverServer.exe -jar C:\SeleniumGrid\selenium-server-standalone-2.40.0.jar -role node -browser browserName=InternetExplorer,version=9,maxInstances=3 -hub http://SEAOTMAPP02.olympus.F5NET.com:4444/grid/register -port 5555\"" type= own start= auto error= ignore DisplayName= SELENIUMIE9
sc.exe create SELENIUMSAFARI obj= "olympus\itqaautotest01" password= "x@@786Z5p" binPath= "C:\SeleniumGrid\service.exe \"java -jar C:\SeleniumGrid\selenium-server-standalone-2.40.0.jar -role node -browser platform=VISTA,browserName=safari,maxInstances=3,version=5 -hub http://SEAOTMAPP02.olympus.F5NET.com:4444/grid/register -port 5551\"" type= own start= auto error= ignore DisplayName= SELENIUMSAFARI
sc.exe create SELENIUMFIREFOX obj= "olympus\itqaautotest01" password= "x@@786Z5p" binPath= "C:\SeleniumGrid\service.exe \"java -jar C:\SeleniumGrid\selenium-server-standalone-2.40.0.jar -role node -browser browserName=firefox,maxInstances=3 -hub http://SEAOTMAPP02.olympus.F5NET.com:4444/grid/register -port 5558\"" type= own start= auto error= ignore DisplayName= SELENIUMFIREFOX
