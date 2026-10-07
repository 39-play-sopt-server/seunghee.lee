package org.sopt;

public class Post {

    private String title;
    private String content;

    // 초기 생성자
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 공개 필드
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // 필드의 변경 책임을 Model인 Post가 가짐
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
