package mardini.project.kaskos.controller;

import lombok.RequiredArgsConstructor;
import mardini.project.kaskos.dto.LoginRequest;
import mardini.project.kaskos.dto.LoginResponse;
import mardini.project.kaskos.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(authentication);
        String jwt = authService.generateJwt(authentication.getName());
        LoginResponse loginResponse = LoginResponse.builder().type("Bearer").token(jwt).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }

}
