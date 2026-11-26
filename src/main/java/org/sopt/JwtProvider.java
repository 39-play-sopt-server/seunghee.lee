package org.sopt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider implements JwtTokenIssuer, JwtTokenParser {
    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public String createAccessToken(Long memberId) {
        return createToken(memberId, TokenType.ACCESS, accessTokenExpiration);
    }

    @Override
    public String createRefreshToken(Long memberId) {
        return createToken(memberId, TokenType.REFRESH, refreshTokenExpiration);
    }

    private String createToken(Long memberId, TokenType tokenType, long expiration) {
        Date now = new Date();
        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim("tokenType", tokenType.name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public Long getMemberId(String token, TokenType expectedType) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (!expectedType.name().equals(claims.get("tokenType", String.class))) {
            throw new JwtException("토큰 종류가 올바르지 않습니다.");
        }

        return Long.valueOf(claims.getSubject());
    }
}
