package org.sopt;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController implements PostApi {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Override
    public ResponseEntity<ApiResponse<PostResponse>> create(PostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        PostResponse.from(postService.create(request)),
                        "게시글이 작성되었습니다."
                ));
    }

    @Override
    public ResponseEntity<ApiResponse<Page<PostResponse>>> findAll(Category category, Pageable pageable) {
        Page<PostResponse> responses = postService.findAll(category, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"))).map(PostResponse::from);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @Override
    public ResponseEntity<ApiResponse<List<Category>>> findCategories() {
        return ResponseEntity.ok(ApiResponse.success(Arrays.asList(Category.values())));
    }

    @Override
    public ResponseEntity<ApiResponse<PostResponse>> findById(Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                PostResponse.from(postService.findDetail(id))
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<PostResponse>> update(Long id, PostRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                PostResponse.from(postService.update(id, request)),
                "게시글이 수정되었습니다."
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<PostResponse>> updateCategory(Long id, Category category) {
        return ResponseEntity.ok(ApiResponse.success(
                PostResponse.from(postService.updateCategory(id, category)),
                "카테고리가 수정되었습니다."
        ));
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> delete(Long id) {
        postService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "게시글이 삭제되었습니다."));
    }
}
