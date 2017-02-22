package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 2/22/17.
 */
public class SkatecreteordiePage extends BasePage {

    public By skateparkStateDropdown = new By.ByXPath("//select[contains(@onchange,'switchparks')]");
    public By skateparkLinks = new By.ByXPath("//a[contains(text(),'Skatepark')]");
    public By chatHeader = new By.ByXPath("//h1[text()='CHAT']");
    public By chatClientUser = new By.ById("chatClientUser");
    public By chatClientMessage = new By.ById("chatClientMessage");
    public By chatClientAutoResponder = new By.ById("chatClientAutoResponder");
    public By messagesdiv = new By.ById("messagesdiv");
    public By musicHeader = new By.ByXPath("//h1[text()='MUSIC']");
    public By selectsongs = new By.ById("selectsongs");
    public By jaemzwaredynamicaudioplayer = new By.ById("jaemzwaredynamicaudioplayer");
    public By nextaudiotrack = new By.ById("nextaudiotrack");
    public By videosHeader = new By.ByXPath("//h1[text()='VIDEOS']");
    public By selectvideos = new By.ById("selectvideos");
    public By jaemzwaredynamicvideoplayer = new By.ById("jaemzwaredynamicvideoplayer");

    public String getBasePageUrl(){
        return "http://skatecreteordie.com:3005/skatecreteordie";
    }
    public By[] getBasePageSanityCheckElements(){
        By[] sanityCheckElements = new By[] {
                skateparkStateDropdown,
                skateparkLinks,
                chatHeader,
                chatClientUser,
                chatClientMessage,
                chatClientAutoResponder,
                messagesdiv,
                musicHeader,
                selectsongs,
                jaemzwaredynamicaudioplayer,
                nextaudiotrack,
                videosHeader,
                selectvideos,
                jaemzwaredynamicvideoplayer
        };

        return sanityCheckElements;
    }
}
