package com.lab1.repository;

import com.lab1.matcher.MatcherClass;
import com.lab1.matcher.articleMatcherRunner;
import com.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MatcherTest {


    private EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("test");
    private EntityManagers managers = new EntityManagers();

    @Before
    public void setUp() {
        EntityManagers.setFactory(managerFactory);
    }

    @After
    public void close() {
        managerFactory.close(); //Make sure it only closes once, or when running multiple tests = fail.
    }






}
