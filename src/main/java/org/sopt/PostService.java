package org.sopt;

import java.util.List;
import java.util.Optional;

// Service 계층: 게시글과 관련된 비즈니스 흐름을 담당한다.
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void create(String title, String content, String author, Category category) {
        Long id = repository.generatedId();
        Post post = new Post(id, title, content, author, category);
        repository.save(post);
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(Long id) {
        return repository.findById(id)
                         .orElseThrow(PostNotFoundException::new);
    }

    public PostResponse findPostById(Long id) {
        Post post = findById(id);
        return new PostResponse(post);
    }


    public void update(Long id, String title, String content, Category category) {
        Post post = this.findById(id);
        post.update(title, content, category);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
