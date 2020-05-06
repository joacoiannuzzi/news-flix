package com.lab1.newsflix.controller;


import com.lab1.newsflix.model.Role;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.repository.UserRepository;
import com.lab1.newsflix.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/signup")
    ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        user.setIsActive(true);
        User result = userRepository.save(user);
        return ResponseEntity.created(new URI("/api/signup" + result.getId())).body(result);
    }

    @RequestMapping("/login")
    public User loginPage(@RequestBody User user){
        Optional<User> result = userRepository.findByEmail(user.getEmail());
        if(result.isPresent() && result.get().getPassword().equals( user.getPassword())){
            return loginSuccess(result.get());
        }

        throw new UsernameNotFoundException("Email Not Found!!");

    }

    @RequestMapping(value = "/secured/loginSuccess", method = RequestMethod.POST)
    public User loginSuccess(User user){

        Set<Role> roles = new HashSet<>();
        roles.addAll((Collection<? extends Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        user.setRole(roles);
        user.setIsActive(true);

        return user;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/secured/home")
    public String homePage(){
        return "home";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping("/secured/user")
    public String userPage(){
        return "user page";
    }

    @RequestMapping("/noLogin")
    public String noLogin(){
        return "user page";
    }
}
