java -Dwebdriver.chrome.driver=chromedrivermac -jar selenium-server-standalone-2.45.0.jar -role node -browser platform=MAC,browserName=chrome,maxInstances=5,seleniumProtocol=WebDriver,acceptSslCerts=true -hub http://50.251.226.90:4445/grid/register -host 166.171.123.245 -port 5561