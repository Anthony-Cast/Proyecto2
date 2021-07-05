package com.example.proyecto2.repository;

import com.example.proyecto2.entity.Oximetro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface OximetroRepository extends JpaRepository<Oximetro,Integer> {

    @Query(value="select count(idoximetro) from netpulse.oximetros where usuarios_idcliente =?1", nativeQuery = true)
    Integer cantidadOximetrosPorUsuario(int idcliente);

    @Query(value="select min(idoximetro) from netpulse.oximetros where usuarios_idcliente = ?1", nativeQuery = true)
    Integer menorIdOximetrosdelUsuario (int idcliente);
    @Query(value="select paciente from oximetros where activo=1 and usuarios_idcliente=?1",nativeQuery = true)
    List<String> buscarPacientes(int id);

}
