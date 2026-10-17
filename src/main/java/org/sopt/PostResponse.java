package org.sopt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// API 응답 형식을 고정해 Post 내부 구조와 외부 응답을 분리한다.
public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        Category category,
        long viewCount,
        String createdAt,
        String updatedAt
) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCategory(),
                post.getViewCount(),
                format(post.getCreatedAt()),
                format(post.getUpdatedAt())
        );
    }

    private static String format(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}
