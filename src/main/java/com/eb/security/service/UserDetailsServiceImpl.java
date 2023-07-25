package com.eb.security.service;

import com.eb.domain.User;
import com.eb.exceptions.ResourceNotFoundException;
import com.eb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        return UserDetailsImpl.build(user);
    }
}
