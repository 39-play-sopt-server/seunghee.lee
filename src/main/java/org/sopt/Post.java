package org.sopt;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Post {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);
    private final Long id;
    private String title;
    private String content;
    private final String author;
    private Category category;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(String title, String content, String author, Category category) {
        validateText(title, "제목");
        validateText(content, "본문");
        validateText(author, "작성자");
        validateCategory(category);
        this.id = ID_GENERATOR.getAndIncrement();
        this.title = title.trim();
        this.content = content.trim();
        this.author = author.trim();
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public Category getCategory() { return category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // 게시글의 상태 변경 책임은 Model인 Post가 갖는다.
    public void update(String title, String content, Category category) {
        validateText(title, "제목");
        validateText(content, "본문");
        validateCategory(category);
        this.title = title.trim();
        this.content = content.trim();
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    private void validateText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidPostException(fieldName + "은(는) 비어 있을 수 없습니다.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new InvalidPostException("카테고리는 필수입니다.");
        }
    }
}
