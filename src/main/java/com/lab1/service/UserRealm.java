package com.lab1.service;

import com.lab1.entity.Users;
import com.lab1.model.User;
import org.securityfilter.realm.SimpleSecurityRealmBase;

import java.util.Optional;

public class UserRealm extends SimpleSecurityRealmBase {

    @Override
    public boolean booleanAuthenticate(String username, String password) {
        final Optional<User> user = Users.findByEmail(username);
        return user.map(u -> u.getPassword().equals(password)).orElse(false);
    }

    @Override
    public boolean isUserInRole(String username, String rolename) {
        return true;
    }

}
