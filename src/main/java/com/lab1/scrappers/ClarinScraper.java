package com.lab1.scrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;
import com.lab1.model.Article;

import java.io.IOException;
import java.util.List;

public class ClarinScraper {

    public static void main(String[] args) {
        scrap();
    }

    public final static String baseUrl = "https://www.clarin.com/";

    public static void scrap() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);

//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);


        // sintax xpath
        // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

        try {
            HtmlPage page = webClient.getPage(baseUrl);

            final List<HtmlArticle> articles = page.getByXPath("//article");

            for (HtmlArticle htmlArticle : articles) {

                System.out.println(htmlArticle.asXml());

                final HtmlAnchor div_mt = htmlArticle.getFirstByXPath("//@class[.='mt '] | //@class[.='mt']");

                System.out.println();
                System.out.println(div_mt.asXml());

//                String text = anchor.getVisibleText();
//
//                String url = anchor.getHrefAttribute();
//                String mainWord = emphasis == null ? "" : emphasis.asText();
//                String title = text.startsWith(mainWord) && !mainWord.equals("") ? text.replaceFirst(mainWord, "").trim() : text;
//                String picture = htmlPicture == null ? "" : htmlPicture.asXml();
//
//                Article article = new Article();
//                article.setTitle(title);
//                article.setUrl(baseUrl + url);
//                article.setMainWord(mainWord);
//                article.setDate();
//                article.setPicture(picture);
//                article.setGrade(
//                        //if( contains h1 VERY IMPORTANT else 5..)
//                        10
//                );
//
////                Articles.persist(article);
//
//                // this is temp, webClient have to put them in database
//                ObjectMapper mapper = new ObjectMapper();
//                String jsonString = mapper.writeValueAsString(article);
//
//                System.out.println(jsonString);
            }


            // todo: only shows few results, need a way to scroll down page

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
