package com.bus_station_ticket.project.ProjectSecurity;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.bus_station_ticket.project.ProjectConfig.LoggerConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandlerCustom implements AuthenticationSuccessHandler{

       @Override
       public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                     Authentication authentication) throws IOException, ServletException {
              // TODO Auto-generated method stub
              
              LoggerConfig.writeInfoLevel(AuthenticationSuccessHandlerCustom.class, "onAuthenticationSuccess", "hello");

              new DefaultRedirectStrategy().sendRedirect(request, response, "/info-login-google");

       }
       
       
}
