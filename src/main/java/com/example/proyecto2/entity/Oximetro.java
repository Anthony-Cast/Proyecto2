package com.example.proyecto2.entity;

import javax.persistence.*;

@Entity
@Table(name="oximetros")
public class Oximetro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idoximetro;
    @ManyToOne
    @JoinColumn(name="usuarios_idcliente")
    private Usuario usuario;
    @Column(nullable = false)
    private String paciente;
    @Column(nullable = false)
    private int activo;

    public Integer getIdoximetro() {
        return idoximetro;
    }

    public void setIdoximetro(Integer idoximetro) {
        this.idoximetro = idoximetro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
