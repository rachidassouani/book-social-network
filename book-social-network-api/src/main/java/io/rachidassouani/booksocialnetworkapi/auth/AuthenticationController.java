package io.rachidassouani.booksocialnetworkapi.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest registrationRequest) throws MessagingException {

        authenticationService.register(registrationRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(
            @RequestBody @Valid AuthenticateRequest authenticateRequest) {
        AuthenticateResponse result = authenticationService.authenticate(authenticateRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("activate-account")
    public void activateAccount(@RequestParam String token) throws MessagingException {
        authenticationService.activateAccount(token);
    }
}
