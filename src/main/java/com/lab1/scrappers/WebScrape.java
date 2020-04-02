package com.lab1.scrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.lab1.model.Article;

import java.io.IOException;
import java.util.List;

public class WebScrape {

    public static void main(String[] args) {

//        EntityManagerFactory managerFactory;
//        managerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManagers.setFactory(managerFactory);


        final String baseUrl = "https://www.lanacion.com.ar";
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getCurrentWindow().setInnerHeight(Integer.MAX_VALUE);

        // sintax xpath
        // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

        // //div[@id='anexo' and div[@class='titulo']]   another type of article that does not have com-description, is inside <article>

        try {
            HtmlPage page = client.getPage(baseUrl);
            page.executeJavaScript("window.scrollBy(0,3000)");

            final List<HtmlArticle> articles = page.getByXPath("//article[div[@class='com-description']]");

            for (HtmlArticle htmlArticle : articles) {

                final HtmlDivision com_description = htmlArticle.getFirstByXPath("div[@class='com-description']");
                final HtmlAnchor anchor = com_description.getFirstByXPath("h1[@class='com-title']/a | h2[@class='com-title']/a");
                final HtmlEmphasis emphasis = anchor.getFirstByXPath("em[@class='com-volanta']"); // can be null
                final HtmlPicture htmlPicture = htmlArticle.getFirstByXPath("div[@class='com-media']/a/picture");

                String text = anchor.getVisibleText();

                String url = anchor.getHrefAttribute();
                String mainWord = emphasis == null ? "" : emphasis.asText();
                String title = text.replaceFirst(mainWord, "").trim();
                String picture = htmlPicture == null ? "" : htmlPicture.asXml();

                Article article = new Article();
                article.setTitle(title);
                article.setUrl(baseUrl + url);
                article.setMainWord(mainWord);
                article.setDate();
                article.setPicture(picture);

//                Articles.persist(article);

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(article);

                System.out.println(jsonString);
            }


            // todo: only shows few results, need a way to scroll down page

        } catch (IOException e) {
            e.printStackTrace();
        }

//        managerFactory.close();
    }
}
