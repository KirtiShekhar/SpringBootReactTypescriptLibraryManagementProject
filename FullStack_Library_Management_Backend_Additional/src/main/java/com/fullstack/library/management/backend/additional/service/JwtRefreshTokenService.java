package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.entity.JwtRefreshToken;
import com.fullstack.library.management.backend.additional.entity.User;

public interface JwtRefreshTokenService
{
    JwtRefreshToken createJwtRefreshToken(Long userId);

    User generateAccessTokenFromJwtRefreshToken(String refreshTokenId);
}
