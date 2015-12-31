/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaemzware.seleniumcodebase;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.net.URL;
import org.junit.Assert;


public class StartAppium {

	private static IOSDriver<MobileElement> iosDriver;
	private static AppiumDriverLocalService service;

	@Before
	public static void setUp() throws Exception {
            if(StartAppiumService()){
                if(StartIosDriver()){
                    Assert.fail("COULD NOT GET IOS DRIVER");
                }
            }
            else{
                Assert.fail("COULD NOT START APPIUM SERVICE");
            }
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
        
        private static boolean StartAppiumService(){
            service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
						.usingDriverExecutable(new File("/usr/local/bin/node")) //CLEAN INSTALL NODEJS FROM NODEJS.ORG
						.withAppiumJS(new File("/Users/jarasim/Downloads/installed/repositories/appium/bin/appium.js")) //CLONE APPIUM
                                                .withIPAddress("0.0.0.0").usingPort(4723));
            System.out.println("setUp - service.start");
            try{
                service.start();
            }
            catch(AppiumServerHasNotBeenStartedLocallyException ashnbslex){
                ashnbslex.printStackTrace();
                System.out.println("COULD NOT START THE APPIUM SERVICE:"+ashnbslex.getMessage());
                return false;
            }
            System.out.println("AppiumServiceStarted");
            return true;
        }
        
        private static boolean StartIosDriver(){
            DesiredCapabilities capabilities = DesiredCapabilities.iphone();
            capabilities.setCapability("address", "0.0.0.0");
            capabilities.setCapability("port", "4723");
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iJarasim5sBlack");
            capabilities.setCapability("platformVersion", "9.2");
            capabilities.setCapability("ipa", "/Users/jarasim/Downloads/StarbucksUITest.ipa"); //app is available as well
            capabilities.setCapability("udid","2bd3fe26e8b2637512e5a7b7850ea72dea42302f");

            //appium -U 2bd3fe26e8b2637512e5a7b7850ea72dea42302f --app /Users/jarasim/Downloads/StarbucksUITest.ipa

            System.out.println("setUp - new iosDriver");
            try{
                iosDriver = new IOSDriver<>(new URL("http://0.0.0.0:4723/"),capabilities);
            }
            catch(Exception ex){
                ex.printStackTrace();
                System.out.println("COULD NOT GET AN IOS DRIVER");
                return false;
            }
            System.out.println("setUp - new iosDriver created");
            return true;
        }
}

