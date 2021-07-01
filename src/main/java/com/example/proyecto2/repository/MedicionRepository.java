package com.example.proyecto2.repository;

import com.example.proyecto2.entity.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion,Integer> {
}
