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
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;


public class StartAppium {

	private static IOSDriver<MobileElement> iosDriver;
	private static AppiumDriverLocalService service;

	@BeforeClass
	public static void setUp() throws Exception {

		service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
						.usingDriverExecutable(new File("/usr/local/bin/node")) //CLEAN INSTALL NODEJS FROM NODEJS.ORG
						.withAppiumJS(
								new File(
										"/Users/jameskarasim/Downloads/installed/repositories/appium/bin/appium.js")) //CLONE APPIUM
						.withIPAddress("127.0.0.1").usingPort(4723));

		service.start();

		DesiredCapabilities capabilities = DesiredCapabilities.iphone();
		capabilities.setCapability("platformName", "IOS");
		capabilities.setCapability("deviceName", "iPhone Simulator");
		capabilities.setCapability("platformVersion", "9.2");
		capabilities.setCapability("browserName", "Safari");

		iosDriver = new IOSDriver<>(service.getUrl(), capabilities);
		iosDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void getAppiumStatus() {
            System.out.println("test");
//		iosDriver.navigate().to("http://www.baidu.com");
//		iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		iosDriver.findElementByName("wd").sendKeys("appium desired capability");
//		iosDriver.findElement(By.name("wd")).clear();
	}

	@AfterClass
	public static void tearDown() {
		iosDriver.quit();
		service.stop();
	}
}

