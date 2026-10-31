package org.sopt;
import jakarta.persistence.*;

@Entity
public class Post extends BaseEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String title;
    @Column(nullable=false, columnDefinition="TEXT") private String content;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="member_id", nullable=false) private Member author;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Category category;
    @Column(nullable=false) private long viewCount;
    protected Post() {}
    public Post(String title,String content,Member author,Category category) { validate(title,content,author,category); this.title=title.strip(); this.content=content.strip(); this.author=author; this.category=category; }
    public Long getId(){return id;} public String getTitle(){return title;} public String getContent(){return content;} public Member getAuthor(){return author;} public Category getCategory(){return category;} public long getViewCount(){return viewCount;}
    public void increaseViewCount(){viewCount++;}
    public void update(String title,String content,Category category){validate(title,content,author,category);this.title=title.strip();this.content=content.strip();this.category=category;}
    private void validate(String title,String content,Member author,Category category){if(title==null||title.isBlank())throw new InvalidPostException("제목은 비어 있을 수 없습니다.");if(content==null||content.isBlank())throw new InvalidPostException("본문은 비어 있을 수 없습니다.");if(author==null)throw new InvalidPostException("작성자는 필수입니다.");if(category==null)throw new InvalidPostException("카테고리는 필수입니다.");}
}
