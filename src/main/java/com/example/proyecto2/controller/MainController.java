package com.example.proyecto2.controller;
import com.example.proyecto2.entity.Spo2;
import com.example.proyecto2.repository.Spo2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    Spo2Repository spo2Repository;

    @GetMapping("/index")
    public String bienvenida(){
        return "index.html";
    }

    @GetMapping("/monitoreo")
    public String monitoreo(){
        return "monitoreo.html";
    }

    @GetMapping("/registrarGet")
    public String registroconGet(@RequestParam(name = "valorSP") Integer spo2obtenido) {
        Spo2 valorspo2 = new Spo2();
        valorspo2.setValorspo2(spo2obtenido);
        spo2Repository.save(valorspo2);
        return "monitoreo.html";
    }

    @PostMapping("/registrar")
    public String registrar(@RequestParam(name = "valorSP") Integer spo2obtenido) {
        Spo2 valorspo2 = new Spo2();
        valorspo2.setValorspo2(spo2obtenido);
        spo2Repository.save(valorspo2);
        return "monitoreo.html";
    }
}
