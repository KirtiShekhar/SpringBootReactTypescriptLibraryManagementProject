package com.fullstack.library.management.backend.additional.service;

import com.fullstack.library.management.backend.additional.entity.Role;
import com.fullstack.library.management.backend.additional.entity.User;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserService
{
    ResponseMessages<User> saveUser(User user);

    ResponseMessages<Optional<User>> findByUserName(String userName);

    ResponseMessages<List<User>> getAllUsers();

    @Transactional
    void changeRole(Role changeRole, String userName);
}
