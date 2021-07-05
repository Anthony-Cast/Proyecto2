package com.example.proyecto2.repository;

import com.example.proyecto2.dto.OximetrosDTO;
import com.example.proyecto2.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    public Usuario findByUsuario(String usuario);
    @Query(value="select paciente,activo,idoximetro from oximetros\n" +
            "where usuarios_idcliente=?1\n" +
            "order by idoximetro",nativeQuery = true)
    List<OximetrosDTO> buscarOximetroTabla(int id);


}
