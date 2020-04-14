package com.lab1.scrappers;


import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InvalidClassException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LaNacionScraper extends AbstractScraper {

    // sintax xpath
    // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html

    //private final static String baseUrl = "https://www.lanacion.com.ar/?utm_source=navigation&datamodule=tema_1;tema_2;tema_3;tema_4;tema_5;tema_6;tema_7;tema_8;tema_9;tema_10;tema_11;tema_12;tema_13;tema_14;tema_15;tema_16";
    private final static String baseURL = "https://www.lanacion.com.ar";
    private final static String searchURL = "https://www.lanacion.com.ar/?utm_source=navigation&datamodule=tema_1;tema_2";
    private final static Pattern pattern = Pattern.compile("<section[^>]*[^>]*>[^~]*?</section>");
    private final List<Document> documentsList = new ArrayList<>();

    @Override
    public void scrap() {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

//        // sintax xpath
//        // https://www.mclibre.org/consultar/xml/lecciones/xml-xpath.html
//
        try {
            UnexpectedPage page = webClient.getPage(searchURL); //Hago de esta manera en vez de xmlpath xq la nacion tiene el searchurl que lo hace mas facil.
            String result = StringEscapeUtils.unescapeJava(page.getWebResponse().getContentAsString()); //Unescape or everything goes wild.
            Matcher matcher = pattern.matcher(result);

            while(matcher.find()){

                documentsList.add(Jsoup.parse(matcher.group(0)));
            }


            for (Document doc : documentsList){

                Elements elements = doc.select("article");

                for (Element link : elements){
                    Elements divs = link.select("div"); //Tenemos los divs  //com-media y com-description

                    String articleURL = divs.get(0).select("a").first().attr("href");// href
                    String imageURL = divs.get(0).select("source").first().attr("srcset");// href
                    String title = divs.get(0).select("a").first().attr("title");//Title ref que tambien estaria en div.get(1)
                    String category = articleURL.substring(1,articleURL.substring(1).indexOf("/")+1);

                    //Now scrap the article
                    try{
                    HtmlPage articlePage = webClient.getPage(baseURL+articleURL);

                    List<HtmlElement> htmlbodytexts =  articlePage.getByXPath("//div[@id='wrapper']/main[@id='item-nota']/article[@id='nota']/section[@id='cuerpo']");

                    for(HtmlElement htmlItem : htmlbodytexts) {
                        final HtmlElement fecha = htmlItem.getFirstByXPath("//div[@class='barra']//section[@class='fecha']");
                        //System.out.println(fecha.asText());
                        //13 de abril de 2020  • 18:53
                        SimpleDateFormat sdf = new SimpleDateFormat("d' de 'MMMM' de 'yyyy'  • 'hh:mm", new Locale("es", "ES"));
                        try {
                            cal.setTime(sdf.parse(fecha.asText()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        final List<HtmlElement> bodytags = htmlItem.getByXPath("//section[@id='cuerpo']//p");

                        String body = "";

                        for (HtmlElement bodyelems : bodytags) {

                            body = body.concat(bodyelems.asText());

                        }

                        System.out.println("\n"+body+"\n");


                        createAndPersistArticle(baseURL + articleURL, title, category, baseURL + imageURL, body, this.cal, "LaNacion");

                        }
                    } catch (IOException e) {
                            e.printStackTrace();
                        }





                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
