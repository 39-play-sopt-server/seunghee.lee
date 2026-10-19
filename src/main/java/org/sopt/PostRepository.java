package org.sopt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Repository 계층: 게시글의 저장·조회·삭제만 담당한다.
public class PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();
   // private final List<Post> posts = new ArrayList<>();
    private long nextId =1L;

    // 저장
    public void save(Post post) {
        posts.put(post.getId(), post);
    }

    // 전체 조회
    public List<Post> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(posts.values()));
    }

    // id로 단일 조회
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    // 삭제
    public void delete(Long id) {
        posts.remove(id);
    }

    public Long generatedId() {
        return nextId++;
    }
}
