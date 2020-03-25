package com.lab1.Article;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArticleTest {

    @Test
    public void ArticleTest(){

        String title = "This is a title";
        String text = "this is a body" +
                "\n" + "this is a body extension";
        String url = "http://thisisaurl.com";
        int grade = 10;

        IArticle Article = new Article(title,text,url,grade);

        assertEquals(Article.getTitle(),title);
        assertEquals(Article.getText(),text);
        assertEquals(Article.getUrl(),url);
        assertEquals(Article.getGrade(),grade);

        System.out.println(Article.getDate());

        //Article Article2 = new Article(title,text,url,99); //Should not work


    }
}
