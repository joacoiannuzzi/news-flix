package com.lab1.repository;

import com.lab1.matcher.MatcherClass;
import com.lab1.matcher.articleMatcherRunner;
import com.lab1.scrappers.ClarinScraper;
import com.lab1.scrappers.InfobaeScraper;
import com.lab1.scrappers.LaNacionScraper;
import com.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ScrapperTest {

    private EntityManagerFactory managerFactory;
    private articleMatcherRunner runner = new articleMatcherRunner();

    @Before
    public void setUp() {
        managerFactory = Persistence.createEntityManagerFactory("test");
        EntityManagers.setFactory(managerFactory);
    }

    @After
    public void close() {
        managerFactory.close(); //Make sure it only closes once, or when running multiple tests = fail.
    }

    @Test
    public void multiScrapper(){
        final LaNacionScraper laNacionScraper = new LaNacionScraper();

        laNacionScraper.scrap();

//        final ClarinScraper clarinScraper = new ClarinScraper();
//
//        clarinScraper.scrap();

        final InfobaeScraper infobaeScraper = new InfobaeScraper();

        infobaeScraper.scrap();

        List<MatcherClass> list = runner.serviceRun();

        for (int i = 0; i < list.size(); i++) {
            MatcherClass j = list.get(i);
            System.out.println("URL1 = "+j.getUrl1()+". URL2= "+j.getUrl2()+". SCORE ="+j.getScore());
        }

    }


    @Test
    public void createLaNacionScrapper() {
        final LaNacionScraper laNacionScraper = new LaNacionScraper();

        laNacionScraper.scrap();
    }
    @Test
    public void createClarinScrapper() {
        final ClarinScraper clarinScraper = new ClarinScraper();

        clarinScraper.scrap();
    }

    @Test
    public void createInfobaeScrapper() {
        final InfobaeScraper infobaeScraper = new InfobaeScraper();

        infobaeScraper.scrap();
    }

}
