package com.jaemzware.seleniumcodebase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;

/**
 * Created by jameskarasim on 2/22/17.
 */
@RunWith(Parameterized.class)
public class SkatecreteordieTest extends BaseTest {
    SkatecreteordiePage testPage = new SkatecreteordiePage();

    String testState = "";

    @Parameterized.Parameters(name = "parameterization on city")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"OREGON"},
                {"MONTANA"},
                {"IDAHO"},
                {"DENMARK"}
        });
    }

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

    public SkatecreteordieTest(String state){
        testState=state;
    }

    @Test
    public void SelectStateParks() throws Exception
    {
        //GO TO THE PAGE FOR PICKING OREGON FROM THE DROPDOWN
        driverGetWithTime(testPage.getBasePageUrl());

        //SELECT OREGON FROM THE STATE DROP DOWN
        Select stateDropDown = new Select(driver.findElement(testPage.skateparkStateDropdown));
        stateDropDown.selectByValue(testState);

        Thread.sleep(500);

        //MAKE SURE THERE ARE AT LEAST 10 PARKS
        int parkCount = driver.findElements(testPage.skateparkLinks).size();

        Assert.assertTrue(parkCount>10);

    }
}
