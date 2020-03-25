package com.lab1.listener;

import com.lab1.util.EntityManagers;
import com.lab1.util.Servlets;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EntityManagerFactoryLifecycle implements ServletContextListener {

  private static EntityManagerFactory emf;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    emf = Persistence.createEntityManagerFactory("resources unit");
    EntityManagers.setFactory(emf);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    Servlets.setEntityManagerFactory(sce.getServletContext(), null);
    emf.close();
  }

}
