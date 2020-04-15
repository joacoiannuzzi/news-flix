package com.lab1.scrappers;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.time.*;
import java.io.IOException;

import java.util.Calendar;
import java.util.List;

public class InfobaeScraper extends AbstractScraper {

    // WORKING FINE!

    private final static String baseUrl = "https://www.infobae.com";

    @Override
    public void scrap() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        // sintax xpath
        // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

        try {
            HtmlPage page = webClient.getPage(baseUrl);

            final List<HtmlDivision> htmlArticles = page.getByXPath("//div[@class='no-skin flex-item flex-stack text-align-left equalize-height-target' and (div[@class='headline huge normal-style '] or div[@class='headline normal normal-style '] or div[@class='headline xx-huge normal-style '])] | //div[@class='no-skin flex-item flex-stack text-align-center equalize-height-target' and (div[@class='headline huge normal-style '] or div[@class='headline normal normal-style '] or div[@class='headline xx-huge normal-style '])]");
            for (HtmlDivision htmlArticle : htmlArticles) {

                final HtmlAnchor anchor = htmlArticle.getFirstByXPath("div[@class='headline huge normal-style ']/a | div[@class='headline normal normal-style ']/a | div[@class='headline xx-huge normal-style ']/a");
                final HtmlImage htmlImage = htmlArticle.getFirstByXPath("div[@class='art art-low art-normal art-full-width text-align-left no-art-separator']//img | div[@class='art art-below-headline art-normal art-full-width text-align-left no-art-separator']//img");

                String url = anchor.getHrefAttribute();

                if (url.charAt(0) == 'h')
                    url = url.substring(23, url.length() - 1); //todas deberian empezar con /, si no es asi empieza con https://infobae.com/ es un hack feo pero funciona
                String title = anchor.asText();
                String picture = htmlImage == null ? "" : htmlImage.asXml();


                //createAndPersistArticle(baseUrl+url,title,"tofigureout",picture,10);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
