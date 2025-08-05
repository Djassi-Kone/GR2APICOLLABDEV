package com.apicollabdev.odk.collabdev.Filter;

import com.apicollabdev.odk.collabdev.security.SessionAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

public class FilterAuth {

    @Component
    public class AuthInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler) throws Exception {

            String token = request.getHeader("Authorization");

            if (token == null || !SessionAuth.sessions.containsKey(token)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Non autorisé. Veuillez vous authentifier.");
                return false;
            }

            //  stocker l’ID utilisateur dans une attribut request
            request.setAttribute("userId", SessionAuth.sessions.get(token));
            return true;
        }
    }

}
