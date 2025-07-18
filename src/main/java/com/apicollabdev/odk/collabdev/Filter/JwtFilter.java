/*package com.apicollabdev.odk.collabdev.Filter;

import com.apicollabdev.odk.collabdev.config.JwtUtils;
import com.apicollabdev.odk.collabdev.service.Impl.CustomerUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Un filtre JWT qui intercepte les requêtes HTTP pour vérifier la présence d'un token JWT dans l'en-tête Authorization.
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomerUserDetailsService userDetailsService;

    // Cette méthode est appelée pour filtrer les requêtes HTTP entrantes.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Vérifie si l'en-tête Authorization est présent et commence par "Bearer ".
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
        }

        // Si le nom d'utilisateur n'est pas nul et qu'il n'y a pas d'authentification dans le contexte de sécurité,
        // charge les détails de l'utilisateur et valide le token JWT.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Vérifie si le token JWT est valide.

            if (jwtUtils.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continue le filtrage de la requête dans la chaîne de filtres.
        filterChain.doFilter(request, response);
    }
}
*/