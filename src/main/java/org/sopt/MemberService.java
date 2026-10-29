package org.sopt;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) { this.memberRepository = memberRepository; }
    public Member create(MemberRequest request) { return memberRepository.save(new Member(request.name())); }
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
