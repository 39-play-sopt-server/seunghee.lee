package org.sopt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public Long signup(String email, String password) {
        if (memberRepository.existsByEmail(email)) {
            throw new InvalidMemberException("이미 가입된 이메일입니다.");
        }
        Member member = new Member(email, passwordEncoder.encode(password));
        return memberRepository.saveAndFlush(member).getId();
    }

    @Transactional(readOnly = true)
    public String login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .filter(foundMember -> !foundMember.isDeleted())
                .orElseThrow(() -> new InvalidMemberException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidMemberException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return jwtProvider.createAccessToken(member.getId());
    }
}
