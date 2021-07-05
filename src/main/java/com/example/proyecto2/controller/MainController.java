package com.example.proyecto2.controller;

import com.example.proyecto2.entity.Medicion;
import com.example.proyecto2.entity.Oximetro;
import com.example.proyecto2.entity.Usuario;
import com.example.proyecto2.repository.MedicionRepository;
import com.example.proyecto2.repository.OximetroRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/netpulse")
public class MainController {

    @Autowired
    MedicionRepository medicionRepository;
    @Autowired
    OximetroRepository oximetroRepository;

    @GetMapping("")
    public String bienvenida(){
        return "index.html";

    }

    @GetMapping("/monitoreospo2")
    public String monitoreo(Model model,HttpSession session){
        Usuario usuario=(Usuario) session.getAttribute("usuarioLogueado");
        List<String> pacientes = oximetroRepository.buscarPacientes(usuario.getIdcliente());
        for(int i=0;i< pacientes.size();i++){
            model.addAttribute("Paciente"+(i+1),pacientes.get(i));
        }
        return "monitoreo.html";
    }

    @GetMapping("/prueba")
    public String prueba(){
        return "bpm.html";

    }

    @PostMapping("/registrarspo2")
    public String registrar(@RequestParam(name = "valorSP") Integer spo2obtenido, @RequestParam(name = "valorID") Integer idoximetro) {
        Medicion medicion = new Medicion();
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime.now();
        medicion.setFecha(ahora);
        medicion.setIdoximetro(idoximetro);
        medicion.setValorspo2(spo2obtenido);
        medicionRepository.save(medicion);
        return "redirect:/netpulse/monitoreospo2";
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
