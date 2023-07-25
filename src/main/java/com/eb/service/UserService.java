package com.eb.service;

import com.eb.domain.Role;
import com.eb.domain.User;
import com.eb.domain.enums.UserRole;
import com.eb.exceptions.ConflictException;
import com.eb.exceptions.ResourceNotFoundException;
import com.eb.repository.RoleRepository;
import com.eb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eb.dto.RegisterRequest;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    //service class should talk to service class and service should talk its can repo class

    public void registerUser(RegisterRequest request)
    {
        if(userRepository.existsByUserName(request.getUserName()))
        {
            throw new ConflictException("user name is already in db");
        }

        Role role = roleRepository.findByName(UserRole.ROLE_STUDENT).orElseThrow(() -> new ResourceNotFoundException("role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        //to map dto to user
        User newUser = new User();
        newUser.setName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setUserName(request.getUserName());
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
    }
}
