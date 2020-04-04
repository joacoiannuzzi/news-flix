package com.lab1.scrappers;

import com.lab1.model.Article;

public abstract class AbstractScraper {


    public abstract void scrap();

    protected Article createAndPersistArticle(String url, String mainWord, String title, String picture) {
        Article article = new Article();
        article.setTitle(title);
        article.setUrl(url);
        article.setMainWord(mainWord);
        article.setDate();
        article.setImage(picture);
        article.setGrade(
                //if( contains h1 VERY IMPORTANT else 5..)
                10
        );
        return article;

        // persist...
    }
}
