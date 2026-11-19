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

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime deletedAt;

    protected Member() { }

    public Member(String name, String email, String password) {
        if (name == null || name.isBlank()) throw new InvalidMemberException("이름은 비어 있을 수 없습니다.");
        if (email == null || email.isBlank()) throw new InvalidMemberException("이메일은 비어 있을 수 없습니다.");
        if (password == null || password.isBlank()) throw new InvalidMemberException("비밀번호는 비어 있을 수 없습니다.");
        this.name = name.strip();
        this.email = email.strip();
        this.password = password;
    }

    public Member(String email, String password) {
        this(email, email, password);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public boolean isDeleted() { return deletedAt != null; }
    public String getDisplayName() { return isDeleted() ? "탈퇴한 회원" : name; }
    public void withdraw() { this.deletedAt = LocalDateTime.now(); }
}
