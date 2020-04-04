package com.lab1.scrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.lab1.model.Article;

import java.io.IOException;
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

        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getCurrentWindow().setInnerHeight(Integer.MAX_VALUE);


        // //div[@id='anexo' and div[@class='titulo']]   another type of article that does not have com-description, is inside <article>

        try {
            HtmlPage page = client.getPage(baseUrl);

            final List<HtmlArticle> articles = page.getByXPath("//article[div[@class='com-description']]");

            for (HtmlArticle htmlArticle : articles) {

                final HtmlDivision com_description = htmlArticle.getFirstByXPath("div[@class='com-description']");
                final HtmlAnchor anchor = com_description.getFirstByXPath("h1[@class='com-title']/a | h2[@class='com-title']/a");
                final HtmlEmphasis emphasis = anchor.getFirstByXPath("em[@class='com-volanta']"); // can be null
                final HtmlSource htmlPicture = htmlArticle.getFirstByXPath("div[@class='com-media']/a/picture/source[@media='(min-width: 75.000em)']");

                String text = anchor.getVisibleText();

                String url = anchor.getHrefAttribute();
                String mainWord = emphasis == null ? "" : emphasis.asText();
                String title = text.startsWith(mainWord) && !mainWord.equals("") ? text.replaceFirst(mainWord, "").trim() : text;
                String picture = htmlPicture == null ? "" : htmlPicture.getAttribute("srcset");

                Article article = createAndPersistArticle(baseUrl + url, mainWord, title, picture);

//                Articles.persist(article);

                // this is temp, we have to put them in database
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(article);

                System.out.println(jsonString);
            }


            // todo: only shows few results, need a way to scroll down page

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
