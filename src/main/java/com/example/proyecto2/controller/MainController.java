package com.example.proyecto2.controller;
import com.example.proyecto2.entity.Medicion;
import com.example.proyecto2.repository.MedicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MainController {

    @Autowired
    MedicionRepository medicionRepository;

    @GetMapping("/index")
    public String bienvenida(){
        return "index.html";
    }

    @GetMapping("/monitoreo")
    public String monitoreo(){
        return "monitoreo.html";
    }

    @PostMapping("/registrar")
    public String registrar(@RequestParam(name = "valorSP") Integer spo2obtenido) {
        Medicion medicion = new Medicion();
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime.now();
        medicion.setFecha(ahora);
        medicion.setIdoximetro(2);
        medicion.setValorspo2(spo2obtenido);
        medicionRepository.save(medicion);
        return "redirect:/monitoreo";
    }
}
