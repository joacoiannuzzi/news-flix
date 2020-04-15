package com.lab1.scrappers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlArticle;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.URL;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;



public class ClarinScraper extends AbstractScraper {

    public static void main(String[]args){
        new ClarinScraper().scrap();
    }


    // sintax xpath
    // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

    //private final static String[] baseUrl = {"https://www.clarin.com/rss/lo-ultimo/","https://www.clarin.com/rss/politica/","https://www.clarin.com/rss/economia/","https://www.clarin.com/rss/sociedad/","https://www.clarin.com/rss/policiales/","https://www.clarin.com/rss/cultura/","https://www.clarin.com/rss/espectaculos/","https://www.clarin.com/rss/deportes/","https://www.clarin.com/rss/mundo/","https://www.clarin.com/rss/tecnologia/","https://www.clarin.com/rss/buena-vida/"};
    private final static String[] baseUrl = {"https://www.clarin.com/rss/lo-ultimo/"};

    @Override
    public void scrap() {

            try {
                // Connect to the web site
                WebClient webClient = new WebClient(BrowserVersion.CHROME);
                webClient.getOptions().setJavaScriptEnabled(false);
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
                webClient.getCookieManager().setCookiesEnabled(true);
                webClient.getOptions().setThrowExceptionOnScriptError(false);

                XmlPage page = webClient.getPage(baseUrl[0]);

                Document doc = Jsoup.parse(page.getWebResponse().getContentAsString(), "", Parser.xmlParser());



            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

