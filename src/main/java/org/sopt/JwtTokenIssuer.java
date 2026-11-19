package org.sopt;

public interface JwtTokenIssuer {
    String createAccessToken(Long memberId);

    String createRefreshToken(Long memberId);
}
