package com.jaemzware.seleniumcodebase;

import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

import static com.jaemzware.seleniumcodebase.ParameterType.linkXpath;
import static com.jaemzware.seleniumcodebase.ParameterType.linksLoadedIndicatorXpath;

/**
 * Created by jameskarasim on 3/15/17.
 */
public class JaemzwareIndexAppiumTest extends BaseAppiumTest {

    JaemzwareIndexPage testPage = new JaemzwareIndexPage();

    @Test
    public void CheckSanityElementsTest() throws Exception
    {
        //GO TO THE PAGE WHERE SANITY ELEMENTS ARE TO BE FOUND
        driverGetWithTime(testPage.getBasePageUrl());

        //CHECK ISELEMENTPRESENT FOR EACH SANITY ELEMENT SPECIFIED
        for(By sanityElement : testPage.getBasePageSanityCheckElements()){
            if(IsElementPresent(sanityElement)){
                System.out.println("PASS: FOUND SANITY ELEMENT"+sanityElement.toString());
            }
            else{
                verificationErrors.append("FAIL: MISSING SANITY ELEMENT"+sanityElement.toString());
            }
        }
    }
}