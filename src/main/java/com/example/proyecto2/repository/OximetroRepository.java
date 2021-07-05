package com.example.proyecto2.repository;

import com.example.proyecto2.entity.Oximetro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OximetroRepository extends JpaRepository<Oximetro,Integer> {
}
