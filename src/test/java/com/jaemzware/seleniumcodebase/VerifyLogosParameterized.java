package com.jaemzware.seleniumcodebase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

/**
 * Created by jameskarasim on 6/18/16.
 */


@RunWith(Parameterized.class)
public class VerifyLogosParameterized extends BaseTest{

    private String testCity="";

    @Parameterized.Parameters(name = "parameterization on city")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"boise"},
                {"seattle"},
                {"portland"},
                {"vancouverbc"}
        });
    }

    public VerifyLogosParameterized(String city){
        testCity=city;
    }

    @Test
    public void ParameterizedVerifyLogosTest(){
        System.out.println("CITY:"+testCity);
        Integer one = 1;
        Assert.assertTrue(one==1);
    }
}
