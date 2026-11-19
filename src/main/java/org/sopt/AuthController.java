package org.sopt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final String refreshTokenCookieName;
    private final boolean refreshTokenCookieSecure;

    public AuthController(
            AuthService authService,
            @Value("${jwt.refresh-token-cookie-name}") String refreshTokenCookieName,
            @Value("${jwt.refresh-token-cookie-secure}") boolean refreshTokenCookieSecure
    ) {
        this.authService = authService;
        this.refreshTokenCookieName = refreshTokenCookieName;
        this.refreshTokenCookieSecure = refreshTokenCookieSecure;
    }

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request.name(), request.email(), request.password()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        TokenPair tokens = authService.login(request.email(), request.password());
        return withRefreshCookie(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(
            @CookieValue(name = "refresh-token", required = false) String refreshToken
    ) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(401).build();
        }
        return withRefreshCookie(authService.refresh(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Long memberId) {
        authService.logout(memberId);
        return ResponseEntity.noContent()
                .header("Set-Cookie", clearRefreshCookie().toString())
                .build();
    }

    private ResponseEntity<LoginResponse> withRefreshCookie(TokenPair tokens) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.accessToken())
                .header("Set-Cookie", refreshCookie(tokens.refreshToken()).toString())
                .body(new LoginResponse(tokens.accessToken()));
    }

    private ResponseCookie refreshCookie(String token) {
        return ResponseCookie.from(refreshTokenCookieName, token)
                .httpOnly(true)
                .secure(refreshTokenCookieSecure)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(60L * 60 * 24 * 14)
                .build();
    }

    private ResponseCookie clearRefreshCookie() {
        return ResponseCookie.from(refreshTokenCookieName, "")
                .httpOnly(true)
                .secure(refreshTokenCookieSecure)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(0)
                .build();
    }
}
