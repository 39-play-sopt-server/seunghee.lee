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

    public List<Post> findAll() { return postRepository.findAll(); }
    public Post findById(Long id) { return postRepository.findById(id); }

    public Post update(Long id, PostRequest request) {
        Post post = postRepository.findById(id);
        post.update(request.title(), request.content(), request.category());
        return post;
    }

    public void delete(Long id) { postRepository.delete(id); }
}
