java -Dwebdriver.chrome.driver=chromedriverlinux64 -jar selenium-server-standalone-2.44.0.jar -role node -browser platform=LINUX,browserName=chrome,maxInstances=5,seleniumProtocol=WebDriver,acceptSslCerts=true -hub http://10.1.10.156:4445/grid/register -port 5561