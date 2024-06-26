package com.lab1.newsflix.scraper;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Component
public class ClarinScraper extends AbstractScraper {

    private final static String[] baseUrl = {"https://www.clarin.com/rss/lo-ultimo/", "https://www.clarin.com/rss/politica/", "https://www.clarin.com/rss/economia/", "https://www.clarin.com/rss/sociedad/",
            "https://www.clarin.com/rss/policiales/", "https://www.clarin.com/rss/cultura/", "https://www.clarin.com/rss/espectaculos/", "https://www.clarin.com/rss/deportes/",
            "https://www.clarin.com/rss/mundo/", "https://www.clarin.com/rss/tecnologia/", "https://www.clarin.com/rss/buena-vida/"
    };

    @Override
    public void scrap() {
        System.out.println("Starting ClarinScrapper");

        try {
            // Connect to the web site
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);

            for (String baseUrlCurrent : baseUrl) {
                XmlPage page = webClient.getPage(baseUrlCurrent);
                Document doc = Jsoup.parse(page.getWebResponse().getContentAsString(), "", Parser.xmlParser());

                Elements elements = doc.select("item");

                for (Element element : elements) {
                    try {

//                        System.out.println(element.toString() + "\n");

                        String title = element.select("title").first().text();
                        String url = element.select("link").first().text();
                        String image = element.select("enclosure").first().attr("url");
                        String s = url.replaceFirst("http://www.clarin.com/", "");
                        String category = s.substring(0, s.indexOf("/"));

                        String pubDate = element.select("pubDate").first().text();
                        String date = pubDate.substring(pubDate.indexOf(",") + 2, pubDate.indexOf("-"));

                        // 15 Apr 2020 10:24:14
                        Calendar cal = Calendar.getInstance();

                        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy hh:mm:ss ", new Locale("en", "EN"));
                        try {

                            cal.setTime(sdf.parse(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Document articleDocument = Jsoup.connect(url).get();

                        Element bodyText = articleDocument.select("div.body-nota").first();
                        Elements bodytags = bodyText.select(" > p");

                        String body = "";

                        for (Element bodyelems : bodytags) {

                            body = body.concat(bodyelems.text() + SEPARATION);

                        }
                        try {
                            save(url, title, category, image, body, cal, "Clarin");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (NullPointerException e) {
                        System.out.println("There was a null pointer with an article so it wasn't persisted");
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

