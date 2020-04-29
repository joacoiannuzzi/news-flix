package com.lab1.matcher;

import com.lab1.model.Article;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lab1.entity.Articles.listAll;
import static com.lab1.matcher.articleMatcherService.Similarity;

public class articleMatcherRunner {

    private List<MatcherClass> result = new ArrayList<>();

    public List<MatcherClass> serviceRun() {

        try {
            List<Article> list = listAll(); //Inside try, since this might throw error if cant get tx.

            for (int i = 0; i < list.size(); i++) {
                for (int j = 1; j < list.size(); j++) {
                     if (!list.get(i).getDiarioName().equals(list.get(j).getDiarioName()))
                            if (!list.get(i).getDiarioName().equals(list.get(j).getDiarioName())) { //Distintos Diarios.

                                //A:B
                                //B:A


                                double score = Similarity(list.get(i).getTitle() , list.get(j).getTitle());


                                    result.add(new MatcherClass(list.get(i).getUrl(), list.get(j).getUrl(), score));



                            }

                }

            }

            for (int i = 0; i < result.size(); i++) {
                for (int j = 1; j < result.size(); j++) {
                    if(result.get(i).getUrl1().equals(result.get(j).getUrl1())){
                        if (result.get(i).getScore() < result.get(j).getScore()){
                            result.remove(i);
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

