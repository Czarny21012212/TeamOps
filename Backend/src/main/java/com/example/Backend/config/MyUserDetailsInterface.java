package com.example.Backend.config;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MyUserDetailsInterface {
    org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
