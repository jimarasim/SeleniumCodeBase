java -Dwebdriver.chrome.driver=chromedrivermac -jar selenium-server-standalone-2.47.1jar -role node -browser platform=MAC,browserName=chrome,maxInstances=5,seleniumProtocol=WebDriver,acceptSslCerts=true -hub http://localhost:4444/grid/register -port 5561