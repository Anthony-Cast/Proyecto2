package com.example.proyecto2.repository;

import com.example.proyecto2.dto.MedicionDto;
import com.example.proyecto2.entity.Medicion;
import com.example.proyecto2.entity.Oximetro;
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

    @Query(value="select m.valorspo2 from mediciones m where m.idoximetro = ?1 order by m.fecha asc",nativeQuery = true)
    List<Integer> listaMedicionesPorIdOximetro(int idoximetro);

    @Query(value="select fecha from mediciones",nativeQuery = true)
    List<Date> listaFecha();

    @Query(value="select m.fecha from mediciones m where m.idoximetro = ?1 order by m.fecha asc",nativeQuery = true)
    List<Date> listaFechaPorIdOximetro(int idoximetro);

    @Query(value="select m.valorspo2 as valorspo2, m.fecha as fechamedicion from mediciones m where m.idoximetro = ?1 order by m.fecha asc",nativeQuery = true)
    List<MedicionDto> listaMedicionesyFechasPorIdOximetro(int idoximetrro);
    @Query(value="select date(fecha) from mediciones\n" +
            "where idoximetro=?1\n" +
            "group by date(fecha)",nativeQuery = true)
    List<String> listMedicionxDia(int id);
    @Query(value = "select date(fecha) fechamedicion, round(avg(valorspo2),2) valorspo2 from mediciones\n" +
            "where idoximetro=?1\n" +
            "group by date(fecha)",nativeQuery = true)
    List<MedicionDto> valoresMensuales(int id);
    @Query(value="SELECT * FROM (SELECT fecha as fechamedicion,valorspo2 as valorspo2 from mediciones where idoximetro = ?1 ORDER BY fecha DESC LIMIT 15) sub ORDER BY fechamedicion ASC",nativeQuery = true)
    List<MedicionDto> valoresUltimos(int id);


}
