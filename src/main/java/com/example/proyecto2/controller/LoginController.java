package com.example.proyecto2.controller;

import com.example.proyecto2.entity.Usuario;
import com.example.proyecto2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping({"","/login"})
    public String loginForm(){

        return "login.html";
    }

    @GetMapping("/redirectByRol")
    public String redirectByRol(Authentication auth, HttpSession session){
        String rol="";
        //setear la última fecha y hora de ingreso
        for(GrantedAuthority role:auth.getAuthorities()){
            rol= role.getAuthority();
            break;
        }

        Usuario usuarioLogueado= usuarioRepository.findByUsuario(auth.getName());
        session.setAttribute("usuarioLogueado",usuarioLogueado);

        /*DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime datetime1 = LocalDateTime.now();
        //String formatDateTime = datetime1.format(format);
        //System.out.println(formatDateTime);*/

        if (rol.equals("admin")){
            return "redirect:/user";
        }else{
            System.out.println(rol);
            return "/login";
        }
    }

}
