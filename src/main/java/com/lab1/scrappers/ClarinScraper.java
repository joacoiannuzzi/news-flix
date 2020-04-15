package com.lab1.scrappers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlArticle;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class ClarinScraper extends AbstractScraper {

    // sintax xpath
    // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

    private final static String baseUrl = "https://www.clarin.com";

    @Override
    public void scrap() {

        try {
            Document document = Jsoup.connect(baseUrl).get();
            Element page = document.select("div.on-demand").first();
            String _url = page.attr("data-src");
            Document doc = Jsoup.connect(baseUrl + _url).get();
            Element div = doc.select("div").first();
            String s = Jsoup.parse(div.toString()).toString();
            System.out.println(s);
//            for (Element article : articles) {

//                System.out.println(article.toString());

//                final HtmlAnchor div_mt = element.getFirstByXPath("//@class[.='mt '] | //@class[.='mt']");

//            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
