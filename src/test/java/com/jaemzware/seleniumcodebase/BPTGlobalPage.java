package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 7/11/16.
 */
public class BPTGlobalPage extends BasePage {
    public String basePageUrl = "http://brownpapertickets.com/";

    public By contactUs = By.xpath("//td[contains(text(),'Contact Us')]");
    public By ticketBuyers = By.xpath("//td[contains(text(),'Ticket Buyers')]");
    public By joinTheMailingList = By.xpath("//td[contains(text(),'Join the mailing list')]");
    public By connectWithUs = By.xpath("//td[contains(text(),'Connect with us')]");
    public By resources = By.xpath("//td[contains(text(),'Resources')]");
    public By eventProducers = By.xpath("//td[contains(text(),'Event Producers')]");
    public By bptLogo = By.xpath("//img[contains(text(),'BPT_logo_drop_small.png')]");
    public By en = By.xpath("//td[contains(text(),'EN')]");
    public By es = By.xpath("//td[contains(text(),'ES')]");
    public By fr = By.xpath("//td[contains(text(),'FR')]");
    public By friendUsOnFacebook = By.xpath("//td[contains(text(),'Friend us on Facebook')]");
    public By followUsOnTwitter = By.xpath("//td[contains(text(),'Follow us on Twitter')]");
    public By watchUsOnYouTube = By.xpath("//td[contains(text(),'Watch us on YouTube')]");
    public By readOurBlog = By.xpath("//td[contains(text(),'Read our blog')]");
    public By getToKnowUs = By.xpath("//td[contains(text(),'Get to know us')]");
    public By meetTheDoers = By.xpath("//td[contains(text(),'Meet the Doers')]");

    public String getBasePageUrl(){
        return basePageUrl;
    }

    //RULE: we want to make sure every page object returns an array of locators that it must have for a sanity check
    public By[] getBasePageSanityCheckElements(){
        By[] sanityCheckElements = {contactUs,
                ticketBuyers,
                joinTheMailingList,
                connectWithUs,
                resources,
                eventProducers,
                bptLogo,
                en,
                es,
                fr,
                friendUsOnFacebook,
                followUsOnTwitter,
                watchUsOnYouTube,
                readOurBlog,
                getToKnowUs,
                meetTheDoers};
        return sanityCheckElements;
    }
}
