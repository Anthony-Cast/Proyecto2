package com.example.proyecto2.repository;

import com.example.proyecto2.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    public Usuario findByUsuario(String usuario);
}
