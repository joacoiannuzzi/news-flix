package com.lab1.newsflix.matcher;


import com.lab1.newsflix.model.Article;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lab1.newsflix.matcher.ArticleMatcherService.similarity;

public class ArticleMatcherRunner {


    public static Collection<Article> findSimilar(Article articleToCompare, Collection<Article> articles) {

        Collection<Article> result = new ArrayList<>();

        Map<String, List<Article>> listMap = articles.stream().collect(Collectors.groupingBy(Article::getNewspaper));
        listMap.remove(articleToCompare.getNewspaper());

        for (List<Article> articles1 : listMap.values()) {
            Stream<Article> articleStream = articles1.stream().filter(article ->
                    Math.abs(article.getDate().get(Calendar.DAY_OF_MONTH) - articleToCompare.getDate().get(Calendar.DAY_OF_MONTH)) <= 3 &&
                            Math.abs(article.getDate().get(Calendar.YEAR) - articleToCompare.getDate().get(Calendar.YEAR)) == 0 &&
                            Math.abs(article.getDate().get(Calendar.MONTH) - articleToCompare.getDate().get(Calendar.MONTH)) == 0
            );

            AtomicReference<Double> max = new AtomicReference<>((double) 0);
            AtomicReference<Article> articleMax = new AtomicReference<>(null);

            articleStream.forEach(article -> {
                        double score = similarity(articleToCompare.getBody(), article.getBody());
                        if (score >= 0.6 && score > max.get()) {
                            max.set(score);
                            articleMax.set(article);
                        }
                    }
            );
            if (articleMax.get() != null)
                result.add(articleMax.get());
        }
        return result;
    }
}


