package com.lab1.repository;

import com.lab1.model.Article;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ArticleDB {
    private final EntityManager entityManager;

    public ArticleDB(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Article> findById(Long id){
        return tx(() ->
                Optional.of(entityManager.find(Article.class, id))
        );
    }

    public <R> R tx(Supplier<R> s) {
        final EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            R r = s.get();

            tx.commit();
            return r;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public void tx(Runnable r){
        final EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            r.run();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    public Optional<Article> findByEmail(String email){
        return Optional.empty();
    }

    public List<Article> listAll() {
        return Collections.emptyList();
    }

    public Article persist(Article article) {
        final EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            entityManager.persist(article);

            tx.commit();
            return article;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
