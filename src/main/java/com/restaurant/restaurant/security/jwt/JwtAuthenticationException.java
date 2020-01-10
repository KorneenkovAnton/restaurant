package com.restaurant.restaurant.security.jwt;

import javax.naming.AuthenticationException;

/**
 * Created by Антон on 09.01.2020.
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String explanation) {
        super(explanation);
    }

    public JwtAuthenticationException() {
    }
}
