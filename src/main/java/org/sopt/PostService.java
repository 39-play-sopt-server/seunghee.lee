package org.sopt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(PostRequest request) {
        Post post = new Post(request.title(), request.content(), request.author(), request.category());
        return postRepository.save(post);
    }

    public List<Post> findAll() { return postRepository.findAll(); }

    public List<Post> search(PostSearchCondition condition) {
        return postRepository.findAll().stream()
                .filter(post -> condition.getKeyword() == null
                        || condition.getKeyword().isBlank()
                        || post.getTitle().contains(condition.getKeyword())
                        || post.getContent().contains(condition.getKeyword()))
                .filter(post -> condition.getCategory() == null
                        || post.getCategory() == condition.getCategory())
                .collect(Collectors.toList());
    }
    public Post findById(Long id) { return postRepository.findById(id); }

    public Post update(Long id, PostRequest request) {
        Post post = postRepository.findById(id);
        post.update(request.title(), request.content(), request.category());
        return post;
    }

    public void delete(Long id) { postRepository.delete(id); }

    public Post updateCategory(Long id, Category category) {
        Post post = postRepository.findById(id);
        post.update(post.getTitle(), post.getContent(), category);
        return post;
    }
}
