package org.sopt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) { this.memberService = memberService; }

    @GetMapping("/me")
    public ResponseEntity<Long> me(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok(memberId);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> create(@RequestBody MemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(MemberResponse.from(memberService.create(request)), "회원이 생성되었습니다."));
    }
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> delete(@AuthenticationPrincipal Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.ok(ApiResponse.success(null, "회원 탈퇴가 처리되었습니다."));
    }
}
