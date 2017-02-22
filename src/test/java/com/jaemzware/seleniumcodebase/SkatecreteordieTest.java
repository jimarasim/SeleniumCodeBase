package com.jaemzware.seleniumcodebase;

import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 2/22/17.
 */
public class SkatecreteordieTest extends BaseTest {
    SkatecreteordiePage testPage = new SkatecreteordiePage();

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
