package com.github.jamie9504.board.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.jamie9504.board.common.exception.NonexistentUser;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
        UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // endpoint every request hit with authorization
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        log.trace(">>>>>>>>>> JwtAuthorizationFilter.doFilterInternal >>>>>>>>>>");
        // Read the Authorization header, where the JWT Token should be
        String authorizationHeader = request.getHeader(JwtProperties.HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (authorizationHeader == null
            || !authorizationHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            // rest of the spring pipeline
            chain.doFilter(request, response);
            log.trace(
                "<<<<<<<<<< JwtAuthorizationFilter.doFilterInternal.checkingHeader <<<<<<<<<<");
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getEmailPasswordAuthentication(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
        log.trace("<<<<<<<<<< JwtAuthorizationFilter.doFilterInternal <<<<<<<<<<");
    }

    private Authentication getEmailPasswordAuthentication(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }
        // parse the token and validate it (decode)
        String email = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
            .build()
            .verify(authorizationHeader.replace(JwtProperties.TOKEN_PREFIX, ""))
            .getSubject();
        if (email == null) {
            return null;
        }
        // Search in the DB if we find the user by token subject (email)
        // If so, then grab user details and create spring auth token using email, pass, authorities/roles
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new NonexistentUser(email + "에 해당하는 유저가 없습니다."));
        UserPrincipal principal = new UserPrincipal(user);
        return new UsernamePasswordAuthenticationToken(email, null, principal.getAuthorities());
    }
}
