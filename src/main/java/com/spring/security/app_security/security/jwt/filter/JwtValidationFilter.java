package com.spring.security.app_security.security.jwt.filter;

import com.spring.security.app_security.security.jwt.JwtService;
import com.spring.security.app_security.security.jwt.JwtUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class JwtValidationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHENTICATION_HEADER_BEARER = "Bearer ";

    private final JwtService jwtService;
    private final JwtUserDetailService jwtUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String password = null;
        String jwt = null;
        if(requestTokenHeader != null && requestTokenHeader.startsWith(AUTHENTICATION_HEADER_BEARER)) {
            jwt = requestTokenHeader.substring(7);
            try {
                username = jwtService.getUsernameFromToken(jwt);

            } catch(IllegalArgumentException e) {
                log.error(e.getMessage());
            } catch(ExpiredJwtException ex) {
                log.warn("Expired JWT token: {}", ex.getMessage());
            }
        }

        if(Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            final var userDetail = this.jwtUserDetailService.loadUserByUsername(username);
            if(this.jwtService.validateToken(jwt, userDetail)) {
                var usernameAndPasswordAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetail, null, userDetail.getAuthorities()
                );
                usernameAndPasswordAuthToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordAuthToken);


            }

        }
        filterChain.doFilter(request, response);

    }

}
