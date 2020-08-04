package com.github.jamie9504.board.user.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jamie9504.board.user.model.UserPrincipal;
import com.github.jamie9504.board.user.payload.LoginRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /* Trigger when we issue POST request to /login
     * We also need to pass in {"email":"jamie", "password":"jamie1234"} in the request body
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        log.trace(">>>>>>>>>> JwtAuthenticationFilter.attemptAuthentication >>>>>>>>>>");

        // Grab credentials and map then to LoginRequest
        LoginRequest credentials = null;
        try {
            credentials = new ObjectMapper()
                .readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

        // Create login token
        assert credentials != null;
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                credentials.getEmail(),
                credentials.getPassword(),
                new ArrayList<>()
            );

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        log.trace("<<<<<<<<<< JwtAuthenticationFilter.attemptAuthentication <<<<<<<<<<");
        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult) {
        // Grab principal
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
            .withSubject(principal.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

        // Add token in response
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
    }
}
