package org.sopt;

import java.util.List;

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

    public List<Post> findAll(Category category) {
        return postRepository.findAll().stream()
                .filter(post -> category == null || post.getCategory() == category)
                .toList();
    }

    public Post findById(Long id) { return getPostOrThrow(id); }

    public Post findDetail(Long id) {
        Post post = getPostOrThrow(id);
        post.increaseViewCount();
        return post;
    }

    public Post update(Long id, PostRequest request) {
        Post post = getPostOrThrow(id);
        post.update(request.title(), request.content(), request.category());
        return post;
    }

    public void delete(Long id) {
        if (!postRepository.delete(id)) {
            throw new PostNotFoundException();
        }
    }

    public Post updateCategory(Long id, Category category) {
        Post post = getPostOrThrow(id);
        post.update(post.getTitle(), post.getContent(), category);
        return post;
    }

    private Post getPostOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}
