package com.lab1.repository;

import com.lab1.entity.Articles;
import com.lab1.model.Article;
import com.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class ArticleTest {

    @Before
    public void setUp() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("test");
        EntityManagers.setFactory(managerFactory);
    }

    @Test
    public void createArticle() {
        final Article article=new Article();

        article.setTitle("TITLE");
        article.setText("THIS IS SOME TEXT");
        article.setUrl("http://THISURL.com");
        article.setGrade(10);
        article.setMainWord("MAINWORD");
        article.setDate();


        assertThat(Articles.persist(article).getId(), greaterThan(0L));

        final Optional<Article> persistedArticle = Articles.findById(article.getId());

        assertThat(persistedArticle.isPresent(), is(true));
        assertThat(persistedArticle.get().getTitle(), is("TITLE"));
        assertThat(persistedArticle.get().getText(), is("THIS IS SOME TEXT"));
        assertThat(persistedArticle.get().getUrl(), is("http://THISURL.com"));
        assertThat(persistedArticle.get().getMainWord(), is("MAINWORD"));
        assertThat(persistedArticle.get().getGrade(), is(10));

        Optional<Article> byEmail = Articles.findByUrl(persistedArticle.get().getUrl());
        System.out.println(byEmail);
    }

}
