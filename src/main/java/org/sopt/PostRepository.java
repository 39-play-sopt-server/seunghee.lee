package org.sopt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Repository 계층: 게시글의 저장·조회·삭제만 담당한다.
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    // 저장
    public void save(Post post) {
        posts.add(post);
    }

    // 전체 조회
    public List<Post> findAll() {
        return Collections.unmodifiableList(posts);
    }

    // id로 단일 조회
    public Optional<Post> findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    // 삭제
    public void delete(Long id) {
        posts.remove(findById(id));
    }
}
