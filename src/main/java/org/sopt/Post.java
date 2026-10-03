package org.sopt;

public class Post {

    private String title;
    private String content;

    // 생성자
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 공개 필드로 접근 가능하게 구현
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // 공개 메서드 구현
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
