package com.scm2.SmartContactManager.Config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.scm2.SmartContactManager.Helper.Message;
import com.scm2.SmartContactManager.Helper.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationFaliureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
                if(exception instanceof DisabledException){
                    HttpSession httpSession= request.getSession();
                    
                    Message message =Message.builder().content("This account is invalid pls validate it").type(MessageType.red).build();

                    httpSession.setAttribute("message", message);

                    response.sendRedirect("/login");

                    return;
                }

            else{
                response.sendRedirect("/login?error=true");

            }
    }

}
