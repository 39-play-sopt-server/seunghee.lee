package org.sopt;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Member create(MemberRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new InvalidMemberException("이미 사용 중인 이메일입니다.");
        }
        return memberRepository.save(new Member(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        ));
    }
    @Transactional(readOnly = true)
    public Member find(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        if (member.isDeleted()) throw new InvalidMemberException("탈퇴한 회원입니다.");
        return member;
    }
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        if (!member.isDeleted()) member.withdraw();
    }
}
