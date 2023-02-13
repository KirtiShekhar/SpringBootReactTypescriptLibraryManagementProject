package com.fullstack.library.management.backend.additional.jwtsecurity;

import com.fullstack.library.management.backend.additional.security.UserPrinciple;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider {
    String generateToken(UserPrinciple auth);

    boolean isTokenValid(HttpServletRequest request);

    Authentication getAuthentication(HttpServletRequest request);
}
