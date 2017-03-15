#!/usr/bin/env bash
mvn -Dtest="JaemzwareIndexAppiumTest" -Dbrowser="APPIUMSAFARISIMULATOR" -DaHubPort="4723" -DaHubServer="localhost" -DappiumIosTargetVersion="8.2" -DappiumIosDeviceName="iPhone 6" test
