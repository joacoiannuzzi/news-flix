package com.lab1.matcher;


import com.google.common.base.Predicates;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.CosineSimilarity;
import static org.simmetrics.builders.StringMetricBuilder.with;

import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;

public class articleMatcherService {

    public static double Similarity(String a, String b){
        StringMetric service =
                with(new CosineSimilarity<>())
                .simplify(Simplifiers.toLowerCase())
                .simplify(Simplifiers.removeNonWord())
                .tokenize(Tokenizers.whitespace())
                .tokenize(Tokenizers.qGram(3))
                .build();

        return service.compare(a,b);
    }
}
