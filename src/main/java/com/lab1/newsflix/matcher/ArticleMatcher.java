package com.lab1.newsflix.matcher;

import com.lab1.newsflix.model.Article;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.simmetrics.builders.StringMetricBuilder.with;

@Component
public class ArticleMatcher {

    public Collection<Article> findSimilar(Article articleToCompare, Collection<List<Article>> articles) {

        Collection<Article> result = new ArrayList<>();

        for (List<Article> articlesFromSpecificNewspaper : articles) {

            double max = 0L;
            Article articleMax = null;

            for (Article article : articlesFromSpecificNewspaper) {
                double score = similarity(articleToCompare.getBody(), article.getBody());
                if (score > max) {
                    max = score;
                    articleMax = article;
                }
            }
//            score >= 0.5 &&
            if (articleMax != null) {
                result.add(articleMax);
                System.out.println(max);
            }
        }
        return result;
    }

    private double similarity(String a, String b) {
        StringMetric service =
                with(new CosineSimilarity<>())
                        .simplify(Simplifiers.toLowerCase())
                        .simplify(Simplifiers.removeNonWord())
                        .simplify(Simplifiers.removeDiacritics())
                        .tokenize(Tokenizers.whitespace())
                        .tokenize(Tokenizers.qGram(2))
                        .build();

        return service.compare(a, b);

    }
}


