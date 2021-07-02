package com.example.proyecto2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/processLogin") // for the POST request of the login form
                .defaultSuccessUrl("/redirectByRol",true);

        httpSecurity.logout()
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        httpSecurity.authorizeRequests()
                .antMatchers("/user","/user/**").hasAuthority("admin")
                .antMatchers("/login", "/login/**").permitAll()
                .anyRequest().permitAll();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select u.usuario, u.contrasenia, u.cuenta from usuarios u WHERE u.usuario =?")
                .authoritiesByUsernameQuery("select u.usuario, u.rol from usuarios u where u.usuario=? and u.cuenta != -1");
    }
}
