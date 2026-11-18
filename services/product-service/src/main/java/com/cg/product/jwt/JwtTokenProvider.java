package com.cg.product.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String getToken() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwt) {

            return jwt.getToken()
                    .getTokenValue();
        }

        return null;
    }
}