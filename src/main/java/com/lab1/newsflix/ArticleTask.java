package com.lab1.newsflix;

import com.lab1.newsflix.scraper.ScraperManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ArticleTask {

    @Autowired
    private ScraperManager scraperManager;

    @Scheduled(fixedDelay = 300000) // 5 minutes (in millis)
    public void execute() {
        scraperManager.scrap();
        System.out.println("finished");
    }
}
