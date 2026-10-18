package org.sopt;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Post findDetail(Long id) {
        Post post = getPostOrThrow(id);
        post.increaseViewCount();
        return post;
    }

    @Transactional
    public Post update(Long id, PostRequest request) {
        Post post = getPostOrThrow(id);
        post.update(request.title(), request.content(), request.category());
        return post;
    }

    public void delete(Long id) {
        Post post = getPostOrThrow(id);
        postRepository.deleteById(post.getId());
    }

    @Transactional
    public Post updateCategory(Long id, Category category) {
        Post post = getPostOrThrow(id);
        post.update(post.getTitle(), post.getContent(), category);
        return post;
    }

    @Transactional
    public Post updatePostTitle(Long id, String title) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.updateTitle(title);

        return post;
    }

    private Post getPostOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}
