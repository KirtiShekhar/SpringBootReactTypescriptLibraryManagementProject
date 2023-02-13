package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.entity.User;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;

public interface AuthenticationService {
    ResponseMessages<User> signInAndReturnJwtRefreshAndAccessToken(User signInRequest);
}
