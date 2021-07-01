package com.example.proyecto2.entity;

import javax.persistence.*;

@Entity
@Table(name="spo2")
public class Spo2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idspo2;

    private int valorspo2;

    public Integer getIdspo2() {
        return idspo2;
    }

    public void setIdspo2(Integer idspo2) {
        this.idspo2 = idspo2;
    }

    public int getValorspo2() {
        return valorspo2;
    }

    public void setValorspo2(int valorspo2) {
        this.valorspo2 = valorspo2;
    }
}
