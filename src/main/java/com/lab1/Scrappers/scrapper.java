package com.lab1.Scrappers;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lab1.model.Article;

public class scrapper {
    public static void main(String[] args) {

        String baseUrl = "https://www.lanacion.com.ar" ;
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {

            HtmlPage page = client.getPage(baseUrl);

            List<HtmlElement> articles = page.getByXPath("/html/body[@class='--scrollDown']/div[@id='wrapper']/main[@class='cargando']/section[@class='lay-sidebar'][1]/section[@class='sidebar__main']/section[@class='row mod-layout-articles --apertura --left place-02 ']//div//div[@class='com-description']/h2[@class='com-title']/a") ;
            articles.addAll(page.getByXPath("/html/body[@class='--scrollUp']/div[@id='wrapper']/main/section[@class='lay-sidebar'][1]/section[@class='sidebar__main']/section[@class='row mod-layout-articles --apertura --left place-02 ']/div[@class='col-tablet-8']/article[@class='mod-article w-100-mobile firma-autor toi8748 nid2349234']/div[@class='com-description']/h1[@class='com-title']/a"));
            articles.addAll(page.getByXPath("/html/body[@class='--scrollDown']/div[@id='wrapper']/main/section[@class='lay-sidebar'][1]/section[@class='sidebar__main']/div[5]/section[@id='tema_2']/div[@class='row-gap-tablet-3']//article//div[@class='com-description']/h2[@class='com-title']/a"));

            if(articles.isEmpty()){
                System.out.println("No articles found !");
            }else{
                for(HtmlElement htmlArticle : articles){
                    HtmlAnchor artTitle = ((HtmlAnchor) htmlArticle.getFirstByXPath(".//em[class='com-volanta']/a"));
                    HtmlElement artImportantWord = ((HtmlElement) htmlArticle.getFirstByXPath(".//a/em[@class='com-volanta']")) ;


                    Article article = new Article();
                    article.setTitle(artTitle.asText());
                    article.setUrl( baseUrl + artTitle.getHrefAttribute());
                    article.setMainWord(artImportantWord.toString());

                    //article.setText(); TODO
                    article.setGrade(
                            //if( contains h1 VERY IMPORTANT else 5..)
                            10
                    );
                    article.setDate();


                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(article) ;

                    System.out.println(jsonString);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
