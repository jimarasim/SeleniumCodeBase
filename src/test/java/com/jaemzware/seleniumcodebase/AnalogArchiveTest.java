package com.jaemzware.seleniumcodebase;

import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by jameskarasim on 2/9/17.
 */
public class AnalogArchiveTest extends BaseTest {
    AnalogArchivePage testPage = new AnalogArchivePage();

    /**
     * THIS TEST ENSURES ALL EXPECTED PAGE ELEMENTS ACCORDING TO THE PAGE OBJECT, ARE ON THE PAGE
     * @throws Exception
     */
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

    /**
     * THIS TEST ENSURES THERE IS A MINIMUM NUMBER OF SONGS ON THE PAGE TO CHOOSE FROM
     * @throws Exception
     */
    @Test
    public void AssertSongCount() throws Exception
    {
        //AT LEAST NUMBER OF EXPECTED SONGS
        int expectedSongs = 334;

        //GO TO THE PAGE WHERE SONGS SHOULD APPEAR
        driverGetWithTime(testPage.getBasePageUrl());

        //GET A LIST OF SONG WEB ELEMENTS
        List<WebElement> songList = driver.findElements(testPage.artistCheckbox);

        //RECORD THE SIZE OF THE SONGLIST IN A VARIABLE
        int songListSize = songList.size();

        //VERIFY THE COUNT IS MORE THAN THE EXPECTED SONGS
        Assert.assertTrue("THERE ARE LESS THAN "+expectedSongs+" SONGS. ACTUAL:"+songListSize,songListSize >= expectedSongs);

        //DISPLAY THE COUNT FOR FUTURE TESTING
        System.out.println("NUMBER OF SONGS FOUND:"+songListSize);
    }

    /**
     * THIS TEST USES THE ARTIST DROPDOWN TO PICK THE MELVINS, THEN ASSERTS THE SONG COUNT
     */
    @Test
    public void AssertSongCountForTheMelvins() throws Exception
    {
        //VARIABLE FOR THE BAND NAME TO PICK
        String bandName = "Melvins";

        //NUMBER OF EXPECTED SONGS FOR THE MELVINS
        int expectedSongs = 23;

        //GO TO THE PAGE WHERE MELVINS SONGS SHOULD APPEAR
        driverGetWithTime(testPage.getBasePageUrl());

        //GET A SELECT TYPE OF WEB ELEMENT TO INTERACT WITH THE DROP DOWN
        Select artistFilterDropDown = new Select( driver.findElement(testPage.artistFilterSelect));

        //SELECT MELVINS FROM THE DROPDOWN
        artistFilterDropDown.selectByValue(bandName);

        //GET A LIST OF SONG WEB ELEMENTS
        List<WebElement> songList = driver.findElements(testPage.artistCheckbox);

        //RECORD THE SIZE OF THE SONGLIST IN A VARIABLE
        int songListSize = songList.size();

        //VERIFY THE COUNT IS MORE THAN THE EXPECTED SONGS
        Assert.assertTrue("THERE ARE LESS THAN "+expectedSongs+" SONGS FOR THE MELVINS. ACTUAL:"+songListSize,songListSize >= expectedSongs);

        //DISPLAY THE COUNT FOR FUTURE TESTING
        System.out.println("NUMBER OF SONGS FOUND FOR THE MELVINS:"+songListSize);
    }

    /**
     * THIS TEST CHECKS A CHECKBOX TO START THE MUSIC PLAYING
     */
    @Test
    public void PlayTheFirstSong() throws Exception
    {
        //GO TO THE PAGE WHERE SONGS SHOULD APPEAR
        driverGetWithTime(testPage.getBasePageUrl());

        //GET A LIST OF SONG WEB ELEMENTS
        List<WebElement> songList = driver.findElements(testPage.artistCheckbox);

        //CLICK THE FIRST SONG TO MAKE IT PLAY
        songList.get(0).click();

        //WAIT A SECOND FOR THE BROWSER TO GIVE AUDIO FEEDBACK
        Thread.sleep(1000);

        //TRY CLICKING THE AUDIO PLAYER
        WebElement audioPlayer = driver.findElement(testPage.analogAudioPlayer);
        audioPlayer.click();

        //WAIT A SECOND FOR USER TO VERIFY IF AUDIO PLAYER CLICKS
        Thread.sleep(1000);
    }

    /**
     * THIS TEST POPULATES THE PLAYLIST WITH SEVERAL SONGS, AND THEN CLEARS IT, MAKING SURE PLAYLIST IS EMPTY
     */
    @Test
    public void ClearThePlaylist() throws Exception
    {
        int songsToPlay = 5;

        //GO TO THE PAGE WHERE SONGS SHOULD APPEAR
        driverGetWithTime(testPage.getBasePageUrl());

        //GET A LIST OF SONG WEB ELEMENTS
        List<WebElement> songList = driver.findElements(testPage.artistCheckbox);

        //CLICK THE FIRST SEVERAL SONGS TO MAKE THEM PLAY
        for(int i=0;i<songsToPlay;i++) {
            songList.get(i).click();
        }

        //ASSERT THAT FIVE SONGS ARE IN THE LIST
        List<WebElement> songsInPlaylist = driver.findElements(testPage.analogPlayListItem);
        Assert.assertTrue(songsInPlaylist.size()==songsToPlay);

        //CLICK THE CLEAR BUTTON
        driver.findElement(testPage.clearPlaylistButton).click();

        //ASSERT THAT THE PLAY LIST SONGS ARE GONE
        Assert.assertFalse(IsElementPresent(testPage.analogPlayListItem));
    }


}
