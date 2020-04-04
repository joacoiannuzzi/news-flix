package com.lab1.scrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lab1.model.Article;

import java.io.IOException;
import java.util.List;

public class InfobaeScraper extends AbstractScraper{

    // WORKING FINE!

    public final static String baseUrl = "https://www.infobae.com";

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

            final List<HtmlDivision> articles = page.getByXPath("//div[@class='no-skin flex-item flex-stack text-align-left equalize-height-target' and (div[@class='headline huge normal-style '] or div[@class='headline normal normal-style '] or div[@class='headline xx-huge normal-style '])] | //div[@class='no-skin flex-item flex-stack text-align-center equalize-height-target' and (div[@class='headline huge normal-style '] or div[@class='headline normal normal-style '] or div[@class='headline xx-huge normal-style '])]");

            for (HtmlDivision htmlArticle : articles) {

                final HtmlAnchor anchor = htmlArticle.getFirstByXPath("div[@class='headline huge normal-style ']/a | div[@class='headline normal normal-style ']/a | div[@class='headline xx-huge normal-style ']/a");
                final HtmlImage htmlImage = htmlArticle.getFirstByXPath("div[@class='art art-low art-normal art-full-width text-align-left no-art-separator']//img | div[@class='art art-below-headline art-normal art-full-width text-align-left no-art-separator']//img");

                String url = anchor.getHrefAttribute();
                String title = anchor.asText();
                String picture = htmlImage == null ? "" : htmlImage.asXml();

                Article article = createAndPersistArticle(baseUrl + url, "", title, picture);
//

                // this is temp, webClient have to put them in database
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(article);

                System.out.println(jsonString);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
