#!/usr/bin/env bash
mvn -Dtest="VerifyLogosAppium#VerifyLogosAppium" -DaNumber="0" -Dbrowser="APPIUMSAFARISIMULATOR" -Dinput="https://seattlerules.com" -Duserid="seattlerules.com" -DaString="//p" -DaHubPort="4723" -DaHubServer="localhost" -DappiumIosTargetVersion="9.3" -DappiumIosDeviceName="iPhone 6" test
