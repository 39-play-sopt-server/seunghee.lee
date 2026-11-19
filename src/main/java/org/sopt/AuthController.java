package org.sopt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request.email(), request.password()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(new LoginResponse(authService.login(request.email(), request.password())));
    }
}
