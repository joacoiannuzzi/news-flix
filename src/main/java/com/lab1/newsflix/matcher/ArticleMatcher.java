package com.lab1.newsflix.matcher;

import com.lab1.newsflix.model.Article;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Predicates.in;
import static com.lab1.newsflix.scraper.AbstractScraper.SEPARATION;
import static org.simmetrics.builders.StringMetricBuilder.with;


@Component
public class ArticleMatcher {

    public Collection<Article> findSimilar(Article articleToCompare, Collection<List<Article>> articles) {

        Collection<Article> result = new ArrayList<>();

        for (List<Article> articlesFromSpecificNewspaper : articles) {

            double max = 0L;


            Article articleMax = null;

            for (Article article : articlesFromSpecificNewspaper) {
                double score1 = similarity(articleToCompare.getTitle(), article.getTitle());
                double score2 = similarity(articleToCompare.getBody(), article.getBody());
                double score = ((score1 * 0.60) + (score2 * 0.40)) / 2;
                if (score > max) {
                    max = score;
                    articleMax = article;
                }
            }
            if (articleMax != null) {
                result.add(articleMax);
                System.out.println(max);
            }
        }
        return result;
    }

    private double similarity(String a, String b) {
        Set<String> commonWords = Sets.newHashSet("la", "que", "el", "en", "y", "a", "los", "se", "del", "las", "un", "por", "con", "no", "una", "su", "para", "es", "al", "lo", "como", "más", "o", "pero", "sus", "le", "ha", "me", "si", "sin", "sobre", "este", "ya", "entre", "cuando", "todo", "esta", "ser", "son", "dos", "también", "fue", "había", "era", "muy", "años", "hasta", "desde", "está", "mi", "porque", "qué", "sólo", "han", "yo", "hay", "vez", "puede", "todos", "así", "nos", "ni", "parte", "tiene", "él", "uno", "donde");

        StringMetric service =
                with(new CosineSimilarity<>())
                        .simplify(Simplifiers.toLowerCase())
                        .simplify(Simplifiers.removeNonWord())
                        .simplify(Simplifiers.removeDiacritics())

                        .simplify(Simplifiers.replaceAll(SEPARATION, " "))

                        .tokenize(Tokenizers.whitespace())
                        .filter(Predicates.not(in(commonWords)))
                        .tokenize(Tokenizers.qGram(3))
                        .build();

        return service.compare(a, b);

    }

}


