package com.example.proyecto2.controller;

import com.example.proyecto2.dto.MedicionDto;
import com.example.proyecto2.dto.OximetrosDTO;
import com.example.proyecto2.dto.PacienteDTO;
import com.example.proyecto2.entity.Medicion;
import com.example.proyecto2.entity.Oximetro;
import com.example.proyecto2.entity.Usuario;
import com.example.proyecto2.repository.MedicionRepository;
import com.example.proyecto2.repository.OximetroRepository;
import com.example.proyecto2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/netpulse")
public class MainController {

    @Autowired
    MedicionRepository medicionRepository;
    @Autowired
    OximetroRepository oximetroRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("")
    public String bienvenida(){
        return "index";

    }

    @GetMapping("/monitoreospo2")
    public String monitoreo(Model model,HttpSession session){
        Usuario usuario=(Usuario) session.getAttribute("usuarioLogueado");
        List<PacienteDTO> pacientes = oximetroRepository.buscarPacientes(usuario.getIdcliente());
        System.out.println(pacientes.size());
        for(int i=0;i< pacientes.size();i++){
            if(pacientes.get(i).getActivo()==1) {
                model.addAttribute("Paciente" + (i + 1), pacientes.get(i).getPaciente());
            }
            else{
                model.addAttribute("Paciente" + (i + 1), "");
            }
            model.addAttribute("IDOxi"+(i+1), (i+1));
        }
        model.addAttribute("usuarioFirebase", usuario.getUsuario());
        return "monitoreo";
    }

    @GetMapping("/graficoHistorico")
    public String graficoHistorico(HttpSession session, @RequestParam(name="valorID") Integer idoximetro, Model model){

        Usuario sessionUser = (Usuario) session.getAttribute("usuarioLogueado");
        Integer idcliente = sessionUser.getIdcliente();
        int minimoIdOximetrosUsuario = oximetroRepository.menorIdOximetrosdelUsuario(idcliente);
        System.out.println(minimoIdOximetrosUsuario);
        int idoximetroCorrespondiente = (minimoIdOximetrosUsuario + idoximetro - 1);
        List<String> listDias = medicionRepository.listMedicionxDia(idoximetroCorrespondiente);
        List<MedicionDto> promDias = medicionRepository.valoresMensuales(idoximetroCorrespondiente);
        System.out.println(idoximetroCorrespondiente);
        List<MedicionDto> listaValoresSpo2yFechaPorIdOxi = medicionRepository.listaMedicionesyFechasPorIdOximetro(idoximetroCorrespondiente);
        model.addAttribute("listaMediciones",listaValoresSpo2yFechaPorIdOxi);
        model.addAttribute("usuarioFirebase", sessionUser.getUsuario());
        model.addAttribute("IDOximetro", idoximetro);
        model.addAttribute("listaDias",listDias);
        model.addAttribute("promDias",promDias);
        model.addAttribute("nombre",oximetroRepository.findById(idoximetroCorrespondiente).get().getPaciente());
        return "spo2/grafica";

    }

    @GetMapping("/resumenHistorico")
    public String resumenHistorico(HttpSession session, @RequestParam(name="valorID") Integer idoximetro, Model model){

        Usuario sessionUser = (Usuario) session.getAttribute("usuarioLogueado");
        Integer idcliente = sessionUser.getIdcliente();
        int minimoIdOximetrosUsuario = oximetroRepository.menorIdOximetrosdelUsuario(idcliente);
        System.out.println(minimoIdOximetrosUsuario);
        int idoximetroCorrespondiente = (minimoIdOximetrosUsuario + idoximetro - 1);

        List<MedicionDto> listaValoresSpo2yFechaPorIdOxi = medicionRepository.valoresUltimos(idoximetroCorrespondiente);
        model.addAttribute("listaMediciones",listaValoresSpo2yFechaPorIdOxi);
        model.addAttribute("usuarioFirebase", sessionUser.getUsuario());
        model.addAttribute("IDOximetro", idoximetro);
        model.addAttribute("nombre",oximetroRepository.findById(idoximetroCorrespondiente).get().getPaciente());
        return "spo2/resumen";

    }

    @PostMapping("/registrarspo2")
    public String registrar(@RequestParam(name = "valorSP") Integer spo2obtenido, @RequestParam(name = "valorID") Integer idoximetro,
                            @RequestParam(name = "vengoDE") Integer vengoDE,
                            @RequestParam(name = "IDOximetro", required = false) Integer IDOximetro, HttpSession session) {
        Medicion medicion = new Medicion();
        LocalDateTime ahora = LocalDateTime.now();
        LocalTime.now();
        medicion.setFecha(ahora);
        Usuario usuariologueado = (Usuario) session.getAttribute("usuarioLogueado");
        Integer idcliente = usuariologueado.getIdcliente();
        int minimoIdOximetrosUsuario = oximetroRepository.menorIdOximetrosdelUsuario(idcliente);
        System.out.println(minimoIdOximetrosUsuario);
        int idoximetroCorrespondiente = (minimoIdOximetrosUsuario + idoximetro - 1);
        System.out.println(idoximetroCorrespondiente);
        medicion.setIdoximetro(idoximetroCorrespondiente);
        medicion.setValorspo2(spo2obtenido);
        medicionRepository.save(medicion);

        if(vengoDE == 10){
            return "redirect:/netpulse/monitoreospo2";
        }else if(vengoDE == 20){
            return "redirect:/netpulse/graficoHistorico?valorID=" + IDOximetro;
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

    @GetMapping("/oximetros")
    public String tablaOximetros(HttpSession session,Model model){
        Usuario usuariologueado = (Usuario) session.getAttribute("usuarioLogueado");
        List<OximetrosDTO> tablaInfo=usuarioRepository.buscarOximetroTabla(usuariologueado.getIdcliente());
        String nombre="Oxímetros asociados de\n "+ usuariologueado.getNombre();
        model.addAttribute("nombre",nombre);
        model.addAttribute("datosTabla",tablaInfo);
     return "tabla";
    }
    @GetMapping("/editar")
    public String editar(Model model, @RequestParam("id") int id, @ModelAttribute("oximetro")Oximetro oximetro){
        Optional<Oximetro> oximetroOptional = oximetroRepository.findById(id);
        if(oximetroOptional.isPresent()){
            oximetro=oximetroOptional.get();
            model.addAttribute("oximetro",oximetro);
            return "editar";
        }
        else{
            model.addAttribute("mensaje","No hay pulsioxímetro asociado");
            return "redirect:/netpulse/oximetros";
        }
    }
}
