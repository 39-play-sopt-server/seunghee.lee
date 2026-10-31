package org.sopt;

public record PostRequest(String title, String content, Long memberId, Category category) {
}
