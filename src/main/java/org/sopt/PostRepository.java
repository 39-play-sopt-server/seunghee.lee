package org.sopt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Repository 계층: 게시글의 저장·조회·삭제만 담당한다.
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public void save(Post post) {
        posts.add(post);
    }

    public List<Post> findAll() {
        return Collections.unmodifiableList(posts);
    }

    public Post findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElseThrow(PostNotFoundException::new);
    }

    public void delete(Long id) {
        posts.remove(findById(id));
    }
}
