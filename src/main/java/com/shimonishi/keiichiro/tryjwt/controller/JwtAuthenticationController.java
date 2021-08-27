package com.shimonishi.keiichiro.tryjwt.controller;

import com.shimonishi.keiichiro.tryjwt.entity.UserEntity;
import com.shimonishi.keiichiro.tryjwt.request.UserAuthenticateRequest;
import com.shimonishi.keiichiro.tryjwt.security.JwtTokenUtil;
import com.shimonishi.keiichiro.tryjwt.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public String createAuthenticationToken(@RequestBody UserAuthenticateRequest authenticationRequest) throws Exception {

        doAuthenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        return jwtTokenUtil.generate(userDetails);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserAuthenticateRequest request) {

        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        try {
            userDetailsService.register(userEntity);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.badRequest().body("Duplicate user: " + request.getUsername());
        } catch (Exception e) {
            log.error("Error while registering a user.", e);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

    private void doAuthenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
