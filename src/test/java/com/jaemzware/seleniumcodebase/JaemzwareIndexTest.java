package com.jaemzware.seleniumcodebase;

import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

import static com.jaemzware.seleniumcodebase.ParameterType.linkXpath;
import static com.jaemzware.seleniumcodebase.ParameterType.linksLoadedIndicatorXpath;

/**
 * Created by jameskarasim on 2/8/17.
 */
public class JaemzwareIndexTest extends BaseTest {

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

    @Test
    public void VisitLinksOnPageTest() throws Exception
    {
        //GO TO THE PAGE WHERE WE WANT TO FIND LINKS
        driverGetWithTime(testPage.getBasePageUrl());

        //SET THIS VARIABLE AS THE LINK XPATH FOR USING GETLINKSONPAGE
        linkXpath = "//a";
        linksLoadedIndicatorXpath = "//a[@href='http://jaemzware.org']";

        //LIST RETURNED FROM CONVENIENCE METHOD TO GET ALL LINKS ON PAGE
        List<String> linksOnPage = GetLinksOnPage();

        //VISIT EACH LINK
        for(String linkOnPage : linksOnPage){
            //DONT VISIT MAILTO OR ITUNES LINKS
            if(!linkOnPage.contains("mailto") && !linkOnPage.contains("itunes")) {
                driverGetWithTime(linkOnPage);
            }
        }
    }
}
