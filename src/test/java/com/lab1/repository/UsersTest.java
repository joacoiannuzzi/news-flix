package com.lab1.repository;

import com.lab1.entity.Users;
import com.lab1.model.User;
import com.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class UsersTest {

    private EntityManagerFactory managerFactory;

    @After
    public void close() {
        managerFactory.close(); //Make sure it only closes once, or when running multiple tests = fail.
    }

    @Before
    public void setUp() {
        managerFactory = Persistence.createEntityManagerFactory("test");
        EntityManagers.setFactory(managerFactory);
    }

    @Test
    public void createUser() {
        final User user = new User();

        user.setEmail("a@gmail.com");
        user.setFirstName("John");
        user.setLastName("Johnson");

        assertFalse(Users.persist(user).getEmail().isEmpty());

        final Optional<User> persistedUser = Users.findByEmail(user.getEmail());

        assertThat(persistedUser.isPresent(), is(true));
        assertThat(persistedUser.get().getEmail(), is("a@gmail.com"));
        assertThat(persistedUser.get().getFirstName(), is("John"));
        assertThat(persistedUser.get().getLastName(), is("Johnson"));

        Optional<User> byEmail = Users.findByEmail(persistedUser.get().getEmail());
        System.out.println(byEmail);
    }

}