package com.lab1.scrappers;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.io.IOException;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InfobaeScraper extends AbstractScraper {

    private final static String baseUrl = "https://www.infobae.com";

    @Override
    public void scrap() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

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
                String image = htmlImage == null ? "" : htmlImage.getAttribute("data-original");
                String category = url.substring(1, url.substring(1).indexOf("/") + 1);

                HtmlPage art = webClient.getPage(baseUrl + url);

                final HtmlElement date = art.getFirstByXPath("//span[@class='byline-date']");

                //13 de abril de 2020

                Calendar cal = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("d' de 'MMMM' de 'yyyy", new Locale("es", "ES"));
                try {
                    cal.setTime(sdf.parse(date.asText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.println("Article: " + title + " with url: " + url +
                            " doesn't have date so it wasn't persisted");
                    continue;
                }

                HtmlElement htmlItem = art.getFirstByXPath("//div[@id='article-content']");
                final List<HtmlElement> bodytags = htmlItem.getByXPath("//div[@class='row pb-content-type-text']");

                String body = "";

                for (HtmlElement bodyelems : bodytags) {

                    body = body.concat(bodyelems.asText() + "\n");

                }

                try {
                    createAndPersistArticle(baseUrl + url, title, category, image, body, cal, "Infobae");
                }catch (Exception e){
                    System.out.println("Repeated Article, or some other error.");
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
