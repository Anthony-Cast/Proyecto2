package com.example.proyecto2.repository;

import com.example.proyecto2.entity.Spo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Spo2Repository extends JpaRepository<Spo2,Integer> {

}
