package com.fullstack.library.management.backend.additional.serviceImplementation;

import com.fullstack.library.management.backend.additional.jwtsecurity.JwtProvider;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import com.fullstack.library.management.backend.additional.service.AuthenticationService;
import com.fullstack.library.management.backend.additional.service.JwtRefreshTokenService;
import com.fullstack.library.management.backend.additional.entity.User;
import com.fullstack.library.management.backend.additional.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @Override
    public ResponseMessages<User> signInAndReturnJwtRefreshAndAccessToken(User signInRequest)
    {
        ResponseMessages<User> userResponseMessages = new ResponseMessages<>();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUserName(),signInRequest.getPassword())
        );

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        String jwtToken = jwtProvider.generateToken(userPrinciple);

        User signInUser = userPrinciple.getUser();
        signInUser.setJwtAccessToken(jwtToken);
        signInUser.setJwtRefreshToken(jwtRefreshTokenService.createJwtRefreshToken(signInUser.getUserId()).getTokenId());
        userResponseMessages.setData(signInUser);

        return userResponseMessages;
    }
}
