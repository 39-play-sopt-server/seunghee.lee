package org.sopt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public Post findById(Long id) {
        Post post = posts.get(id);
        if (post == null) throw new PostNotFoundException();
        return post;
    }

    public void delete(Long id) {
        if (posts.remove(id) == null) throw new PostNotFoundException();
    }
}
