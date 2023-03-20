package com.shop.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException{

        if("XMLHttpRequest".equals(request.getHeader("x-request-width"))){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized");
        }else {
            response.sendRedirect("/members/login");
        }
    }

}
