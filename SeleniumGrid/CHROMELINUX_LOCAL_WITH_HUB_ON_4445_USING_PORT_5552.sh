java -Dwebdriver.chrome.driver=chromedriverlinux64 -jar selenium-server-standalone-3.0.1.jar -role node -browser platform=LINUX,browserName=chrome,maxInstances=5,seleniumProtocol=WebDriver,acceptSslCerts=true -hub http://localhost:4445/grid/register -port 5562
