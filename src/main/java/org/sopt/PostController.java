package org.sopt;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody PostRequest request) {
        return postService.create(request);
    }

    @GetMapping
    public List<Post> findAll() {
        return postService.findAll();
    }

    // 예시: /posts/search?keyword=Spring&category=QUESTION
    @GetMapping("/search")
    public List<Post> search(@ModelAttribute PostSearchCondition condition) {
        return postService.search(condition);
    }

    // 예시: /posts?keyword=Spring&page=1
    @GetMapping("/filter")
    public List<Post> filter(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page
    ) {
        if (page < 1) {
            throw new InvalidPostException("페이지는 1 이상이어야 합니다.");
        }
        PostSearchCondition condition = new PostSearchCondition();
        condition.setKeyword(keyword);
        return postService.search(condition);
    }

    // 예시: Authorization 헤더와 sessionId 쿠키를 함께 받는 API
    @GetMapping("/session")
    public List<Post> findWithSession(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @CookieValue(value = "sessionId", required = false) String sessionId
    ) {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody PostRequest request) {
        return postService.update(id, request);
    }

    @PatchMapping("/{id}/category")
    public Post updateCategory(
            @PathVariable Long id,
            @RequestParam Category category
    ) {
        return postService.updateCategory(id, category);
    }

    // multipart/form-data 요청의 image 파트를 받는 예시 API
    @PostMapping("/{id}/image")
    public String uploadImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile image
    ) {
        postService.findById(id);
        return image.getOriginalFilename() + " 파일을 받았습니다.";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        postService.delete(id);
    }
}
