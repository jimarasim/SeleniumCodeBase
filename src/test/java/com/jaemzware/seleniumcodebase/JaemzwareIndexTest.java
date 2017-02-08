package com.jaemzware.seleniumcodebase;

import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 2/8/17.
 */
public class JaemzwareIndexTest extends BaseTest {

    JaemzwareIndexPage testPage = new JaemzwareIndexPage();

    @Test
    public void CheckSanityElementsTest() throws Exception
    {

        driverGetWithTime(testPage.getBasePageUrl());

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
