package org.sopt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) { this.memberService = memberService; }
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> create(@RequestBody MemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(MemberResponse.from(memberService.create(request)), "회원이 생성되었습니다."));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        memberService.delete(id); return ResponseEntity.ok(ApiResponse.success(null, "회원 탈퇴가 처리되었습니다."));
    }
}
