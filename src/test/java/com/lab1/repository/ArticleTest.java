package com.lab1.repository;

import com.lab1.entity.Articles;
import com.lab1.model.Article;
import com.lab1.util.EntityManagers;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArticleTest {

    @Before
    public void setUp() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("test");
        EntityManagers.setFactory(managerFactory);
    }

    @Test
    public void createArticle() {
        final Article article = new Article();

        article.setTitle("TITLE");
        article.setUrl("http://THISURL.com");
        article.setCategory("MAINWORD");
        article.setImage("http://imageurl.com");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 3, 4, 2);
        article.setDate(calendar);

        Articles.persist(article);

        final Optional<Article> persistedArticle = Articles.findByUrl(article.getUrl());

        assertThat(persistedArticle.isPresent(), is(true));
        assertThat(persistedArticle.get().getUrl(), is("http://THISURL.com"));
        assertThat(persistedArticle.get().getTitle(), is("TITLE"));
        assertThat(persistedArticle.get().getCategory(), is("MAINWORD"));
        assertThat(persistedArticle.get().getDate(), is(calendar));
        assertThat(persistedArticle.get().getImage(), is("http://imageurl.com"));

        Optional<Article> byEmail = Articles.findByUrl(persistedArticle.get().getUrl());
        System.out.println(byEmail);
    }

}
