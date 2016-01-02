/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jaemzware.seleniumcodebase;

import static com.jaemzware.seleniumcodebase.CodeBase.iosDriver;
import static com.jaemzware.seleniumcodebase.ParameterType.appiumIosDeviceName;
import static com.jaemzware.seleniumcodebase.ParameterType.appiumIosTargetVersion;
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
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class StartAppium {

	private static IOSDriver<MobileElement> iosDriver=null;
        private static IOSDriver<WebElement> iosWebDriver=null;

	private static AppiumDriverLocalService service=null;
        private static final String ipaddress="127.0.0.1";

	@Before
	public void setUp() throws Exception {
            if(StartAppiumService()){
//                if(StartIosDriver()){
                if(StartIosDriver()){
                    //RUN TESTS!
                }else{
                    Assert.fail("COULD NOT GET IOS DRIVER");
                }
            }
            else{
                Assert.fail("COULD NOT START APPIUM SERVICE");
            }
	}

	@Test
	public void PrintElements() {
            try{
                //this output elements in the app, but only saw it work once
                List<MobileElement> elements = iosDriver.findElements(By.xpath("//*"));
                elements.stream().forEach((mobileElement) -> {
                    System.out.println("TAG:"+mobileElement.getTagName()+" TEXT:"+mobileElement.getText());
                });
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }   
	}

	@After
	public void tearDown() {
		if(iosDriver!=null){
                    iosDriver.quit();
                    iosDriver=null;
                }
		if(service.isRunning()){
                    service.stop();
                }
                if(service!=null){
                    service=null;
                }
	}
        
        private static boolean StartAppiumService(){
            service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
						.usingDriverExecutable(new File("/usr/local/bin/node")) //CLEAN INSTALL NODEJS FROM NODEJS.ORG
						.withAppiumJS(new File("/Users/jarasim/Downloads/installed/repositories/appium/bin/appium.js")) //CLONE APPIUM
                                                .withIPAddress(ipaddress).usingPort(4723));
            System.out.println("setUp - service.start");
            try{
                service.start();
            }
            catch(AppiumServerHasNotBeenStartedLocallyException ashnbslex){
                System.out.println("COULD NOT START THE APPIUM SERVICE:"+ashnbslex.getMessage());
                return false;
            }
            System.out.println("AppiumServiceStarted");
            return true;
        }
        
        private static boolean StartIosDriver(){
            DesiredCapabilities capabilities = DesiredCapabilities.iphone();
            capabilities.setCapability("robot-address", ipaddress);
            capabilities.setCapability("port", "4723");
            capabilities.setCapability("platform-name", "iOS");
            capabilities.setCapability("deviceName", "iPhone 6");
            capabilities.setCapability("platform-version", "9.2");
            capabilities.setCapability("fullReset", "true");
            capabilities.setCapability("app", "/Users/jarasim/Downloads/StarbucksUITest.ipa"); //app is available as well


            System.out.println("setUp - new iosDriver");
            try{
                iosDriver = new IOSDriver<>(service.getUrl(),capabilities);
            }
            catch(Exception ex){
                System.out.println("COULD NOT GET AN IOS DRIVER. EXCEPTION:"+ex.getMessage());
                return false;
            }
            
            System.out.println("setUp - new iosDriver created");
            
            if(iosDriver==null){
                System.out.println("IOSDRIVER NULL AFTER CREATION");
                return false;
            }
            
            return true;
        }
        
        private static boolean StartIosWebDriver(){
            DesiredCapabilities capabilities = DesiredCapabilities.iphone();
            capabilities.setCapability("robot-address", ipaddress);
            capabilities.setCapability("port", "4723");
            capabilities.setCapability("platform-name", "iOS");
            capabilities.setCapability("deviceName", "iPhone 6s");
            capabilities.setCapability("platform-version", "9.2");
            capabilities.setCapability("full-reset", "true");
            capabilities.setCapability("browserName", "Safari");


            System.out.println("setUp - new iosDriver");
            try{
                iosWebDriver = new IOSDriver<>(service.getUrl(),capabilities);
            }
            catch(Exception ex){
                System.out.println("COULD NOT GET AN IOS DRIVER. EXCEPTION:"+ex.getMessage());
                return false;
            }
            
            System.out.println("setUp - new iosDriver created");
            
            if(iosDriver==null){
                System.out.println("IOSDRIVER NULL AFTER CREATION");
                return false;
            }
            
            return true;
        }
}

