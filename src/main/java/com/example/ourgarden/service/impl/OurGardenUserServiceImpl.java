package com.example.ourgarden.service.impl;

import com.example.ourgarden.model.entity.UserEntity;
import com.example.ourgarden.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OurGardenUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public OurGardenUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsernameAndActive(username,true).orElseThrow(()->new UsernameNotFoundException("Username "+ username + " not found or not active"));
        return mapToUserDetails(userEntity);
    }

    private static UserDetails mapToUserDetails(UserEntity userEntity){

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + userEntity.getUserRoleEnum());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthority);
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );


    }
}
