package org.sopt;

// @ModelAttribute로 query parameter를 바인딩하기 위한 검색 조건 객체
public class PostSearchCondition {
    private String keyword;
    private Category category;

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
