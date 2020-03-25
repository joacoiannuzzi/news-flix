package com.lab1.entity;

import com.lab1.model.Article;
import com.lab1.util.LangUtils;

import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

import static com.lab1.util.EntityManagers.currentEntityManager;
import static com.lab1.util.LangUtils.checkedList;
import static com.lab1.util.Transactions.tx;

public class Articles {
    public static Optional<Article> findById(Long id){
        return tx(() ->
                Optional.of(currentEntityManager().find(Article.class, id))
        );
    }

    public static Optional<Article> findByUrl(String url){
        return tx(() -> LangUtils.<Article>checkedList(currentEntityManager()
                .createQuery("SELECT art FROM Article art WHERE art.url LIKE :url")
                .setParameter("url", url).getResultList()).stream()
                .findFirst()
        );
    }

    public static List<Article> listAll() {
        return tx(() ->
                checkedList(currentEntityManager().createQuery("SELECT art FROM Article art").getResultList())
        );
    }
    public static Article persist(Article article) {
        final EntityTransaction tx = currentEntityManager().getTransaction();

        try {
            tx.begin();

            currentEntityManager().persist(article);

            tx.commit();
            return article;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
