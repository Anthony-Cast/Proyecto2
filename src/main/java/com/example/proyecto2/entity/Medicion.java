package com.example.proyecto2.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="mediciones")
public class Medicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idmedicion;

    private int idoximetro;

    private LocalDateTime fecha;

    private int valorspo2;

    public int getIdmedicion() {
        return idmedicion;
    }

    public void setIdmedicion(int idmedicion) {
        this.idmedicion = idmedicion;
    }

    public int getIdoximetro() {
        return idoximetro;
    }

    public void setIdoximetro(int idoximetro) {
        this.idoximetro = idoximetro;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getValorspo2() {
        return valorspo2;
    }

    public void setValorspo2(int valorspo2) {
        this.valorspo2 = valorspo2;
    }
}
