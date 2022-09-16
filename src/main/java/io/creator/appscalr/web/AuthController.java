package io.creator.appscalr.web;

import io.creator.appscalr.dto.*;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.exceptions.UserAlreadyExistAuthenticationException;
import io.creator.appscalr.service.serviceimpl.RefreshTokenService;
import io.creator.appscalr.service.serviceimpl.Userserviceimpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final Userserviceimpl authService;


    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signup(@RequestBody RegisterRequest registerRequest) throws UserAlreadyExistAuthenticationException {
        User user = authService.signup(registerRequest);
        RegisterResponse response = new RegisterResponse();
        response.setFullname(user.getFullname());
        response.setEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Logged in! x)");
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }

    /*@PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        System.out.println("Token refreshed! x)");
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(refreshTokenRequest));
    }*/

}
