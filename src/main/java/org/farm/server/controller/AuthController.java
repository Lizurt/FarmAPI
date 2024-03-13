package org.farm.server.controller;

import org.farm.server.model.requests.SignInRequest;
import org.farm.server.model.requests.SignUpRequest;
import org.farm.server.model.responses.JwtAuthenticationResponse;
import org.farm.server.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Creates a user based on sign up info and also generates and return a jwt
     *
     * @param request a sign up request
     * @return a jwt
     */
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    /**
     * Generates a jwt based on sign in info
     *
     * @param request a sign in request
     * @return a jwt
     */
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }

}
