#!/usr/bin/env bash
java -jar selenium-server-standalone-3.0.1.jar -role node -browser platform=MAC,browserName=safari,maxInstances=5,version=10,seleniumProtocol=WebDriver,acceptSslCerts=true -hub http://192.168.99.100:4444/grid/register -port 5553
