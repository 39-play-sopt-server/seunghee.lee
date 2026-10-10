package org.sopt;

public enum Category {
    NOTICE("공지"), FREE("자유"), QUESTION("질문"), INFORMATION("정보");

    private final String displayName;

    Category(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
