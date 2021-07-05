package com.example.proyecto2.repository;

import com.example.proyecto2.dto.PacienteDTO;
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
    @Query(value="select paciente,activo from oximetros where usuarios_idcliente=?1",nativeQuery = true)
    List<PacienteDTO> buscarPacientes(int id);
    @Query(value = "select o.*from usuarios u inner join oximetros o on u.idcliente = o.usuarios_idcliente\n" +
            "where o.idoximetro=?1 and u.nombre = ?2",nativeQuery = true)
    Oximetro buscarxIdAndNombre(int id, String nomnbre);


}
