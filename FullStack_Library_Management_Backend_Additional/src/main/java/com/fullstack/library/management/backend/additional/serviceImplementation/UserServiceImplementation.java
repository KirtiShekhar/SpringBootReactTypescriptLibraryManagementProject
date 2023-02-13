package com.fullstack.library.management.backend.additional.serviceImplementation;

import com.fullstack.library.management.backend.additional.entity.Role;
import com.fullstack.library.management.backend.additional.repository.UserRepository;
import com.fullstack.library.management.backend.additional.response.ResponseMessages;
import com.fullstack.library.management.backend.additional.service.UserService;
import com.fullstack.library.management.backend.additional.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseMessages<User> saveUser(User user)
    {
        ResponseMessages<User> userResponseMessages = new ResponseMessages<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userResponseMessages.setData(userRepository.save(user));
        return userResponseMessages;
    }

    @Override
    public ResponseMessages<Optional<User>> findByUserName(String userName)
    {
        ResponseMessages<Optional<User>> userOptionalResponseMessages = new ResponseMessages<>();
        userOptionalResponseMessages.setData(userRepository.findByUserName(userName));
        return userOptionalResponseMessages;
    }

    @Override
    public ResponseMessages<List<User>> getAllUsers()
    {
        ResponseMessages<List<User>> userListResponseMessages = new ResponseMessages<>();
        userListResponseMessages.setData(userRepository.findAll());
        return userListResponseMessages;
    }

    @Override
    @Transactional
    public void changeRole(Role changeRole, String userName)
    {
        userRepository.updateUserRole(userName,changeRole);
    }
}
