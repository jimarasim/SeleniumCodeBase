package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 2/9/17.
 */
public class AnalogArchivePage extends BasePage {
    public By albumArtImage = By.id("albumart");
    public By analogAudioPlayer = By.id("analogplayer");
    public By disclaimer = By.xpath("//td/ul/li[contains(text(),'This site will never have advertising nor anything for sale.')]");
    public By clearPlaylistButton = By.id("clear");
    public By playlistDiv = By.id("playlistdiv");
    public By artistFilterSelect = By.id("artistFilter");
    public By artistSortLink = By.id("artistSort");
    public By albumSortLink = By.id("albumSort");
    public By titleSortLink = By.id("titleSort");
    public By fileSortLink = By.id("fileSort");
    public By modifiedDateSortLink = By.id("modifiedDateSort");
    public By artistCheckbox = By.xpath("//td[@name='artist']/input[@type='checkbox']");


    public String getBasePageUrl(){
        return "http://analogarchive.com";
    }

    public By[] getBasePageSanityCheckElements(){

        By[] sanityCheckElements = new By[] {
                albumArtImage,
                analogAudioPlayer,
                disclaimer,
                clearPlaylistButton,
                playlistDiv,
                artistFilterSelect,
                artistSortLink,
                albumSortLink,
                titleSortLink,
                fileSortLink,
                modifiedDateSortLink,
                artistCheckbox
        };

        return sanityCheckElements;
    }
}
