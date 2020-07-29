package com.lab1.newsflix.scraper;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Component
public class InfobaeScraper extends AbstractScraper {
//public class InfobaeScraper {

    private final static String baseUrl = "https://www.infobae.com/";
    private final static String searchURL = "https://www.infobae.com/feeds/rss/";


    @Override
    public void scrap() {
    //public static void scrap() {

        System.out.println("Starting InfobaeScraper");

        try {
            // Connect to the web site
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            try {
                XmlPage page = webClient.getPage(searchURL);
                Document doc = Jsoup.parse(page.getWebResponse().getContentAsString(), "", Parser.xmlParser());
                Elements elements = doc.select("item");

                for (Element element : elements) {
                    try {

                        String title = element.select("title").first().text();
                        String url = element.select("link").first().text();

                        String s = url.replaceFirst("https://www.infobae.com/", "");
                        String category = s.substring(0, s.indexOf("/"));

                        String date = element.select("pubDate").first().text();


                        Calendar cal = Calendar.getInstance();

                        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss Z", new Locale("en", "EN"));

                        try {

                            cal.setTime(sdf.parse(date));
                        } catch (ParseException e) {
                            System.out.println("Wrong date type");
                        }


                        Document articleDocument = Jsoup.connect(url).get();

                        String img = articleDocument.select("article picture img").attr("src");

                        Elements bodyText = articleDocument.select("p");


                        String body = "";

                        for (Element bodyelems : bodyText) {

                            body = body.concat(bodyelems.text() + SEPARATION);

                        }
                        try {
                            save(url, title, category, img, body, cal, "Infobae");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (NullPointerException e) {
                        System.out.println("There was a null pointer with an article so it wasn't persisted");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

