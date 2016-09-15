package com.jaemzware.seleniumcodebase;

import org.openqa.selenium.By;

/**
 * Created by jameskarasim on 7/11/16.
 */
public class BPTGlobalPage extends BasePage {
    public static String basePageUrl = null;
    public By contactUs = By.xpath("//td[contains(text(),'Contact us')]");
    public By ticketBuyers = By.xpath("//td[contains(text(),'Ticket Buyers')]");
    public By joinTheMailingList = By.xpath("//td[contains(text(),'Join the mailing list')]");
    public By connectWithUs = By.xpath("//td[contains(text(),'Connect with us')]");
    public By resources = By.xpath("//td[contains(text(),'Resources')]");
    public By eventProducers = By.xpath("//td[contains(text(),'Event Producers')]");
    public By bptLogo = By.xpath("//img[contains(@src,'logo')]");
    public By en = By.xpath("//td[contains(text(),'EN')]");
    public By es = By.xpath("//td[contains(text(),'ES')]");
    public By fr = By.xpath("//td[contains(text(),'FR')]");
    public By friendUsOnFacebook = By.xpath("//a[contains(text(),'Friend us on Facebook')]");
    public By followUsOnTwitter = By.xpath("//a[contains(text(),'Follow us on Twitter')]");
    public By watchUsOnYouTube = By.xpath("//a[contains(text(),'Watch us on YouTube')]");
    public By readOurBlog = By.xpath("//a[contains(text(),'Read our blog')]");
    public By getToKnowUs = By.xpath("//a[contains(text(),'Get to know us')]");
    public By meetTheDoers = By.xpath("//a  [contains(text(),'Meet the Doers')]");

    public BPTGlobalPage(){
        basePageUrl = baseSiteUrl;
    }

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
