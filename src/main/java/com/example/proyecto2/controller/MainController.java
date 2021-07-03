package com.example.proyecto2.controller;

import com.example.proyecto2.entity.Medicion;
import com.example.proyecto2.repository.MedicionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class MainController {

    @Autowired
    MedicionRepository medicionRepository;

    @GetMapping("")
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
        return "redirect:/user/monitoreo";
    }
    @GetMapping("/grafico")
    public String graficoGozu(Model model){
        List<Integer> listaMediciones = medicionRepository.listaMediciones();
        List<Date> listaFecha = medicionRepository.listaFecha();
        model.addAttribute("listaMediciones",listaMediciones);
        model.addAttribute("listaFecha",listaFecha);
        return "grafico.html";
    }
}
