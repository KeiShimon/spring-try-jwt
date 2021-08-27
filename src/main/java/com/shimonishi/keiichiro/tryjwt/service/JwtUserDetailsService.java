package com.shimonishi.keiichiro.tryjwt.service;

import java.util.ArrayList;

import com.shimonishi.keiichiro.tryjwt.entity.UserEntity;
import com.shimonishi.keiichiro.tryjwt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userMapper.getUser(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with name: " + username);
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }

    public void register(UserEntity userEntity) {
        userMapper.insert(userEntity);
    }
}
