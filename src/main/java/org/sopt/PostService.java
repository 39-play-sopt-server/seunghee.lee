package org.sopt;

import java.util.List;

// Service 계층: 게시글과 관련된 비즈니스 흐름을 담당한다.
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public void create(String title, String content, String author, Category category) {
        repository.save(new Post(title, content, author, category));
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post findById(Long id) {
        return repository.findById(id);
    }

    public void update(Long id, String title, String content, Category category) {
        Post post = repository.findById(id);
        post.update(title, content, category);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
