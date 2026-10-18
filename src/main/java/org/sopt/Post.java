package org.sopt;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private long viewCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // JPA가 Entity를 생성할 때 사용하는 기본 생성자
    protected Post() {
    }

    public Post(String title, String content, String author, Category category) {
        validateText(title, "제목");
        validateText(content, "본문");
        validateText(author, "작성자");
        validateCategory(category);

        this.title = title.strip();
        this.content = content.strip();
        this.author = author.strip();
        this.category = category;
        this.viewCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public Category getCategory() { return category; }
    public long getViewCount() { return viewCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void update(String title, String content, Category category) {
        validateText(title, "제목");
        validateText(content, "본문");
        validateCategory(category);

        this.title = title.strip();
        this.content = content.strip();
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTitle(String title) {
        validateText(title, "제목");
        this.title = title.strip();
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseViewCount() {
        viewCount++;
    }

    private void validateText(String value, String fieldName) {
        if (value == null || value.strip().isEmpty()) {
            throw new InvalidPostException(fieldName + "은(는) 비어 있을 수 없습니다.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new InvalidPostException("카테고리는 필수입니다.");
        }
    }
}
