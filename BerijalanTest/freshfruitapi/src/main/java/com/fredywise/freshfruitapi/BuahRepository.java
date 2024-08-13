package com.fredywise.freshfruitapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuahRepository extends JpaRepository<Buah, Integer> {

    @Query("SELECT p FROM Buah p WHERE LOWER(p.name) LIKE %:name%")
    List<Buah> findByNameContainingIgnoreCase(String name);
}

