package com.jaemzware.seleniumcodebase;

import org.junit.Assert;
import org.junit.Test;

import static com.jaemzware.seleniumcodebase.CodeBase.IsElementPresent;

/**
 * Created by jameskarasim on 5/26/16.
 */
public class SkateCreteOrDieTest extends BaseTest{

    @Test
    public void SkatecreteordieUsingPageObject(){
        try{
            SkateCreteOrDiePage testPage = new SkateCreteOrDiePage();

            driverGetWithTime(testPage.pageUrl);
            if(IsElementPresent(testPage.pageTitle)) {
                System.out.println("PASS: PAGETITLE");
            }
            else{
                verificationErrors.append("MISSING TITLE");
            }

            if(IsElementPresent(testPage.pageHeader)) {
                System.out.println("PASS: PAGEHEADER");
            }
            else{
                verificationErrors.append("MISSING HEADER");
            }
        }
        catch(Exception ex){
            Assert.fail("FAIL:"+ex.getMessage());
        }
    }
}
