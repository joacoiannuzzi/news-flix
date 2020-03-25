package com.lab1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HibernateUtil {

    private SessionFactory sessionFactory;

    public HibernateUtil() {
        setUp();
    }

    protected void setUp() {

        if (sessionFactory == null) {
            // Create registry
            final StandardServiceRegistry registry =
                    new StandardServiceRegistryBuilder().configure().build();
            try {

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }

    }

    protected void tearDown() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public void testBasicUsage() {

        Session session = sessionFactory.openSession();
        // start a transaction
        session.beginTransaction();

        // save the student objects
        User user1 = new User("pete", "Fred", "petefred@gmail.com", true);
        User user2 = new User("George", "Hans", "georgehans@gmail.com", true);

        session.save(user1);
        session.save(user2);

        // commit transaction
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> students = session.createQuery("from User", User.class).list();
        students.forEach(s -> System.out.println(s.toString()));

        session.getTransaction().commit();
        session.close();
    }
}
