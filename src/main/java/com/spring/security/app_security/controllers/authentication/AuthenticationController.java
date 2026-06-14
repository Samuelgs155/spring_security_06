package com.spring.security.app_security.controllers.authentication;

import com.spring.security.app_security.entities.authorization.JwtRequest;
import com.spring.security.app_security.entities.authorization.JwtResponse;
import com.spring.security.app_security.security.jwt.JwtService;
import com.spring.security.app_security.security.jwt.JwtUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/authenticate")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailService jwtUserDetailService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> postToken(@RequestBody JwtRequest request) {
        this.authenticate(request);
        final var userDetails = this.jwtUserDetailService.loadUserByUsername(request.getUsername());
        final String token = this.jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(JwtRequest request) {
        try {
            this.authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getUsername(),
                                    request.getPassword()));
        } catch (BadCredentialsException | DisabledException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
