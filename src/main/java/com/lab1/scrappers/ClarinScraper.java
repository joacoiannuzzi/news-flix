package com.lab1.scrappers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlArticle;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public class ClarinScraper extends AbstractScraper{

    // sintax xpath
    // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

    private final static String baseUrl = "https://www.clarin.com/";

    @Override
    public void scrap() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        try {
            HtmlPage page = webClient.getPage(baseUrl);

            final List<HtmlArticle> articles = page.getByXPath("//article");

            for (HtmlArticle htmlArticle : articles) {

                System.out.println(htmlArticle.asXml());

                final HtmlAnchor div_mt = htmlArticle.getFirstByXPath("//@class[.='mt '] | //@class[.='mt']");

                System.out.println();
                System.out.println(div_mt.asXml());

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
