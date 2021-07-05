package com.example.proyecto2.controller;

import com.example.proyecto2.dto.MedicionDto;
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

    int IDglobal = 0;

    @Autowired
    MedicionRepository medicionRepository;

    @Autowired
    OximetroRepository oximetroRepository;

    @GetMapping("")
    public String bienvenida(){
        return "index";

    }

    @GetMapping("/monitoreospo2")
    public String monitoreo(){

        return "monitoreo";
    }

    @GetMapping("/graficoHistorico")
    public String graficoHistorico(HttpSession session, @RequestParam(name="valorID") Integer idoximetro, Model model){

        IDglobal = idoximetro;
        Usuario sessionUser = (Usuario) session.getAttribute("usuarioLogueado");
        Integer idcliente = sessionUser.getIdcliente();
        int minimoIdOximetrosUsuario = oximetroRepository.menorIdOximetrosdelUsuario(idcliente);
        System.out.println(minimoIdOximetrosUsuario);
        int idoximetroCorrespondiente = (minimoIdOximetrosUsuario + idoximetro - 1);
        System.out.println(idoximetroCorrespondiente);
        List<MedicionDto> listaValoresSpo2yFechaPorIdOxi = medicionRepository.listaMedicionesyFechasPorIdOximetro(idoximetroCorrespondiente);
        //List<Integer> listaMedicionesPorOximetro = medicionRepository.listaMedicionesPorIdOximetro(idoximetroCorrespondiente);
        //List<Date> listaFecha = medicionRepository.listaFechaPorIdOximetro(idoximetroCorrespondiente);
        model.addAttribute("listaMediciones",listaValoresSpo2yFechaPorIdOxi);
        return "spo2/prueba";

    }

    @PostMapping("/registrarspo2")
    public String registrar(@RequestParam(name = "valorSP") Integer spo2obtenido, @RequestParam(name = "valorID") Integer idoximetro,
                            @RequestParam(name = "vengoDE") Integer vengoDE) {
        Medicion medicion = new Medicion();
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime.now();
        medicion.setFecha(ahora);
        medicion.setIdoximetro(idoximetro);
        medicion.setValorspo2(spo2obtenido);
        medicionRepository.save(medicion);
        if(vengoDE == 10){
            return "redirect:/netpulse/monitoreospo2";
        }else if(vengoDE == 20){
            return "redirect:/netpulse/graficoHistorico?valorID=" + IDglobal;
        }else{
            return "redirect:/netpulse/monitoreospo2";
        }
    }
    @GetMapping("/grafico")
    public String graficoGozu(Model model){
        List<Integer> listaMediciones = medicionRepository.listaMediciones();
        List<Date> listaFecha = medicionRepository.listaFecha();
        model.addAttribute("listaMediciones",listaMediciones);
        model.addAttribute("listaFecha",listaFecha);
        return "grafico";
    }
}
