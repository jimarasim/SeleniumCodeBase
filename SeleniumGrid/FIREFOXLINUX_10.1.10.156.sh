java -jar selenium-server-standalone-2.47.1.jar -role node -browser platform=LINUX,browserName=firefox,maxInstances=5,seleniumProtocol=WebDriver,acceptSslCerts=true -hub http://10.1.10.156:4445/grid/register -port 5558
