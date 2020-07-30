package com.lab1.newsflix.repository;

import com.lab1.newsflix.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "select distinct plans from payment ", nativeQuery = true)
    List<String> getPlans();
}
