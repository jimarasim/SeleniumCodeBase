java -jar selenium-server-standalone-2.47.1.jar -role node -browser platform=MAC,browserName=safari,maxInstances=5,version=7,seleniumProtocol=WebDriver,acceptSslCerts=true -hub http://10.1.10.156:4445/grid/register -port 5553 -host 10.1.10.155