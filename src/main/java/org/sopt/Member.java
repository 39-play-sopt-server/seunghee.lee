package org.sopt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    private LocalDateTime deletedAt;

    protected Member() { }

    public Member(String name) {
        if (name == null || name.isBlank()) throw new InvalidMemberException("이름은 비어 있을 수 없습니다.");
        this.name = name.strip();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public boolean isDeleted() { return deletedAt != null; }
    public String getDisplayName() { return isDeleted() ? "탈퇴한 회원" : name; }
    public void withdraw() { this.deletedAt = LocalDateTime.now(); }
}
