/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaemzware.seleniumcodebase;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.net.URL;


public class StartAppium {

	private static IOSDriver<MobileElement> iosDriver;
	private static AppiumDriverLocalService service;

	@Before
	public static void setUp() throws Exception {
		service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
						.usingDriverExecutable(new File("/usr/local/bin/node")) //CLEAN INSTALL NODEJS FROM NODEJS.ORG
						.withAppiumJS(new File("/Users/jameskarasim/Downloads/installed/repositories/appium/bin/appium.js")) //CLONE APPIUM
                                                .withIPAddress("127.0.0.1").usingPort(4723));
		System.out.println("setUp - service.start");
                service.start();
                System.out.println("AppiumServiceStarted");
                
		DesiredCapabilities capabilities = DesiredCapabilities.iphone();
		capabilities.setCapability("platformName", "IOS");
		capabilities.setCapability("deviceName", "iPhone 6");
		capabilities.setCapability("platformVersion", "9.2");
		capabilities.setCapability("browserName", "Safari");
                
		System.out.println("setUp - new iosDriver");
		iosDriver = new IOSDriver<>(new URL("http://127.0.0.1:4723/"),capabilities);
                System.out.println("setUp - new iosDriver created");
	}

	@Test
	public void getAppiumStatus() {
            System.out.println("getAppiumStatus");
            iosDriver.get("https://google.com");
                
	}

	@After
	public static void tearDown() {
		iosDriver.quit();
		service.stop();
	}
}

