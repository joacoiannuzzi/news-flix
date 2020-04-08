package com.lab1.scrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.URL;
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

    //private final static String baseUrl = "https://www.lanacion.com.ar/?utm_source=navigation&datamodule=tema_1;tema_2;tema_3;tema_4;tema_5;tema_6;tema_7;tema_8;tema_9;tema_10;tema_11;tema_12;tema_13;tema_14;tema_15;tema_16";
    private final static String baseUrl = "https://www.lanacion.com.ar/?utm_source=navigation&datamodule=tema_1";


    @Override
    public void scrap() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        // sintax xpath
        // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

        try {
            UnexpectedPage page = webClient.getPage(baseUrl);
            String result = page.getWebResponse().getContentAsString();
            String[] split = result.split("<section[^>]*[^>]*>[^~]*?</section>"); //Nos divide en secciones x temas, tendremos un arreglo de 16




//            final List<HtmlDivision> htmlArticles = (HtmlPage) split[0];
//            for (HtmlDivision htmlArticle : htmlArticles) {
//
//                final HtmlAnchor anchor = htmlArticle.getFirstByXPath("");
//                final HtmlImage htmlImage = htmlArticle.getFirstByXPath("");
//                String url = anchor.getHrefAttribute();
//
//
//                String title = anchor.asText();
//                String picture = htmlImage == null ? "" : htmlImage.asXml();
//
//                calendar.set(2020, Calendar.MARCH,3,23,5); // We have to figure out how to extract from articles.
//
//                createAndPersistArticle(baseUrl+url,title,"tofigureout",picture,10);
//
//            }

        }


         catch (IOException e) {
            e.printStackTrace();
        }

    }

}
