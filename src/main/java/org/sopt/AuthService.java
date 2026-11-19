package org.sopt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Base64;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenIssuer tokenIssuer;
    private final JwtTokenParser tokenParser;
    private final long refreshTokenExpiration;

    public AuthService(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenIssuer tokenIssuer,
            JwtTokenParser tokenParser,
            @org.springframework.beans.factory.annotation.Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenIssuer = tokenIssuer;
        this.tokenParser = tokenParser;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Transactional
    public Long signup(String name, String email, String password) {
        if (memberRepository.existsByEmail(email)) {
            throw new InvalidMemberException("이미 가입된 이메일입니다.");
        }
        Member member = new Member(name, email, passwordEncoder.encode(password));
        return memberRepository.saveAndFlush(member).getId();
    }

    @Transactional
    public TokenPair login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .filter(foundMember -> !foundMember.isDeleted())
                .orElseThrow(() -> new InvalidMemberException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidMemberException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        String accessToken = tokenIssuer.createAccessToken(member.getId());
        String refreshToken = tokenIssuer.createRefreshToken(member.getId());
        member.saveRefreshToken(
                hash(refreshToken),
                refreshTokenExpiresAt()
        );
        return new TokenPair(accessToken, refreshToken);
    }

    @Transactional
    public TokenPair refresh(String refreshToken) {
        Long memberId = tokenParser.getMemberId(refreshToken, TokenType.REFRESH);
        Member member = memberRepository.findById(memberId)
                .filter(foundMember -> foundMember.hasValidRefreshToken(hash(refreshToken), LocalDateTime.now()))
                .orElseThrow(() -> new InvalidMemberException("유효하지 않은 Refresh Token입니다."));

        String newAccessToken = tokenIssuer.createAccessToken(member.getId());
        String newRefreshToken = tokenIssuer.createRefreshToken(member.getId());
        member.saveRefreshToken(hash(newRefreshToken), refreshTokenExpiresAt());
        return new TokenPair(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void logout(Long memberId) {
        memberRepository.findById(memberId).ifPresent(Member::clearRefreshToken);
    }

    private String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(
                    digest.digest(token.getBytes(StandardCharsets.UTF_8))
            );
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("토큰 해시 알고리즘을 사용할 수 없습니다.", exception);
        }
    }

    private LocalDateTime refreshTokenExpiresAt() {
        return LocalDateTime.now().plus(Duration.ofMillis(refreshTokenExpiration));
    }
}
