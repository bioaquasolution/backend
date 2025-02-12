package com.thexbyte.bioaqua.config;

 
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thexbyte.bioaqua.utils.ResponseMsg;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFIlter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull  FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String userEmail ;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authHeader.substring(7);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            userEmail = jwtService.extractUsername(token);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            sendErrorResponse(response, objectMapper, "Token has expired");
            return;
        } catch (io.jsonwebtoken.JwtException e) {  
            sendErrorResponse(response, objectMapper, "Invalid token");
            return;
        }
    


        if(userEmail != null
                && SecurityContextHolder.getContext().getAuthentication()== null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(token , userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails , null ,
                                userDetails.getAuthorities()
                        );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } 
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }


    private void sendErrorResponse(HttpServletResponse response, ObjectMapper objectMapper, String message) throws IOException {
        ResponseMsg responseMsg = new ResponseMsg(message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
         response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(responseMsg));
        response.getWriter().flush();
    }
}