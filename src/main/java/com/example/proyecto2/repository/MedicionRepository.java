package com.example.proyecto2.repository;

import com.example.proyecto2.entity.Medicion;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.Date;
import java.util.List;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion,Integer> {
    @Query(value="select valorspo2 from mediciones",nativeQuery = true)
    List<Integer> listaMediciones();
    @Query(value="select fecha from mediciones",nativeQuery = true)
    List<Date> listaFecha();
}
