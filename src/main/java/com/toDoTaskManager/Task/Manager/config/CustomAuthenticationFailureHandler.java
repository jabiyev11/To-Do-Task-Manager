package com.toDoTaskManager.Task.Manager.config;

import com.toDoTaskManager.Task.Manager.exceptions.EmailNotConfirmedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if(exception.getCause() instanceof EmailNotConfirmedException){
            response.sendRedirect("/login?error=emailNotConfirmed");
        }else{
            response.sendRedirect("/login?error");
        }
    }
}
