package com.lab1.newsflix.matcher;


import com.lab1.newsflix.model.Article;
import com.lab1.newsflix.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lab1.newsflix.matcher.ArticleMatcherService.similarity;


public class ArticleMatcherRunner {


    public static Article findMostSimilar(Long id, String diarioABuscar, Collection<Article> articles) {



        //Tenemos un id del articulo a comparar y un String del diario a buscar la mas similar y todos los articulos

        Optional<Article> byId = articles.stream().filter(article -> article.getId().equals(id)).findFirst();
        if (byId.isPresent()) {

            Article artDelId = byId.get();


            articles = articles.stream().filter(article -> article.getNewspaper().equals(diarioABuscar)).collect(Collectors.toList());

            double max = 0;
            Long idOfMax = 0L;
            for (Article temp : articles) {
                double score = similarity(artDelId.getBody(), temp.getBody());

                if (score > max) {
                    max = score;
                    idOfMax = temp.getId();
                }
            }

            System.out.println("Max Score = " + max);

            //Ya esta presente este id porque esta en el respository.
            Long finalIdOfMax = idOfMax;
            return articles.stream().filter(article -> article.getId().equals(finalIdOfMax)).findFirst().get();

        } else {

            throw new RuntimeException("No article found for title.");
        }

    }
}

