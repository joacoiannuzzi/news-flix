package com.lab1.newsflix.repository;

import com.lab1.newsflix.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByCustomerID(String customerID);

    Optional<User> findBysubscriptionID(String subscriptionID);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByEmail(String email);
}
