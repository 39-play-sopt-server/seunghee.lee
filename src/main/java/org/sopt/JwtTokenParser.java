package org.sopt;

public interface JwtTokenParser {
    Long getMemberId(String token, TokenType expectedType);
}
