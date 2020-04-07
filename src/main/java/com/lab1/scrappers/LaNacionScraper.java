package com.lab1.scrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.lab1.model.Article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LaNacionScraper extends AbstractScraper {


    public static void main(String[] args) {
        new LaNacionScraper().scrap();
    }
    // sintax xpath
    // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

    public final static String baseUrl = "https://www.lanacion.com.ar/";

    @Override
    public void scrap() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        // sintax xpath
        // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

        try {
            HtmlPage page = webClient.getPage(baseUrl);

            final List<HtmlDivision> htmlArticles = page.getByXPath("//article[div[@class='com-description']]");
            for (HtmlDivision htmlArticle : htmlArticles) {

                final HtmlAnchor anchor = htmlArticle.getFirstByXPath("");
                final HtmlImage htmlImage = htmlArticle.getFirstByXPath("");
                String url = anchor.getHrefAttribute();


                String title = anchor.asText();
                String picture = htmlImage == null ? "" : htmlImage.asXml();
                calendar.set(2020, Calendar.MARCH,3,23,5);

                createAndPersistArticle(baseUrl+url,title,"tofigureout",picture,10);

            }

        }


         catch (IOException e) {
            e.printStackTrace();
        }

    }

}
