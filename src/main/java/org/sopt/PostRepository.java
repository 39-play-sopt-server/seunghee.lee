package org.sopt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public Post save(Post post) {
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll() { return new ArrayList<>(posts.values()); }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public boolean delete(Long id) {
        return posts.remove(id) != null;
    }
}
